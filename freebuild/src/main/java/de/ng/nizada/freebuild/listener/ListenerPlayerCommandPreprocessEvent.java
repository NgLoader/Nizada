package de.ng.nizada.freebuild.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.ng.nizada.freebuild.callback.CallbackManager;
import de.ng.nizada.freebuild.callback.CallbackType;
import de.ng.nizada.freebuild.callback.TypeCallback;

public class ListenerPlayerCommandPreprocessEvent implements Listener {
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		TypeCallback<Object> callback = CallbackManager.getCallback(event.getPlayer().getUniqueId(), CallbackType.COMMAND);
		if(callback != null) {
			event.setCancelled(true);
			callback.done(event.getMessage(), null);
			return;
		}

		if(event.getMessage().contains(" ") ? event.getMessage().split(" ")[0].equalsIgnoreCase("/help") : event.getMessage().equalsIgnoreCase("/help"))
			event.setMessage("/hilfe");
	}
}