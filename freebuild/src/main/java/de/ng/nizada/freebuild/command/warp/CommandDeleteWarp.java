package de.ng.nizada.freebuild.command.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.warp.Warp;
import de.ng.nizada.freebuild.warp.WarpManager;

public class CommandDeleteWarp implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("nizada.warp.delete")) {
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}
		
        if(args.length == 1) {
        	String warpName = args[0].toLowerCase();
        	
        	if(!WarpManager.WARP_MANAGER.existWarp(warpName)) {
        		sender.sendMessage(Freebuild.PREFIX + "§7Dieser §cWarp §7exestiert nicht§8.");
        		return true;
        	}
        	Warp warp = WarpManager.WARP_MANAGER.deleteWarp(warpName);
        	sender.sendMessage(Freebuild.PREFIX + "§7Du hast erfolgreich denn §aWarp §8\"§a" + warp.getName() + "§8\" §cgelöscht§8.");
        } else {
        	sender.sendMessage(Freebuild.PREFIX + "§7/DeleteWarp §8<§7name§8>");
        }
        return true;
    }
}