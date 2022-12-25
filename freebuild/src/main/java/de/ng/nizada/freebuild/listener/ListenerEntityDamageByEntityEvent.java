package de.ng.nizada.freebuild.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ListenerEntityDamageByEntityEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;
		
		if(event.getDamager() != null)
			if(event.getDamager().getType() == EntityType.PLAYER)
				event.setCancelled(true);
			else if(event.getDamager().getType() == EntityType.ARROW) {
				Arrow arrow = (Arrow) event.getDamager();
				
				if(arrow != null && arrow.getShooter() instanceof Player)
					event.setCancelled(true);
			}
	}
}