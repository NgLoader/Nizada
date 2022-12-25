package de.ng.nizada.freebuild.home;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import org.bukkit.Location;

import de.ng.nizada.freebuild.Freebuild;

public class HomeManager {

	public static final HomeManager HOME_MANAGER = new HomeManager();

	private HashMap<UUID, HashMap<String, Home>> homes;

	private boolean loaded;

	public HomeManager() {
		homes = new HashMap<>();
		loaded = false;
		loadAllHomes();
	}

	public void reload() {
		loaded = false;
		homes.clear();
		loadAllHomes();
	}

	public Home getHome(UUID uuid, String home) {
		waitForReady();
		return homes.containsKey(uuid) ? homes.get(uuid).get(home.toLowerCase()) : null;
	}

	public boolean existHome(UUID uuid, String home) {
		waitForReady();
		return homes.containsKey(uuid) && homes.get(uuid).containsKey(home.toLowerCase());
	}

	public Home createHome(UUID uuid, String homeName, Location location, String description) {
		Home home = new Home(homeName, location, description);

		if(!homes.containsKey(uuid))
			homes.put(uuid, new HashMap<>());
		homes.get(uuid).put(homeName.toLowerCase(), home);

		saveHome(uuid, home);
		return home;
	}

	public Home deleteHome(UUID uuid, String home) {
		waitForReady();
		if(!homes.containsKey(uuid) && !homes.get(uuid).containsKey(home.toLowerCase()))
			return null;

		File file = new File(Freebuild.instance.getDataFolder(), "/Homes/" + uuid.toString() + "/" + home.toLowerCase() + ".properties");
		if(file.exists())
			file.delete();
		return homes.get(uuid).remove(home.toLowerCase());
	}

	public void loadAllHomes() {
		if(!new File(Freebuild.instance.getDataFolder(), "/Homes/").exists())
			new File(Freebuild.instance.getDataFolder(), "/Homes/").mkdirs();

		for(File folder : new File(Freebuild.instance.getDataFolder(), "/Homes/").listFiles()) {
			UUID uuid = UUID.fromString(folder.getName());

			for(File file : folder.listFiles())
				if(file.getName().endsWith(".properties")) {
					Home home = loadHome(file);
					if(home != null) {
						if(!homes.containsKey(uuid))
							homes.put(uuid, new HashMap<>());
						homes.get(uuid).put(home.getName().toLowerCase(), home);
					}
				}
		}
		loaded = true;
	}

	public Home loadHome(File file) {
		try {
			if(!file.exists())
				return null;
			
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(file);
			properties.load(inputStream);
			inputStream.close();
			
			return new Home(properties);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void saveHome(UUID uuid, Home home) {
		try {
			File folder = new File(Freebuild.instance.getDataFolder(), "/Homes/" + uuid.toString());
			File file = new File(Freebuild.instance.getDataFolder(), "/Homes/" + uuid.toString() + "/" + home.getName().toLowerCase() + ".properties");

			if(!folder.exists())
				folder.mkdirs();
			if(!file.exists())
				file.createNewFile();

			FileOutputStream outputStream = new FileOutputStream(file);
			home.toProperties().store(outputStream, "Bitte lass die finder hier von THX!");
			outputStream.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public HashMap<String, Home> getHomesFromPlayer(UUID uuid) {
		waitForReady();
		if(homes.containsKey(uuid))
			return homes.get(uuid);
		else return new HashMap<>();
	}

	public void waitForReady() {
		while(!loaded)
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
	}

	public HashMap<UUID, HashMap<String, Home>> getHomes() {
		return homes;
	}
}