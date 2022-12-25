package de.ng.nizada.freebuild.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import de.ng.nizada.freebuild.vanish.Vanish;

public class ListenerEntityPickupItemEvent implements Listener {
	
	@EventHandler
	public void onEntityPickupItem(EntityPickupItemEvent event) {
		if(event.getEntity().getType() == EntityType.PLAYER)
			if(Vanish.VANISH.isVanish((Player)event.getEntity()))
				event.setCancelled(true);
	}
}