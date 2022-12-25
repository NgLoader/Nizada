package de.ng.nizada.freebuild.command.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.warp.Warp;
import de.ng.nizada.freebuild.warp.WarpManager;

public class CommandCreateAliasesWarp implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann kein §cWarp §7erstellen8.");
			return true;
		}
		Player player = (Player) sender;
        
		if(!player.hasPermission("nizada.warp.create")) {
			player.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}
		
        if(args.length == 2) {
        	String warpName = args[0];
        	String alisesName = args[1];
        	
        	if(WarpManager.WARP_MANAGER.existWarp(warpName.toLowerCase())) {
        		Warp warp = WarpManager.WARP_MANAGER.getWarp(warpName.toLowerCase());
        		
        		if(!warp.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(alisesName))) {
        			warp.getAliases().add(alisesName);
        			WarpManager.WARP_MANAGER.saveWarp(warp);
                    player.sendMessage(Freebuild.PREFIX + "§7Du hast §aerfolgreich §7für denn §aWarp §8\"§a" + warp.getName() + "§8\" §7denn unternamen §8\"§a" + alisesName + "§8\" §ahinzugefügt§8.");
        		} else
                    player.sendMessage(Freebuild.PREFIX + "§7Dieser Untername für denn Warp §8\"§c" + warp.getName() + "§8\" §7exestiert schon§8.");
        		return true;
        	}
            player.sendMessage(Freebuild.PREFIX + "§7Dieser §cWarp §7exestiert nicht§8.");
        } else {
        	player.sendMessage(Freebuild.PREFIX + "§7/CreateAliasesWarp §8<§7warp name§8> §8<§7Untername§8>");
        }
        return true;
    }
}