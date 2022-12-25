package de.ng.nizada.freebuild.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import de.ng.nizada.freebuild.mobcatcher.MobCatcherManager;

public class ListenerPlayerInteractEntityEvent implements Listener {
	
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if(event.getHand() == EquipmentSlot.HAND && MobCatcherManager.instance.isMobCatcher(player.getInventory().getItemInMainHand()) && event.getRightClicked() != null && event.getRightClicked().isValid() && !event.getRightClicked().isOp() && event.getRightClicked().getType() != EntityType.PLAYER) {
			event.setCancelled(true);
			MobCatcherManager.instance.save(player, player.getInventory().getItemInMainHand(), (LivingEntity)event.getRightClicked());
		}
	}
}