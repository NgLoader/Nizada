package de.ng.nizada.freebuild.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.help.HelpManager;
import de.ng.nizada.freebuild.home.HomeManager;
import de.ng.nizada.freebuild.scoreboard.ScoreboardManager;
import de.ng.nizada.freebuild.warp.WarpManager;

public class CommandFreebuildReload implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("nizada.reload")) {
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}
		if(args.length == 1)
			switch(args[0].toLowerCase()) {
			case "home":
			case "homes":
				HomeManager.HOME_MANAGER.reload();
				sender.sendMessage(Freebuild.PREFIX + "§aHomes §7wurde §7neugeladen§8.");
				return true;

			case "warp":
			case "warps":
				WarpManager.WARP_MANAGER.reload();
				sender.sendMessage(Freebuild.PREFIX + "§aWarps §7wurde §7neugeladen§8.");
				return true;

			case "help":
				HelpManager.HELP_MANAGER.reload();
				sender.sendMessage(Freebuild.PREFIX + "§aHelp §7wurde §7neugeladen§8.");
				return true;

			case "sb":
			case "scoreb":
			case "sboard":
			case "scoreboard":
				ScoreboardManager.SCOREBOARD_MANAGER.reloadScoreboard();
				sender.sendMessage(Freebuild.PREFIX + "§aScoreboard §7wurde §7neugeladen§8.");
				return true;

			case "all":
				HomeManager.HOME_MANAGER.reload();
				WarpManager.WARP_MANAGER.reload();
				HelpManager.HELP_MANAGER.reload();
				ScoreboardManager.SCOREBOARD_MANAGER.reloadScoreboard();
				sender.sendMessage(Freebuild.PREFIX + "§7Es wurden §aalles §7neugeladen§8.");
				return true;
				
			default:
				break;
			}
		sender.sendMessage(Freebuild.PREFIX + "§7/Freebuildreload §8<§7HOME§8|§7WARP§8|§7HELP§8|§7SCOREBOARD§8|§7ALL§8>");
		return true;
	}
}