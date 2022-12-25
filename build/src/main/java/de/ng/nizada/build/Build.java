package de.ng.nizada.build;

import org.bukkit.plugin.java.JavaPlugin;

import de.ng.nizada.build.command.CommandGameMode;
import de.ng.nizada.build.command.CommandGiveItem;
import de.ng.nizada.build.command.CommandHead;
import de.ng.nizada.build.command.CommandPing;
import de.ng.nizada.build.listener.PlayerChat;
import de.ng.nizada.build.listener.PlayerConnection;
import de.ng.nizada.build.listener.PlayerDead;

public class Build extends JavaPlugin {
	
	public static final String PREFIX = "§8[§7Build§8] ";
	
	public static Build instance;
	
	public Build() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		registerCommands();
		registerListener();
	}
	
	private void registerListener() {
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		getServer().getPluginManager().registerEvents(new PlayerConnection(), this);
		getServer().getPluginManager().registerEvents(new PlayerDead(), this);
	}
	
	private void registerCommands() {
		getCommand("ping").setExecutor(new CommandPing());
		getCommand("gamemode").setExecutor(new CommandGameMode());
		getCommand("head").setExecutor(new CommandHead());
		getCommand("giveitem").setExecutor(new CommandGiveItem());
	}
}