package de.ng.nizada.build.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.build.Build;
import de.ng.nizada.gamecore.util.ItemFactory;

public class CommandHead implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Build.PREFIX + "§7Die Console kann ihren §aGameMode §7nicht ändern§8.");
			return true;
		}
		Player player = (Player) sender;
		
		if(args.length == 0) {
			player.getInventory().addItem(new ItemFactory(Material.SKULL_ITEM, (byte)3).setSkullOwner(player.getName()).setDisplayName("§7Kopf§8: §a" + player.getName()).build());
			return true;
		}
		player.getInventory().addItem(new ItemFactory(Material.SKULL_ITEM, (byte)3).setSkullOwner(args[0]).setDisplayName("§7Kopf§8: §a" + args[0]).build());
		return true;
	}
}