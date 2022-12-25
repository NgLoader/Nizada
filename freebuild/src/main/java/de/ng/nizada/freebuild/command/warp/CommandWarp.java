package de.ng.nizada.freebuild.command.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.warp.Warp;
import de.ng.nizada.freebuild.warp.WarpManager;

public class CommandWarp implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann keine §cWarp §7nutzen§8.");
			return true;
		}
		Player player = (Player) sender;
        
        if(args.length == 1) {
        	Warp warp = WarpManager.WARP_MANAGER.getWarp(args[0].toLowerCase());
        	
        	if(warp == null) {
                player.sendMessage(Freebuild.PREFIX + "§7Dieser §cWarp §7exestiert nicht§8.");
        		return true;
        	}
        	
        	if(warp.getPermission() != null && !warp.getPermission().equals("")) {
        		if(!player.hasPermission(warp.getPermission())) {
        			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §4Rechte §7um diesen §cWarp §7zu nutzen§8.");
        			return true;
        		}
        	} else if(!player.hasPermission("nizada.warp.default")) {
    			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §4Rechte §7um diesen §cWarp §7zu nutzen§8.");
    			return true;
        	}
        	
            player.teleport(warp.getLocation());
            player.sendMessage(Freebuild.PREFIX + "§7Du wurdest §aerfolgreich §7zum Warp §8\"§a" + warp.getName() + "§8\" §7teleportiert§8.");
        } else {
        	player.sendMessage(Freebuild.PREFIX + "§7/Warp §8<§7name§8>");
        }
        return true;
    }
}