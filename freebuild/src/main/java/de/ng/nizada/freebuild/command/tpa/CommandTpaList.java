package de.ng.nizada.freebuild.command.tpa;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.tpa.TpaCache;
import de.ng.nizada.freebuild.tpa.TpaManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandTpaList implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann keine §cTpa §7nutzen§8.");
			return true;
		}
		Player player = (Player) sender;
		if(TpaManager.getRequests().containsKey(player.getUniqueId()) && !TpaManager.getRequests().get(player.getUniqueId()).isEmpty()) {
			player.sendMessage(Freebuild.PREFIX + "§8[]§7------§a{ §2Teleport anfragen liste §a}§7------§8[]");
			player.sendMessage(Freebuild.PREFIX + " ");
			for(TpaCache tpaCache : TpaManager.getRequests().get(player.getUniqueId()))
				if(tpaCache.duration > System.currentTimeMillis()) {
					TextComponent message = new TextComponent(Freebuild.PREFIX + "     §8- " + tpaCache.senderName + " §8| ");
					TextComponent accept = new TextComponent("§a✔");
					TextComponent deny = new TextComponent("§cX");

					accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aNehme die §2Teleportanfrage §avon §8\"§2" + player.getName() + "§8\" §aan").create()));
					accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaaccept " + tpaCache.senderName));

					deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cLehne die §4Teleportanfrage §cvon §8\"§4" + player.getName() + "§8\" §cab").create()));
					deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpadeny " + tpaCache.senderName));

					message.addExtra(accept);
					message.addExtra(new TextComponent(" §8- "));
					message.addExtra(deny);

					player.spigot().sendMessage(message);
				}
			player.sendMessage(Freebuild.PREFIX + " ");
			player.sendMessage(Freebuild.PREFIX + "§8[]§7------§a{ §2Teleport anfragen liste §a}§7------§8[]");
		} else
			player.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cTeleportanfragen§8.");
		return true;
	}
}