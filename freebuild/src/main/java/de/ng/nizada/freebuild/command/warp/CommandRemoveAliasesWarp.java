package de.ng.nizada.freebuild.command.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.warp.Warp;
import de.ng.nizada.freebuild.warp.WarpManager;

public class CommandRemoveAliasesWarp implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann kein §cWarp §7löschen§8.");
			return true;
		}
		Player player = (Player) sender;
        
		if(!player.hasPermission("nizada.warp.create")) {
			player.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}
		
        if(args.length == 1) {
        	String alisesName = args[0];
        	
        	if(WarpManager.WARP_MANAGER.existWarp(alisesName.toLowerCase())) {
        		Warp warp = WarpManager.WARP_MANAGER.getWarp(alisesName.toLowerCase());
        		
        		if(warp.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(alisesName))) {
        			warp.getAliases().remove(warp.getAliases().stream().filter(alias -> alias.equalsIgnoreCase(alisesName)).findFirst().get());
        			WarpManager.WARP_MANAGER.saveWarp(warp);
                    player.sendMessage(Freebuild.PREFIX + "§7Du hast §aerfolgreich §7für denn §aWarp §8\"§a" + warp.getName() + "§8\" §7denn unternamen §8\"§a" + alisesName + "§8\" §centfernt§8.");
        		} else
                    player.sendMessage(Freebuild.PREFIX + "§7Dieser Untername f§r denn Warp §8\"§c" + warp.getName() + "§8\" §7exestiert nicht§8.");
        		return true;
        	}
            player.sendMessage(Freebuild.PREFIX + "§7Dieser §cWarp §7exestiert nicht§8.");
        } else {
        	player.sendMessage(Freebuild.PREFIX + "§7/RemoveAliasesWarp §8<§7Untername§8>");
        }
        return true;
    }
}