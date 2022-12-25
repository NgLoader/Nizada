package de.ng.nizada.bungeesystem.command;

import de.ng.nizada.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Command;

public class CommandAlert extends Command {

	public static final ServerInfo SERVER_INFO_BUILD = BungeeSystem.instance.getProxy().getServerInfo("build");

	public CommandAlert() {
		super("alert", "nizada.command.alert");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length == 0)
			sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7musst §7eine §cNachricht §7angeben§8."));
		else
			ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(BungeeSystem.PREFIX + "§a" + ChatColor.translateAlternateColorCodes('&', String.join(" ", args))));
	}
}