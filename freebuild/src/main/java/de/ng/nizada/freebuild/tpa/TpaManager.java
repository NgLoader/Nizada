package de.ng.nizada.freebuild.tpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;

public class TpaManager {

	private static final HashMap<UUID, List<TpaCache>> REQUESTS = new HashMap<>();

	public static void startScheduler() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Freebuild.instance, new Runnable() {
			HashMap<TpaCache, UUID> remove = new HashMap<>();

			@Override
			public void run() {
				remove.clear();
				for(Entry<UUID, List<TpaCache>> entry : REQUESTS.entrySet())
					for(TpaCache tpaCache : entry.getValue())
						if(Bukkit.getPlayer(tpaCache.sender) == null || Bukkit.getPlayer(tpaCache.target) == null || tpaCache.duration < System.currentTimeMillis())
							remove.put(tpaCache, entry.getKey());
				for(Entry<TpaCache, UUID> entry : remove.entrySet())
					if(REQUESTS.containsKey(entry.getValue()))
						REQUESTS.get(entry.getValue()).remove(entry.getKey());
			}
		}, 0, 20);
	}

	public static boolean hasRequest(UUID sender, UUID target) {
		if(REQUESTS.containsKey(target))
			return REQUESTS.get(target).stream().anyMatch(request -> request.duration > System.currentTimeMillis() && request.sender.equals(sender));
		return false;
	}

	public static TpaCache hasRequestAndRemove(UUID sender, UUID target) {
		if(REQUESTS.containsKey(target))
			for(TpaCache request : REQUESTS.get(target))
				if(request.sender.equals(sender)) {
					REQUESTS.get(target).remove(request);
					if(request.duration > System.currentTimeMillis())
						return request;
				}
		return null;
	}

	public static boolean addRequest(Player sender, Player target, Location location) {
		if(hasRequest(sender.getUniqueId(), target.getUniqueId()))
			return false;
		if(!REQUESTS.containsKey(target.getUniqueId()))
			REQUESTS.put(target.getUniqueId(), new ArrayList<>());
		REQUESTS.get(target.getUniqueId()).add(new TpaCache(sender.getUniqueId(), target.getUniqueId(), sender.getName(), target.getName(), System.currentTimeMillis() + 60000, location));
		return true;
	}

	public static HashMap<UUID, List<TpaCache>> getRequests() {
		for(Entry<UUID, List<TpaCache>> entry : REQUESTS.entrySet())
			for(TpaCache tpaCache : entry.getValue())
				if(tpaCache.duration < System.currentTimeMillis())
					REQUESTS.get(entry.getKey()).remove(tpaCache);
		return REQUESTS;
	}

	public static void reset(UUID uuid) {
		REQUESTS.remove(uuid);
	}
}