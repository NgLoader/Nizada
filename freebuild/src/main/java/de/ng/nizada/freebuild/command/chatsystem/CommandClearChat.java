package de.ng.nizada.freebuild.command.chatsystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.chatsystem.ChatLog;

public class CommandClearChat implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			if(sender.hasPermission("nizada.chatsystem.clearchat")) {
				for(int i = 0; i < 100; i++)
					for(Player player : Bukkit.getOnlinePlayers())
						player.sendMessage(" ");
				ChatLog.CHAT_LOG.clearMessages();
				
				for(Player player : Bukkit.getOnlinePlayers())
					if(player.hasPermission("nizada.chatsystem.clearchat.see"))
						player.sendMessage(Freebuild.PREFIX + "§7Der §aChat §7wurden von §8\"§a" + (sender instanceof Player ? "§a" : "§2") + sender.getName() + "§8\" §7gelöscht§8.");
					else
						player.sendMessage(Freebuild.PREFIX + "§7Der §aChat §7wurden §cgelöscht§8.");
				return true;
			}
		} else if(args.length == 1) {
			if(sender.hasPermission("nizada.chatsystem.clearchat.select")) {
				String ignore = args[0];
				
				for(int i = 0; i < 100; i++)
					for(Player player : Bukkit.getOnlinePlayers())
						player.sendMessage(" ");
				
				List<String> newChatMessages = new ArrayList<>();
				newChatMessages.clear();
				
				ChatLog.CHAT_LOG.getChatMessages().forEach(message -> {
					if(!ChatColor.stripColor(message).toLowerCase().contains(ignore.toLowerCase())) {
						Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(message));
						newChatMessages.add(message);
					}
				});
				ChatLog.CHAT_LOG.setChatMessages(newChatMessages);
				
				sender.sendMessage(Freebuild.PREFIX + "§7Du hast alle §aNachrichten §7mit denn inhalt §8\"§6" + ignore + "§8\" §cgelöscht§8.");
				for(Player all : Bukkit.getOnlinePlayers())
					if(all.hasPermission("nizada.chatsystem.clearchat.see") && !all.equals(sender))
						all.sendMessage(Freebuild.PREFIX + "§8\"" + (sender instanceof Player ? "§a" : "§c") + sender.getName() + "§8\" §7hat alles mit denn inhalt §8\"§a" + ignore + "§8\" §7gelöscht§8.");
				return true;
			}
		}
		sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
		return true;
	}
}