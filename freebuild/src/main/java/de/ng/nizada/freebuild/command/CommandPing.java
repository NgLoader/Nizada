package de.ng.nizada.freebuild.command;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;

public class CommandPing implements CommandExecutor {

	private static final HashMap<UUID, Long> COULDOWN = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console hat keinen §4Ping§8.");
			return true;
		}
		Player player = (Player) sender;

		if(COULDOWN.containsKey(player.getUniqueId())) {
			if(COULDOWN.get(player.getUniqueId()) > System.currentTimeMillis()) {
				player.sendMessage(Freebuild.PREFIX + "§7Bitte warte noch §8\"§c" + ((int) ((COULDOWN.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000)) + "§8\" §7Sekunden§8.");
				return true;
			} else
				COULDOWN.remove(player.getUniqueId());
		}
		COULDOWN.put(player.getUniqueId(), System.currentTimeMillis() + (5000));

		sender.sendMessage(Freebuild.PREFIX + "§7Dein §aPing §7beträgt §a" + ((CraftPlayer)player).getHandle().ping + "§cms§8.");
		return true;
	}
}