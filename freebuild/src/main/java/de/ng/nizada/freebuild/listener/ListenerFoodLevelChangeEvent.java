package de.ng.nizada.freebuild.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.vanish.Vanish;

public class ListenerFoodLevelChangeEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(FoodLevelChangeEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		
		if(Vanish.VANISH.isVanish(player)) {
			event.setCancelled(true);
			return;
		}
		
		if(player.hasMetadata("NIZADA_GODMODE")) {
			if(!player.hasPermission("nizada.godmode")) {
				player.removeMetadata("NIZADA_GODMODE", Freebuild.instance);
				return;
			}
			event.setCancelled(true);
			event.setFoodLevel(20);
			player.setFoodLevel(20);
		}
	}
}