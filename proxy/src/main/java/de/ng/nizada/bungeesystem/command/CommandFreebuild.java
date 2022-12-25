package de.ng.nizada.bungeesystem.command;

import de.ng.nizada.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandFreebuild extends Command {

	public static final ServerInfo SERVER_INFO_FREEBUILD = BungeeSystem.instance.getProxy().getServerInfo("freebuild");

	public CommandFreebuild() {
		super("freebuild", "nizada.command.freebuild", "freeb", "fbuild", "fb");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7bist §7kein §cSpieler§8."));
			return;
		}
		final ProxiedPlayer player = (ProxiedPlayer) sender;
		if(!player.getServer().getInfo().equals(SERVER_INFO_FREEBUILD)) {
			player.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7wirst §7nun §7auf §7den §7Server §aFreebuild §7gesendet§8."));
			player.connect(SERVER_INFO_FREEBUILD);
		} else
			player.sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7bist §7bereits §7auf §7diesen §cServer§8."));
	}
}