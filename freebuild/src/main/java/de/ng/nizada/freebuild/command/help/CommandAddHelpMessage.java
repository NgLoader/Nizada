package de.ng.nizada.freebuild.command.help;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.help.HelpManager;

public class CommandAddHelpMessage implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("nizada.help.addmessage")) {
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}
		
		if(args.length > 1) {
			String permission = args[0].equals("-") ? "" : args[0];
			String message = String.join(" ", (String[]) ArrayUtils.remove(args, 0));
			
			HelpManager.HELP_MANAGER.addLine(message, permission);
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast die §aNachricht §8\"§7" + message + "§8\" §7mit dem §aRecht §8\"§7" + permission + "§8\" §7zur Help liste §ahinzugefügt§8.");
			return true;
		}
		
		sender.sendMessage(Freebuild.PREFIX + "§7/AddHelpMessage §8<§7Permission§8> §8<§7Message§8>§8.");
		return true;
	}
}