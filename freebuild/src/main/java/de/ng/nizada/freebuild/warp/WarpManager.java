package de.ng.nizada.freebuild.warp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.bukkit.Location;

import de.ng.nizada.freebuild.Freebuild;

public class WarpManager {
	
	public static final WarpManager WARP_MANAGER = new WarpManager();
	
	private HashMap<String, Warp> warps;
	
	private boolean loaded;
	
	public WarpManager() {
		warps = new HashMap<>();
		loaded = false;
		loadAllWarps();
	}
	
	public void reload() {
		loaded = false;
		warps.clear();
		loadAllWarps();
	}
	
	public Warp getWarp(String warp) {
		waitForReady();
		if(warps.containsKey(warp.toLowerCase()))
			return warps.get(warp.toLowerCase());
		for(Warp w : warps.values())
			if(w.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(warp)))
				return w;
		return null;
	}
	
	public boolean existWarp(String warp) {
		waitForReady();
		if(warps.containsKey(warp.toLowerCase()))
			return true;
		for(Warp w : warps.values())
			if(w.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(warp)))
				return true;
		return false;
	}
	
	public Warp createWarp(String warpName, Location location, String permission, String description, List<String> aliases) {
		Warp warp = new Warp(warpName, location, permission, description, aliases);
		
		warps.put(warpName.toLowerCase(), warp);
		saveWarp(warp);
		
		return warp;
	}
	
	public Warp deleteWarp(String warp) {
		waitForReady();
		if(!warps.containsKey(warp.toLowerCase()))
			return null;
		
		File file = new File(Freebuild.instance.getDataFolder(), "/Warps/" + warp.toLowerCase() + ".properties");
		if(file.exists())
			file.delete();
		return warps.remove(warp.toLowerCase());
	}
	
	public void loadAllWarps() {
		if(!new File(Freebuild.instance.getDataFolder(), "/Warps/").exists())
			new File(Freebuild.instance.getDataFolder(), "/Warps/").mkdirs();
		
		for(File file : new File(Freebuild.instance.getDataFolder(), "/Warps/").listFiles())
			if(file.getName().endsWith(".properties")) {
				Warp warp = loadWarp(file);
				if(warp != null)
					warps.put(warp.getName().toLowerCase(), warp);
			}
		loaded = true;
	}
	
	public Warp loadWarp(File file) {
		try {
			if(!file.exists())
				return null;
			
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(file);
			properties.load(inputStream);
			inputStream.close();
			
			return new Warp(properties);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public void saveWarp(Warp warp) {
		try {
			File folder = new File(Freebuild.instance.getDataFolder(), "/Warps/");
			File file = new File(Freebuild.instance.getDataFolder(), "/Warps/" + warp.getName().toLowerCase() + ".properties");
			
			if(!folder.exists())
				folder.mkdirs();
			if(!file.exists())
				file.createNewFile();
			
			FileOutputStream outputStream = new FileOutputStream(file);
			warp.toProperties().store(outputStream, "Bitte lass die finder hier von THX!");
			outputStream.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void waitForReady() {
		while(!loaded)
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
	}
	
	public HashMap<String, Warp> getWarps() {
		return warps;
	}
}