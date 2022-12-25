package de.ng.nizada.freebuild.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.vanish.Vanish;

public class CommandVanish implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann sich nicht §cunsichtbar §7machen§8.");
			return true;
		}
		Player player = (Player) sender;
		if(args.length > 0) {
			if(sender.hasPermission("nizada.vanish.other")) {
				Player target = Bukkit.getPlayer(args[0]);
				if(target != null) {
					if(Vanish.VANISH.isVanish(target)) {
						target.sendMessage(Freebuild.PREFIX + "§8\"§4" + sender.getName() + "§8\" §7hat dich §csichtbar §7gemacht§8.");
						sender.sendMessage(Freebuild.PREFIX + "§7Du hast §8\"§4" + target.getName() + "§8\" §csichtbar §7gemacht§8.");
						Vanish.VANISH.removePlayer(target);
					} else {
						target.sendMessage(Freebuild.PREFIX + "§8\"§4" + sender.getName() + "§8\" §7hat dich §aunsichtbar §7gemacht§8.");
						sender.sendMessage(Freebuild.PREFIX + "§7Du hast §8\"§4" + target.getName() + "§8\" §aunsichtbar §7gemacht§8.");
						Vanish.VANISH.addPlayer(player);
					}
				} else
					sender.sendMessage(Freebuild.PREFIX + "§7Der angegebene §cSpieler §8\"§4" + args[0] + "§8\" ist nicht §conline§8.");
			} else
				sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
		} else if(sender.hasPermission("nizada.vanish")) {
			if(Vanish.VANISH.isVanish(player)) {
				Vanish.VANISH.removePlayer(player);
				sender.sendMessage(Freebuild.PREFIX + "§7Du bist nun §cnicht §7mehr §cunsichtbar§8.");
				return true;
			}
			Vanish.VANISH.addPlayer(player);
			sender.sendMessage(Freebuild.PREFIX + "§7Du bist nun §aunsichtbar§8.");
		} else
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
		return true;
	}
}