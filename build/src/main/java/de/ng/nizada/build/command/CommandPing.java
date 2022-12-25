package de.ng.nizada.build.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.ng.nizada.build.Build;

public class CommandPing implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Build.PREFIX + "§7Die §aConsole besitzt keinen §aPing§7.");
			return true;
		}
		sender.sendMessage(Build.PREFIX + "§aDein §aPing §aist§8: �7" + (((CraftPlayer)sender).getHandle().ping));
		return true;
	}
}