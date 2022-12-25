package de.ng.nizada.bungeesystem.command;

import java.util.Collections;
import java.util.stream.Collectors;

import de.ng.nizada.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class CommandServer extends Command implements TabExecutor {

	public static final ServerInfo SERVER_INFO_BUILD = BungeeSystem.instance.getProxy().getServerInfo("build");

	public CommandServer() {
		super("server", "nizada.command.server");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length == 0) {
			if(sender instanceof ProxiedPlayer)
				sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7bist §7grade §7auf §8\"§a" + ((ProxiedPlayer)sender).getServer().getInfo().getName().substring(0, 1).toUpperCase() + ((ProxiedPlayer)sender).getServer().getInfo().getName().substring(1).toLowerCase() + "§8\"§8."));
			
			for(ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()) {
				if(!serverInfo.canAccess(sender))
					continue;
				
				TextComponent component = new TextComponent(BungeeSystem.PREFIX + "     §8- §a" + serverInfo.getName() + " §8(§7" + serverInfo.getPlayers().size() + "§8)");
				component.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§aKlicke zum verbinden").create()));
				component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + serverInfo.getName()));
				sender.sendMessage(component);
			}
		} else {
			if(!(sender instanceof ProxiedPlayer)) {
				sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7bist §7kein §cSpieler§8."));
				return;
			}
			ProxiedPlayer player = (ProxiedPlayer) sender;
			ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(args[0]);
			
			if(serverInfo != null)
				if(serverInfo.canAccess(sender))
					player.connect(serverInfo);
				else
					sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7hast §7keine §cRechte §7um §7diesen §cServer §7zu §cbetreten§8."));
			else
				sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Der angegebene Server §8\"§a" + args[0] + "§8\" §7wurde §7nicht §7gefunden§8."));
		}
	}
	
	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
		if(args.length > 1)
			return Collections.emptyList();
		return ProxyServer.getInstance().getServers().values().stream().filter(serverInfo -> serverInfo.getName().toLowerCase().startsWith(args[0].toLowerCase())).map(serverInfo -> serverInfo.getName()).collect(Collectors.toSet());
		
	}
}