package de.ng.nizada.freebuild.callback;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.gamecore.util.Pair;

public class CallbackManager {

	private static final HashMap<UUID, Entry<CallbackType, TypeCallback<Object>>> CALLBACKS = new HashMap<>();

	public static void startScheduler() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Freebuild.instance, new Runnable() {

			@Override
			public void run() {
				for(Entry<UUID, Entry<CallbackType, TypeCallback<Object>>> entry : CALLBACKS.entrySet())
					if(entry.getValue() == null || entry.getValue().getValue() == null || entry.getValue().getValue().isExpired()) {
						if(entry.getValue() != null && entry.getValue().getValue() != null)
							entry.getValue().getValue().expired();
						CALLBACKS.remove(entry.getKey());
					}
			}
		}, 0, 20);
	}

	public static boolean hasCallback(UUID uuid, CallbackType callbackType) {
		return CALLBACKS.containsKey(uuid) && CALLBACKS.get(uuid).getKey() == callbackType;
	}

	public static boolean hasCallback(UUID uuid) {
		return CALLBACKS.containsKey(uuid);
	}

	public static Entry<CallbackType, TypeCallback<Object>> getCallback(UUID uuid) {
		if(hasCallback(uuid))
			return CALLBACKS.get(uuid);
		return null;
	}

	public static TypeCallback<Object> getCallback(UUID uuid, CallbackType callbackType) {
		if(hasCallback(uuid, callbackType))
			return CALLBACKS.get(uuid).getValue();
		return null;
	}

	public static TypeCallback<Object> getCallbackAndRemove(UUID uuid, CallbackType callbackType) {
		if(hasCallback(uuid, callbackType)) {
			Entry<CallbackType, TypeCallback<Object>> callback = CALLBACKS.get(uuid);
			CALLBACKS.remove(uuid);
			return callback.getValue();
		}
		return null;
	}

	public static void addCallback(UUID uuid, CallbackType callbackType, TypeCallback<Object> callback) {
		if(CALLBACKS.containsKey(uuid))
			throw new NullPointerException("Already added \"" + uuid + "\" to callbacks");
		CALLBACKS.put(uuid, new Pair<CallbackType, TypeCallback<Object>>(callbackType, callback));
	}
}