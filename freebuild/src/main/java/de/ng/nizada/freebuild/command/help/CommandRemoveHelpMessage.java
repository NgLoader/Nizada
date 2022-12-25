package de.ng.nizada.freebuild.command.help;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.help.HelpManager;
import de.ng.nizada.freebuild.help.HelpMessage;

public class CommandRemoveHelpMessage implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("nizada.help.addmessage")) {
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}
		
		if(args.length > 0) {
			int line;
			try {
				line = Integer.parseInt(args[0]);
			} catch(NumberFormatException ex) {
				sender.sendMessage(Freebuild.PREFIX + "§7§8\"§c" + args[0] + "§8\" §7ist keine valide §cZahl§8.");
				return true;
			}
			HelpMessage message = HelpManager.HELP_MANAGER.getLine(line);
			
			if(message != null) {
				HelpManager.HELP_MANAGER.removeLine(line);
				sender.sendMessage(Freebuild.PREFIX + "§7Du hast die §aNachricht §8\"§7" + message.getMessage() + "§8\" §7erfolgreich §cgelöscht§8.");
			} else
				sender.sendMessage(Freebuild.PREFIX + "§7Die angegebene §cNummer §8\"§c" + line + "§8\" §7exestiert §cnicht§8.");
			return true;
		}
		
		sender.sendMessage(Freebuild.PREFIX + "§7/RemoveHelpMessage §7<§8Line§7>§8.");
		return true;
	}
}