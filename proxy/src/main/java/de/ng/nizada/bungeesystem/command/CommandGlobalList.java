package de.ng.nizada.bungeesystem.command;

import de.ng.nizada.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Command;

public class CommandGlobalList extends Command {

	public static final ServerInfo SERVER_INFO_BUILD = BungeeSystem.instance.getProxy().getServerInfo("build");

	public CommandGlobalList() {
		super("glist", "nizada.command.globallist", "globallist", "gl");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Derzeitig §aOnline§8: " + ProxyServer.getInstance().getOnlineCount()));
		for(ServerInfo serverInfo : ProxyServer.getInstance().getServers().values())
			if(!serverInfo.canAccess(sender))
				continue;
			else {
				TextComponent component = new TextComponent(BungeeSystem.PREFIX + "     §8- §a" + serverInfo.getName() + " §8(§7" + serverInfo.getPlayers().size() + "§8)");
				component.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§aKlicke zum verbinden").create()));
				component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + serverInfo.getName()));
				sender.sendMessage(component);
			}
	}
}