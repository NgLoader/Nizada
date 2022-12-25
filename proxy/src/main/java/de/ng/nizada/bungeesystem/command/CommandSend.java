package de.ng.nizada.bungeesystem.command;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;

import de.ng.nizada.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class CommandSend extends Command implements TabExecutor {

	public static final ServerInfo SERVER_INFO_BUILD = BungeeSystem.instance.getProxy().getServerInfo("build");

	public CommandSend() {
		super("send", "nizada.command.send");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length != 2) {
			sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7/Send §8<§7server§8|§7player§8|§7all§8|§7current§8> §8<§7target§8>"));
			return;
		}
		
		ServerInfo target = ProxyServer.getInstance().getServerInfo(args[1].toLowerCase());
		
		if(target == null) {
			sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Der §7angegebene §7Server §8\"§c" + args[1] + "§8\" §7wurden §7nicht §7gefunden§8."));
			return;
		}
		
		switch (args[0]) {
		case "all":
			ProxyServer.getInstance().getPlayers().forEach(player -> summon(player, target, sender));
			sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7hast §7alle §7aufen §7Server §8\"§a" + target.getName().substring(0, 1).toUpperCase() + target.getName().substring(1).toLowerCase() + "§8\" §agesendet§8."));
			return;
			
		case "current":
			if(!(sender instanceof ProxiedPlayer)) {
				sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Die §7Console §7kann §7auf §7keinen §cServer §7sein§8."));
				return;
			}
			((ProxiedPlayer)sender).getServer().getInfo().getPlayers().forEach(player -> summon(player, target, sender));
			sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7hast §7alle §7von §8\"§c" + ((ProxiedPlayer)sender).getServer().getInfo().getName().substring(0, 1).toUpperCase() + ((ProxiedPlayer)sender).getServer().getInfo().getName().substring(1).toLowerCase() + "§8\" §7auf §8\"§a" + target.getName().substring(0, 1).toUpperCase() + target.getName().substring(1).toLowerCase() + "§8\" §agesendet§8."));
			return;
			
		default:
			ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(args[0].toLowerCase());
			
			if(serverInfo != null) {
				serverInfo.getPlayers().forEach(player -> summon(player, target, sender));
				sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7hast §7alle §7von §8\"§c" + serverInfo.getName().substring(0, 1).toUpperCase() + serverInfo.getName().substring(1).toLowerCase() + "§8\" §7auf §8\"§a" + target.getName().substring(0, 1).toUpperCase() + target.getName().substring(1).toLowerCase() + "§8\" §agesendet§8."));
				return;
			}
			
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
			if(player != null) {
				summon(player, target, sender);
				sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7hast §7den §aSpieler §8\"§a" + player.getName() + "§8\" §7aufen §7Server §8\"§a" + target.getName().substring(0, 1).toUpperCase() + target.getName().substring(1).toLowerCase() + "§8\" §agesendet§8."));
				return;
			}
			break;
		}
		sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7/Send §8<§7server§8|§7player§8|§7all§8|§7current§8> §8<§7target§8>"));
	}
	
	private boolean summon(ProxiedPlayer player, ServerInfo target, CommandSender sender) {
		if(player.getServer() == null || player.getServer().getInfo().equals(target))
			return false;
		
			player.connect(target);
			if(player.hasPermission("nizada.command.send.see") && !player.equals(sender))
				player.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7wurdest §7von §8\"" + (sender instanceof ProxiedPlayer ? "§a" : "§c") + sender.getName() + "§8\" §7aufen §7Server §8\"§a" + target.getName().substring(0, 1).toUpperCase() + target.getName().substring(1).toLowerCase() + "§8\" §7gesendet§8."));
			else
				player.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7wurdest §7aufen §8\"§a" + target.getName().substring(0, 1).toUpperCase() + target.getName().substring(1).toLowerCase() + "§8\" §7Server §agesendet§8."));
		return false;
	}
	
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
		if (args.length > 2 || args.length == 0)
			return ImmutableSet.of();
		Set<String> matches = new HashSet<String>();
		
		String search = (args.length == 1 ? args[0] : args[1]).toLowerCase();
		matches.addAll(ProxyServer.getInstance().getServers().values().stream().filter(serverInfo -> serverInfo.getName().toLowerCase().startsWith(search)).map(serverInfo -> serverInfo.getName()).collect(Collectors.toList()));
		
		if (args.length == 1) {
			matches.addAll(ProxyServer.getInstance().getPlayers().stream().filter(player -> player.getName().toLowerCase().startsWith(search)).map(player -> player.getName()).collect(Collectors.toList()));
			
			if ("all".startsWith(search))
				matches.add("all");
			if ("current".startsWith(search))
				matches.add("current");
		}
		
		return matches;
	}
}