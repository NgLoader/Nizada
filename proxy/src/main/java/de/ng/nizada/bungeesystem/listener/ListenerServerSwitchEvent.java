package de.ng.nizada.bungeesystem.listener;

import de.ng.nizada.bungeesystem.BungeeSystem;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ListenerServerSwitchEvent implements Listener {
	
	@EventHandler
	public void onServerSwitch(ServerSwitchEvent event) {
		String serverName = event.getPlayer().getServer().getInfo().getName();
		event.getPlayer().sendMessage(new TextComponent(BungeeSystem.PREFIX + "§7Du §7betrittst §7nun §7den §7Server §a" + serverName.substring(0, 1).toUpperCase() + serverName.substring(1).toLowerCase() + "§8."));
	}
}