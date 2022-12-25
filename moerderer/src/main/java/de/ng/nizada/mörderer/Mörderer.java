package de.ng.nizada.mörderer;

import org.bukkit.plugin.java.JavaPlugin;

public class Mörderer extends JavaPlugin {
	
	public static Mörderer instance;
	
	public Mörderer() {
		instance = this;
	}
	
	@Override
	public void onLoad() { }
	
	@Override
	public void onEnable() { }
	
	@Override
	public void onDisable() { }
}