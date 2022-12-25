package de.ng.nizada.freebuild.home;

import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Home {
	
	private String name;
	
	private String description;
	
	private Location location;
	private String world;
	private double x, y, z;
	private float yaw, pitch;
	
	public Home(String name, Location location, String description) {
		this.name = name;
		this.location = location;
		this.description = description != null ? description : "";
		
		this.world = location.getWorld().getName();
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.yaw = location.getYaw();
		this.pitch = location.getPitch();
	}
	
	public Home(Properties properties) {
		this.name = properties.getProperty("name");
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
	
	public Properties toProperties() {
		Properties properties = new Properties();
		properties.setProperty("name", name);
		properties.setProperty("description", description);
		properties.setProperty("world", world);
		properties.setProperty("x", String.valueOf(x));
		properties.setProperty("y", String.valueOf(y));
		properties.setProperty("z", String.valueOf(z));
		properties.setProperty("yaw", String.valueOf(yaw));
		properties.setProperty("pitch", String.valueOf(pitch));
		return properties;
	}
}