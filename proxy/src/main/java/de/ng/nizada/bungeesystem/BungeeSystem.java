package de.ng.nizada.bungeesystem;

import de.ng.nizada.bungeesystem.command.CommandAlert;
import de.ng.nizada.bungeesystem.command.CommandAlertRaw;
import de.ng.nizada.bungeesystem.command.CommandBuild;
import de.ng.nizada.bungeesystem.command.CommandFind;
import de.ng.nizada.bungeesystem.command.CommandFreebuild;
import de.ng.nizada.bungeesystem.command.CommandGlobalList;
import de.ng.nizada.bungeesystem.command.CommandMsg;
import de.ng.nizada.bungeesystem.command.CommandSend;
import de.ng.nizada.bungeesystem.command.CommandServer;
import de.ng.nizada.bungeesystem.listener.ListenerServerSwitchEvent;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeSystem extends Plugin {
	
	public static final String PREFIX = "§8[§4N§ci§4Z§ca§4D§ca§8] ";
	
	public static BungeeSystem instance;
	
	@Override
	public void onLoad() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		registerListener();
		registerCommands();
	}
	
	@Override
	public void onDisable() { }
	
	private void registerListener() {
		getProxy().getPluginManager().registerListener(this, new ListenerServerSwitchEvent());
	}
	
	private void registerCommands() {
		getProxy().getPluginManager().registerCommand(this, new CommandServer());
		getProxy().getPluginManager().registerCommand(this, new CommandSend());
		getProxy().getPluginManager().registerCommand(this, new CommandGlobalList());
		getProxy().getPluginManager().registerCommand(this, new CommandFreebuild());
		getProxy().getPluginManager().registerCommand(this, new CommandFind());
		getProxy().getPluginManager().registerCommand(this, new CommandBuild());
		getProxy().getPluginManager().registerCommand(this, new CommandAlertRaw());
		getProxy().getPluginManager().registerCommand(this, new CommandAlert());
		getProxy().getPluginManager().registerCommand(this, new CommandMsg());
	}
}