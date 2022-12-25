package de.ng.nizada.freebuild.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;

public class CommandSpawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann sich nicht §cTeleportieren§8.");
			return true;
		}
		
		((Player) sender).teleport(Bukkit.getWorld("world").getSpawnLocation().add(.5, 0, .5));
		sender.sendMessage(Freebuild.PREFIX + "§7Du wurdest §aerfolgreich §7zum §aSpawn §7Teleportiert§8.");
		return true;
	}
}