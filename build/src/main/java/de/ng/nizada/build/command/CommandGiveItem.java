package de.ng.nizada.build.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.build.Build;
import de.ng.nizada.gamecore.util.ItemFactory;

public class CommandGiveItem implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Build.PREFIX + "�7Die Console kann ihren �aGameMode �7nicht �ndern�8.");
			return true;
		}
		Player player = (Player) sender;
		
		if(args.length == 0) {
			player.sendMessage(Build.PREFIX + "§7Bitte gebe ein Item an was du haben möchtest.\n"
					+ Build.PREFIX + "§7/give §8[§7ITEM-ID§8/§7NAME§8]");
			return true;
		}
		
		String arg = String.join(" ", args);
		
		Material material = arg.matches("-?[0-9]+") ? Material.getMaterial(Integer.valueOf(arg)) : Material.getMaterial(arg.replaceAll("_", " ").toUpperCase());
		
		if(material == null) {
			player.sendMessage(Build.PREFIX + "§7Das angegebene §aItem §7konnte nichte gefunden werden§8.\n"
					+ Build.PREFIX + "§7/give §8[§7ITEM-ID§8/§7NAME§8]");
			return true;
		}
		
		player.getInventory().addItem(new ItemFactory(material).setDisplayName("�a" + material.name().substring(0, 1).toUpperCase() + material.name().substring(1, material.name().length()).toLowerCase()).build());
		return true;
	}
}