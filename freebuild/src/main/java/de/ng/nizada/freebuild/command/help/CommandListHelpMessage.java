package de.ng.nizada.freebuild.command.help;

import java.util.Comparator;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.help.HelpManager;
import de.ng.nizada.freebuild.help.HelpMessage;

public class CommandListHelpMessage implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("nizada.help.addmessage")) {
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}
		
		if(HelpManager.HELP_MANAGER.getHelpMessage().isEmpty())
			sender.sendMessage(Freebuild.PREFIX + "§7Die §aHelp §7liste ist §cleer§8.");
		else
			sender.sendMessage(String.join("\n", HelpManager.HELP_MANAGER.getHelpMessage().stream()
					.sorted(Comparator.comparing(HelpMessage::getLineNumber))
					.map(helpMessage ->
						"§7" + helpMessage.getLineNumber()
						+ "§8: §8\"§a" + helpMessage.getMessage()
						+ "§8\"" + (helpMessage.getPermission().isEmpty() ? "" : " §8[§7" + helpMessage.getPermission() + "§8]"))
					.collect(Collectors.toList())));
		return true;
	}
}