package de.ng.nizada.freebuild.command.help;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.ng.nizada.freebuild.help.HelpManager;

public class CommandHelp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(HelpManager.HELP_MANAGER.buildHelpMessage(sender));
		return true;
	}
}