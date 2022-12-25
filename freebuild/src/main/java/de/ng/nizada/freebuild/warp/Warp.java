package de.ng.nizada.freebuild.warp;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Warp {
	
	private String name;
	
	private String permission;
	
	private String description;
	
	private Location location;
	private String world;
	private double x, y, z;
	private float yaw, pitch;
	
	private List<String> aliases;
	
	public Warp(String name, Location location, String permission, String description, List<String> aliases) {
		this.name = name;
		this.location = location;
		this.permission = permission != null ? permission : "";
		this.description = description != null ? description : "";
		this.aliases = aliases;
		
		this.world = location.getWorld().getName();
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.yaw = location.getYaw();
		this.pitch = location.getPitch();
	}
	
	public Warp(Properties properties) {
		this.name = properties.getProperty("name");
		this.permission = properties.getProperty("permission");
		this.description = properties.getProperty("description");
		this.world = properties.getProperty("world");
		this.x = Double.valueOf(properties.getProperty("x"));
		this.y = Double.valueOf(properties.getProperty("y"));
		this.z = Double.valueOf(properties.getProperty("z"));
		this.yaw = Float.valueOf(properties.getProperty("yaw"));
		this.pitch = Float.valueOf(properties.getProperty("pitch"));
		this.location = new Location(Bukkit.getWorld(world), x, y, z);
		this.location.setYaw(yaw);
		this.location.setPitch(pitch);
		this.aliases = new LinkedList<>();
		if(properties.getProperty("aliases") != null)
			this.aliases.addAll(Arrays.asList(properties.getProperty("aliases", "").split("§")));
	}
	
	public void addAliases(String alias) {
		this.aliases.add(alias);
	}
	
	public void removeAliases(String alias) {
		this.aliases.remove(alias);
	}
	
	public String getPermission() {
		return permission;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public String getName() {
		return name;
	}
	
	public String getWorld() {
		return world;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public List<String> getAliases() {
		return aliases;
	}
	
	public Properties toProperties() {
		Properties properties = new Properties();
		properties.setProperty("name", name);
		properties.setProperty("permission", permission);
		properties.setProperty("description", description);
		properties.setProperty("world", world);
		properties.setProperty("x", String.valueOf(x));
		properties.setProperty("y", String.valueOf(y));
		properties.setProperty("z", String.valueOf(z));
		properties.setProperty("yaw", String.valueOf(yaw));
		properties.setProperty("pitch", String.valueOf(pitch));
		properties.setProperty("aliases", String.join("�", aliases));
		return properties;
	}
}