package de.ng.nizada.freebuild.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ListenerEntitySpawnEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(EntitySpawnEvent event) {
		if(event.getEntityType() == EntityType.WITHER) {
			if(event.getEntity() != null)
				event.getEntity().remove();
			event.setCancelled(true);
		}
	}
}