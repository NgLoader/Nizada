package de.ng.nizada.freebuild.command.help;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.help.HelpManager;
import de.ng.nizada.freebuild.help.HelpMessage;

public class CommandUpdateHelpMessage implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("nizada.help.addmessage")) {
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}
		
		if(args.length > 2) {
			int line;
			try {
				line = Integer.parseInt(args[0]);
			} catch(NumberFormatException ex) {
				sender.sendMessage(Freebuild.PREFIX + "§7§8\"§c" + args[0] + "§8\" §7ist keine valide §cZahl§8.");
				return true;
			}
			HelpMessage message = HelpManager.HELP_MANAGER.getLine(line);
			
			if(message != null) {
				String permission = args[1].equals("*") ? null : args[1].equals("-") ? "" : args[1];
				String newMessage = String.join(" ", (String[]) ArrayUtils.remove(ArrayUtils.remove(args, 0), 0));
				
				if(permission == null || permission.equals(message.getPermission()))
					HelpManager.HELP_MANAGER.setLine(0, newMessage);
				else
					HelpManager.HELP_MANAGER.setLine(line, newMessage, permission);
				sender.sendMessage(Freebuild.PREFIX + "§7Du hast die §aNachricht §8\"§7" + message.getMessage() + "§8\" §7erfolgreich §aAktualisiert§8.");
			} else
				sender.sendMessage(Freebuild.PREFIX + "§7Die angegebene §cNummer §8\"§c" + line + "§8\" §7exestiert §cnicht§8.");
			return true;
		}
		
		sender.sendMessage(Freebuild.PREFIX + "§7/UpdateHelpMessage §7<§8Line§7> §8<§7Permission§8> §7<§8NeueMessage§7>§8.");
		return true;
	}
}