package de.ng.nizada.freebuild.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import de.ng.nizada.freebuild.mobcatcher.MobCatcherManager;

public class ListenerPlayerInteractEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if(event.getHand() == EquipmentSlot.HAND && MobCatcherManager.instance.isMobCatcher(item)) {
			event.setCancelled(true);
			MobCatcherManager.instance.load(player, item, event.getClickedBlock().getLocation().add(0.5, 1, 0.5));
		}
	}
}