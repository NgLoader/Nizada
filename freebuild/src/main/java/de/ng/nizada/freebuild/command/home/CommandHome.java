package de.ng.nizada.freebuild.command.home;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.home.Home;
import de.ng.nizada.freebuild.home.HomeManager;

public class CommandHome implements CommandExecutor {

	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann kein §cHome §7nutzen§8.");
			return true;
		}
		Player player = (Player) sender;
		if(!player.hasPermission("nizada.home.use")) {
			player.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}

        if(args.length > 0) {
			UUID targetUUID = null;
    		if(args.length > 1 && player.hasPermission("nizada.home.use.other")) {
    			if(args[0].length() > 16)
    				targetUUID = UUID.fromString(args[0]);
    			else {
    				Player target = Bukkit.getPlayer(args[0]);
    				if(target != null)
    					targetUUID = target.getUniqueId();
    				else {
    					sender.sendMessage(Freebuild.PREFIX + "§7Der angegebene §cSpieler §8\"§4" + args[0] + "§8\" ist nicht §cOnline§8.");
    					return true;
    				}
    			}
    		}
        	Home home = HomeManager.HOME_MANAGER.getHome(targetUUID != null ? targetUUID : player.getUniqueId(), args[targetUUID != null ? 1 : 0].toLowerCase());

        	if(home == null) {
                player.sendMessage(Freebuild.PREFIX + "§7Dieser §cHome §7exestiert nicht§8.");
        		return true;
        	}

            player.teleport(home.getLocation());
            player.sendMessage(Freebuild.PREFIX + "§7Du wurdest §aerfolgreich §7zum Home §8\"§a" + home.getName() + "§8\" §7teleportiert§8.");
        } else
        	player.sendMessage(Freebuild.PREFIX + "§7/Home §8<§7name§8>");
        return true;
    }
}