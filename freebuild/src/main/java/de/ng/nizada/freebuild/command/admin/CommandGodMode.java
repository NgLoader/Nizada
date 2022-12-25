package de.ng.nizada.freebuild.command.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import de.ng.nizada.freebuild.Freebuild;

public class CommandGodMode implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann nicht §cFliegen§8.");
			return true;
		}
		Player player = (Player) sender;
		
		if(player.hasPermission("nizada.godmode")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("an") || args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("1")) {
					if(player.hasMetadata("NIZADA_GODMODE_LEVITATION"))
						player.sendMessage(Freebuild.PREFIX + "§7Du hast diese fehigkeit bereits §aAktiviert§8.");
					else {
						player.setMetadata("NIZADA_GODMODE_LEVITATION", new FixedMetadataValue(Freebuild.instance, true));
						player.sendMessage(Freebuild.PREFIX + "§7Du hast diese fehigkeit §aAktiviert§8.");
					}
				} else {
					if(player.hasMetadata("NIZADA_GODMODE_LEVITATION")) {
						player.removeMetadata("NIZADA_GODMODE_LEVITATION", Freebuild.instance);
						player.sendMessage(Freebuild.PREFIX + "§7Du hast diese fehigkeit §cDeaktiviert§8.");
					} else
						player.sendMessage(Freebuild.PREFIX + "§7Du hast diese fehigkeit bereits §cDeaktiviert§8.");
				}
				return true;
			}
			
			if(player.hasMetadata("NIZADA_GODMODE")) {
				player.removeMetadata("NIZADA_GODMODE", Freebuild.instance);
				player.sendMessage(Freebuild.PREFIX + "§6G§eod§6M§eode §7wurde §cdeaktiviert§8.");
			} else {
				player.setMetadata("NIZADA_GODMODE", new FixedMetadataValue(Freebuild.instance, true));
				player.sendMessage(Freebuild.PREFIX + "§6G§eod§6M§eode §7wurde §aaktiviert§8.");
			}
			return true;
		} else
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
		return true;
	}
}