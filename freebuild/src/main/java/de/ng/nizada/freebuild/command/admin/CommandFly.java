package de.ng.nizada.freebuild.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;

public class CommandFly implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann nicht §cFliegen§8.");
			return true;
		}
		
		if(args.length == 0) {
			if(sender.hasPermission("nizada.fly")) {
				Player player = (Player) sender;
				
				player.setAllowFlight(!player.getAllowFlight());
				player.sendMessage(Freebuild.PREFIX + "§7Fliegen wurden erfolgreich " + (player.getAllowFlight() ? "§aAktiviert" : "§cDeaktiviert") + "§8.");
			} else 
				sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		} else if(args.length == 1) {
			if(sender.hasPermission("nizada.fly.other")) {
				Player target = Bukkit.getPlayer(args[0]);
				
				if(target != null) {
					target.setAllowFlight(!target.getAllowFlight());
					target.sendMessage(Freebuild.PREFIX + "§7Fliegen wurden von §8\"§a" + target.getName() + "§8\" §7erfolgreich " + (target.getAllowFlight() ? "§aAktiviert" : "§cDeaktiviert") + "§8.");
					sender.sendMessage(Freebuild.PREFIX + "§7Fliegen wurden für §8\"§a" + target.getName() + "§8\" §7erfolgreich " + (target.getAllowFlight() ? "§aAktiviert" : "§cDeaktiviert") + "§8.");
				} else
					sender.sendMessage(Freebuild.PREFIX + "§7Der angebene Spieler §8\"§c" + args[0] + "§8\" §7ist nicht §cOnline§8.");
			} else
				sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}
		sender.sendMessage(Freebuild.PREFIX + "§7/Fly §8[§7Spieler§8]§8.");
		return true;
	}
}