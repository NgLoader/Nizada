package de.ng.nizada.bungeesystem.command;

import de.ng.nizada.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandFind extends Command {

	public static final ServerInfo SERVER_INFO_BUILD = BungeeSystem.instance.getProxy().getServerInfo("build");

	public CommandFind() {
		super("find", "nizada.command.find", "search");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length != 1)
			sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Bitte §7gebe §7einen §cSpieler §7an§8."));
		else {
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
			
			if(player != null&& player.getServer() != null) {
				sender.sendMessage(new ComponentBuilder(BungeeSystem.PREFIX + "§7Der §aSpieler §8\"§a" + player.getName() + "§8\" §7ist §aOnline §7auf ")
						.append(player.getServer().getInfo().getName())
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aKlick zum verbinden").create()))
						.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + player.getServer().getInfo().getName())).create());
			}
			else
				sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Der §cSpieler §8\"§c" + args[0] + "§8\" §7ist §7nicht §cOnline§8."));
		}
	}
}