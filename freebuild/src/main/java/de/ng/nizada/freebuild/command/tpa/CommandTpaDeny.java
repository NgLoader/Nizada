package de.ng.nizada.freebuild.command.tpa;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.tpa.TpaManager;

public class CommandTpaDeny implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann keine §cTpa §7nutzen§8.");
			return true;
		}
		Player player = (Player) sender;
		if(args.length > 0) {
			Player target = Bukkit.getPlayer(args[0]);
			if(target != null)
				if(TpaManager.hasRequestAndRemove(target.getUniqueId(), player.getUniqueId()) != null) {
					player.sendMessage(Freebuild.PREFIX + "§7Die §aTeleportanfrage §7von §8\"§2" + target.getName() + "§8\" §7wurde §cabgelehnt§8.");
					target.sendMessage(Freebuild.PREFIX + "§7Die §aTeleportanfrage §7an §8\"§2" + player.getName() + "§8\" §7wurde §cabgelehnt§8.");
				} else
					player.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cTeleportanfrage §7von §8\"§c" + target.getName() + "§8\" §7bekommen§8.");
			else
				player.sendMessage(Freebuild.PREFIX + "§7Der angegebene §cSpieler §8\"§4" + args[0] + "§8\" §7ist nicht §cOnline§8.");
		} else
			player.sendMessage(Freebuild.PREFIX + "§8/§7tpadeny §8<§aSpieler§8>");
		return true;
	}
}