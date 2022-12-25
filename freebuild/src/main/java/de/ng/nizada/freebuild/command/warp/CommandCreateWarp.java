package de.ng.nizada.freebuild.command.warp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.warp.Warp;
import de.ng.nizada.freebuild.warp.WarpManager;

public class CommandCreateWarp implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann kein §cWarp §7erstellen§8.");
			return true;
		}
		Player player = (Player) sender;
        
		if(!player.hasPermission("nizada.warp.create")) {
			player.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}
		
        if(args.length >= 1) {
        	String warpName = args[0];
        	String permission = args.length > 1 ? args[1].equalsIgnoreCase("-") ? null : args[1] : null;
        	List<String> description = new ArrayList<>();
        	
        	for(int i = 2; i < args.length; i++)
        		description.add(args[i]);
        	
        	if(WarpManager.WARP_MANAGER.existWarp(warpName.toLowerCase())) {
                player.sendMessage(Freebuild.PREFIX + "§7Dieser §cWarp §7exestiert schon§8.");
        		return true;
        	}
        	
        	Warp warp = WarpManager.WARP_MANAGER.createWarp(warpName, player.getLocation(), permission, String.join(" ", description), new LinkedList<>());
            player.sendMessage(Freebuild.PREFIX + "§7Du hast erfolgreich denn §aWarp §8\"§a" + warp.getName() + "§8\" §aerstellt§8.");
        } else {
        	player.sendMessage(Freebuild.PREFIX + "§7/CreateWarp §8<§7name§8> §8[§7permission§8] §8[§7Beschreibung§8]");
        }
        return true;
    }
}