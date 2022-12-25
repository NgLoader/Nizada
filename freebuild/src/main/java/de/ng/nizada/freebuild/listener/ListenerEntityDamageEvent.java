package de.ng.nizada.freebuild.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.ng.nizada.freebuild.Freebuild;

public class ListenerEntityDamageEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		
		if(player.hasMetadata("NIZADA_GODMODE")) {
			if(!player.hasPermission("nizada.godmode")) {
				player.removeMetadata("NIZADA_GODMODE", Freebuild.instance);
				return;
			}
			event.setDamage(0);
			
			if(player.getHealth() < 20)
				player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 4, 1), true);
			player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 40, 4), true);
			
			if(event.getCause() == DamageCause.VOID)
				player.teleport(Bukkit.getWorld("world").getSpawnLocation());
		}
	}
}