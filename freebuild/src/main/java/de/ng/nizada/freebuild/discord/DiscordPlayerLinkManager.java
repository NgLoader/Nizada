package de.ng.nizada.freebuild.discord;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.gamecore.util.Pair;
import de.ng.nizada.gamecore.util.PropertiesUtil;
import de.ng.nizada.gamecore.util.StringGenerator;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import sx.blah.discord.handle.obj.IUser;

public class DiscordPlayerLinkManager {
	
	public static final DiscordPlayerLinkManager DISCORD_PLAYER_LINK_MANAGER = new DiscordPlayerLinkManager();
	
	private final HashMap<UUID, Long> LINKED_PLAYERS;
	private final HashMap<UUID, Pair<Long, String>> PLAYER_VERIFICATION;
	
	public DiscordPlayerLinkManager() {
		LINKED_PLAYERS = new HashMap<>();
		PLAYER_VERIFICATION = new HashMap<>();
	}
	
	public void load() {
		Properties properties = PropertiesUtil.load(new File(Freebuild.instance.getDataFolder(), "discord/linked.properties"));
		properties.forEach((key, value) -> LINKED_PLAYERS.put(UUID.fromString((String) key), Long.valueOf((String) value)));
	}
	
	public void save() {
		Properties properties = new Properties();
		LINKED_PLAYERS.forEach((key, value) -> properties.put(key.toString(), value));
		PropertiesUtil.save(new File(Freebuild.instance.getDataFolder(), "discord/linked.properties"), properties);
	}
	
	public void unlink(IUser user) {
		if(isLinked(user.getLongID())) {
			LINKED_PLAYERS.remove(getUUID(user.getLongID()));
			user.removeRole(DiscordManager.DISCORD_MANAGER.getDiscordClient().getGuildByID(DiscordManager.GUILD_ID).getRoleByID(DiscordManager.LINKED_GROUP));
			user.getOrCreatePMChannel().sendMessage("Du bist nun nicht mehr mit Minecraft verbunden.");
		} else
			user.getOrCreatePMChannel().sendMessage("Du bist derzeitig nicht mit Minecraft verbunden.");
	}
	
	public void createVerification(IUser user, String username) {
		Player player = Bukkit.getPlayer(username);
		
		if(player != null)
			if(!isLinked(user.getLongID()) && !isLinked(player.getUniqueId())) {
				String code = StringGenerator.ALPHANUMERICSPECIAL.generateString(8);
				PLAYER_VERIFICATION.put(player.getUniqueId(), new Pair<Long, String>(user.getLongID(), code));
				
				user.getOrCreatePMChannel().sendMessage("Bitte bestätigen sie nun in Minecraft ihren Account.");
				
				TextComponent textComponent = new TextComponent(
						Freebuild.PREFIX + "§7Möchtest du dich mit den §9Discord §7account §8\"§a" + user.getName() + "#" + user.getDiscriminator() + "§8\" verbinden?"
						+ "\n" + Freebuild.PREFIX + "§7 ");
				
				TextComponent accept = new TextComponent("§aAnnehmen");
				TextComponent deny = new TextComponent("§cAblehnen");
				
				accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aVerbinde dich mit §2" + user.getName() + "§7#§2" + user.getDiscriminator()).create()));
				accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/link " + user.getLongID() + " " + code));
				
				deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cLehne die verbinden mit §4" + user.getName() + "§7#§4" + user.getDiscriminator() + " §cab").create()));
				deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/unlink"));

				textComponent.addExtra(accept);
				textComponent.addExtra(new TextComponent(" §8- "));
				textComponent.addExtra(deny);
				
				player.spigot().sendMessage(textComponent);
			} else
				user.getOrCreatePMChannel().sendMessage("Der Nutzer ist schon mit einen anderen Benutzer verknüpft.\nWenn sie ihren Benutzer entlinked wollen geben sie ``/unlink`` ein");
		else
			user.getOrCreatePMChannel().sendMessage("Der Nutzer \"" + username + "\" konnte nicht gefunden werden.");
	}
	
	public void verificate(Player player, String longIdStr, String token) {
		UUID uuid = player.getUniqueId();
		Long longId;
		
		try {
			longId = Long.valueOf(longIdStr);
		} catch(NumberFormatException ex) {
			player.sendMessage(Freebuild.PREFIX + "§7Es ist ein §cFehler §7aufgetreten§8.");
			return;
		}
		
		IUser user = DiscordManager.DISCORD_MANAGER.getDiscordClient().getGuildByID(DiscordManager.GUILD_ID).getUserByID(longId);
		
		if(user != null)
			if(!isLinked(uuid, longId))
				if(PLAYER_VERIFICATION.containsKey(uuid)) {
					Pair<Long, String> pair = PLAYER_VERIFICATION.get(uuid);
					
					if(pair.getKey().equals(longId) && pair.getValue().equals(token)) {
						LINKED_PLAYERS.put(uuid, longId);
						PLAYER_VERIFICATION.remove(uuid);
						user.addRole(DiscordManager.DISCORD_MANAGER.getDiscordClient().getGuildByID(DiscordManager.GUILD_ID).getRoleByID(DiscordManager.LINKED_GROUP));
						player.sendMessage(Freebuild.PREFIX + "§7Sie sind nun §aerfolgreich §7mit den Nutzer §8\"§a" + user.getName() +  "§7#§a" + user.getDiscriminator() + "§8\" §7verbunden§8.");
						user.getOrCreatePMChannel().sendMessage("Sie sind nun erfolgreich mit den Minecraft Nutzer \"" + player.getName() + "\" verbunden.");
					} else
						player.sendMessage(Freebuild.PREFIX + "§7Der angegebene §cCode §7ist falsch§8.");
				} else
					player.sendMessage(Freebuild.PREFIX + "§7Sie sind nicht in der §cverification §7phase§8.");
			else
				player.sendMessage(Freebuild.PREFIX + "§7Sie sind bereits §averbunden§8.");
		else
			player.sendMessage(Freebuild.PREFIX + "§7Es wurde kein §cNutzer §7gefunden§8.");
	}
	
	public UUID getUUID(Long longId) {
		for(Entry<UUID, Long> entry : LINKED_PLAYERS.entrySet())
			if(entry.getValue().equals(longId))
				return entry.getKey();
		return null;
	}
	
	public Long getLongId(UUID uuid) {
		return LINKED_PLAYERS.containsKey(uuid) ? LINKED_PLAYERS.get(uuid) : null;
	}
	
	public boolean isLinked(UUID uuid) {
		if(uuid != null)
			return LINKED_PLAYERS.containsKey(uuid);
		return false;
	}
	
	public boolean isLinked(Long longId) {
		if(longId != null)
			return LINKED_PLAYERS.containsValue(longId);
		return false;
	}
	
	public boolean isLinked(UUID uuid, Long longId) {
		if(uuid != null && longId != null)
			return LINKED_PLAYERS.containsKey(uuid) ? LINKED_PLAYERS.get(uuid).equals(longId) ? true : false : false;
		return false;
	}
}