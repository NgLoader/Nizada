package de.ng.nizada.freebuild.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.gamecore.util.CustomPlayerUtil;
import net.minecraft.server.v1_12_R1.Packet;

public class CommandChangeName implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 1 && sender.hasPermission("nizada.change.name")) {
			if(sender.hasPermission("nizada.change.name"))
				if(sender instanceof Player) {
					Player player = (Player) sender;
					
					player.setDisplayName(args[0]);
					changeName(player, args[0]);
					
					player.sendMessage(Freebuild.PREFIX + "§7Du hast deinen Namen erfolgreich zu §8\"§a" + args[0] + "§8\" §7geändert§8.");
				} else
					sender.sendMessage(Freebuild.PREFIX + "§7Die §cConsole §7kann ihren §cnamen §7nicht ändern§8.");
			else
				sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		} else if(args.length == 2 && sender.hasPermission("nizada.change.name.other")) {
			Player target = Bukkit.getPlayer(args[1]);
			
			if(target != null) {
				target.setDisplayName(args[0]);
				changeName(target, args[0]);
				
				if(target.equals(sender))
					sender.sendMessage(Freebuild.PREFIX + "§7Du hast deinen Namen erfolgreich zu §8\"§a" + args[0] + "§8\" §7geändert§8.");
				else {
					sender.sendMessage(Freebuild.PREFIX + "§7Du hast denn Namen von §8\"§a" + target.getName() + "§8\" §7zu §8\"§a" + args[0] + "§8\" §7geändert§8.");
					target.sendMessage(Freebuild.PREFIX + "§7Dein Name wurden von §8\"" + (sender instanceof Player ? "§a" : "§c") + sender.getName() + "§8\" §7zu §8\"§a" + args[0] + "§8\" §7geändert§8.");
				}
			} else
				sender.sendMessage(Freebuild.PREFIX + "§7Der Angegebene Spieler §8\"§c" + args[1] + "§8\" §7ist nicht §cOnline§8.");
			return true;
		}
		if(sender.hasPermission("nizada.change.name") || sender.hasPermission("nizada.change.name.other"))
			sender.sendMessage(Freebuild.PREFIX + "§8/§7Changename §8<§7NeuerName§8> §8[§7Spieler§8]");
		else
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
		return true;
	}
	
	private void changeName(Player player, String newName) {
		Packet<?> packetWithPerm = CustomPlayerUtil.addToTabList(player, (!player.getName().equals(newName) ? "§8{§7" + player.getName() + "§8} §7" : "") + newName);
		Packet<?> packetWithOutPerm = CustomPlayerUtil.addToTabList(player, newName);
		
		for(Player all : Bukkit.getOnlinePlayers())
				CustomPlayerUtil.sendPacket(all, all.hasPermission("nizada.change.name.see") ? packetWithPerm : packetWithOutPerm);
	}
}