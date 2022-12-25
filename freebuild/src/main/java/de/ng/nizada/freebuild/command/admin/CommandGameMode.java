package de.ng.nizada.freebuild.command.admin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;

public class CommandGameMode implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (sender instanceof Player) ? (Player) sender : null;
		
		if(args.length == 0) {
			if(sender.hasPermission("nizada.gamemode")) {
				if(player == null) {
					sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann ihren §aGameMode §7nicht ändern§8.");
					return true;
				}
				
				switch (player.getGameMode()) {
				case SPECTATOR:
					player.setGameMode(GameMode.SURVIVAL);
					break;
				case SURVIVAL:
					player.setGameMode(GameMode.ADVENTURE);
					break;
				case ADVENTURE:
					player.setGameMode(GameMode.CREATIVE);
					break;
				case CREATIVE:
					player.setGameMode(GameMode.SPECTATOR);
					break;
				}
				player.sendMessage(Freebuild.PREFIX + "§7Dein GameMode wurde zu " + getGameModeName(player.getGameMode()) + " §7geändert§8.");
				return true;
			}
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §aRechte §7um diesen command zu nutzen§8.");
			return true;
		}
		
		if(!sender.hasPermission("nizada.gamemode") && !sender.hasPermission("nizada.gamemode.other")) {
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §aRechte §7um diesen command zu nutzen§8.");
			return true;
		}
		
		List<GameMode> found = Arrays.asList(GameMode.values()).stream().filter(gm -> isGameMode(gm, args[0]) || gm.name().equalsIgnoreCase(args[0]) || gm.name().toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
		
		if(found.size() == 0) {
			sender.sendMessage(
					Freebuild.PREFIX + 
					"§7Der angegebene GameMode §c\"" + args[0] + "\" §7konnte nicht gefunden werden§8.\n" +
					Freebuild.PREFIX + "§7/gm §8[§a0§7/§21§7/§52§7/§D3§8]§8.");
			return true;
		}
		
		GameMode gameMode = found.get(0);
		
		if(args.length == 1) {
			if(player == null) {
				sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann ihren §aGameMode §7nicht ändern§8.");
				return true;
			}
			
			if(sender.hasPermission("nizada.gamemode")) {
				player.setGameMode(gameMode);
				player.sendMessage(Freebuild.PREFIX + "§7Dein GameMode wurde zu " + getGameModeName(player.getGameMode()) + " §7geändert§8.");
				return true;
			}
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §aRechte §7um diesen command zu nutzen§8.");
			return true;
		}
		
		if(!sender.hasPermission("nizada.gamemode.other")) {
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §aRechte §7um diesen command zu nutzen§8.");
			return true;
		}
		
		Player target = args.length > 1 ? Bukkit.getPlayer(args[1]) : null;
		
		if(target == null) {
			sender.sendMessage(Freebuild.PREFIX + "§7Der angegebene §aSpieler §7ist nicht online§8.");
			return true;
		}
		
		target.setGameMode(gameMode);
		
		if(player != null && target.equals(player))
			sender.sendMessage(Freebuild.PREFIX + "§7Dein GameMode wurde zu " + getGameModeName(player.getGameMode()) + " §7geändert§8.");
		else {
			target.sendMessage(Freebuild.PREFIX + "§7Dein GameMode wurde von " + (player == null ? "der §c" : "§a") + sender.getName() + " §7zu " + getGameModeName(gameMode) + " §7geändert§8.");
			sender.sendMessage(Freebuild.PREFIX + "§7Der GameMode von §a" + target.getName() + " §7wurde zu " + getGameModeName(gameMode) + " §7geändert§8.");
		}
		return true;
	}
	
	private static String getGameModeName(GameMode gameMode) {
		switch (gameMode) {
		case SURVIVAL:
			return "§aSurvival";
		case ADVENTURE:
			return "§2Adventure";
		case CREATIVE:
			return "§5Creative";
		case SPECTATOR:
			return "§dSpectator";

		default:
			return "§fUnknown";
		}
	}
	
	private static boolean isGameMode(GameMode gameMode, String value) {
		switch (gameMode) {
		case SURVIVAL:
			return value.equals("0");

		case CREATIVE:
			return value.equals("1");
			
		case ADVENTURE:
			return value.equals("2");
			
		case SPECTATOR:
			return value.equals("3");
		default:
			return false;
		}
	}
}