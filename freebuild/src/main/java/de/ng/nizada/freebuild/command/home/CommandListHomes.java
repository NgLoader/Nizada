package de.ng.nizada.freebuild.command.home;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.home.Home;
import de.ng.nizada.freebuild.home.HomeManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandListHomes implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		UUID targetUUID = null;
		Collection<Home> selectedHomes = null;
		if(args.length > 0 && player.hasPermission("nizada.home.list.other")) {
			if(args[0].length() > 16)
				try {
					targetUUID = UUID.fromString(args[0]);
				} catch(NumberFormatException ex) {
					sender.sendMessage(Freebuild.PREFIX + "§7Die angegebene §cUUID §8\"§4" + args[0] + "§8\" §7exestiert §cnicht§8.");
					return true;
				}
			else {
				Player target = Bukkit.getPlayer(args[0]);
				if(target != null)
					targetUUID = target.getUniqueId();
				else {
					sender.sendMessage(Freebuild.PREFIX + "§7Der angegebene §cSpieler §8\"§4" + args[0] + "§8\" §7ist nicht §cOnline§8.");
					return true;
				}
			}
			selectedHomes = HomeManager.HOME_MANAGER.getHomesFromPlayer(targetUUID).values();
		}

		if((selectedHomes != null && selectedHomes.isEmpty()) || (selectedHomes == null && HomeManager.HOME_MANAGER.getHomesFromPlayer(player.getUniqueId()).values().isEmpty())) {
			player.sendMessage(Freebuild.PREFIX + "§7Keine §cHomes §7gefunden§8.");
			return true;
		}

		player.sendMessage(Freebuild.PREFIX + "§8[]§7------§a{ §2Home Liste §a}§7------§8[]");
		player.sendMessage(Freebuild.PREFIX);

		if(selectedHomes != null) {
			player.sendMessage(Freebuild.PREFIX + "§7Homes von§8: §a" + targetUUID.toString());
			player.sendMessage(Freebuild.PREFIX);
		}

		for(Home home : selectedHomes != null ? selectedHomes : HomeManager.HOME_MANAGER.getHomesFromPlayer(player.getUniqueId()).values()) {
			TextComponent message = new TextComponent(Freebuild.PREFIX + "§8- §a" + home.getName() + (home.getDescription().isEmpty() ? "" : " §8(§7" + home.getDescription() + "§8)"));

			TextComponent teleport = new TextComponent("§aTeleportieren");
			teleport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aKlick zum §2Teleportieren").create()));
			teleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/home " + (selectedHomes != null ? targetUUID.toString() + " " : "") + home.getName()));

			TextComponent delete = new TextComponent("§cLöschen");
			delete.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cKlick zum §4Löschen").create()));
			delete.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/delhome " + home.getName() + (selectedHomes != null ? " " + targetUUID.toString() : "")));

			message.addExtra(new TextComponent("\n" + Freebuild.PREFIX + "     §8• "));
			message.addExtra(teleport);
			message.addExtra(new TextComponent("\n" + Freebuild.PREFIX + "     §8• "));
			message.addExtra(delete);
			message.addExtra(new TextComponent("\n" + Freebuild.PREFIX));

			player.spigot().sendMessage(message);
		}

//		player.sendMessage(Freebuild.PREFIX);
		player.sendMessage(Freebuild.PREFIX + "§8[]§7------§a{ §2Home Liste §a}§7------§8[]");
        return true;
    }
}