package de.ng.nizada.build.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDead implements Listener {
	
	@EventHandler
	public void onChat(PlayerDeathEvent event) {
		event.setDeathMessage("");
		event.setKeepInventory(true);
		event.setKeepLevel(false);
		event.setNewExp(0);
		event.setNewTotalExp(0);
		event.setNewLevel(0);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		event.setRespawnLocation(event.getPlayer().getWorld().getSpawnLocation().add(0.5, 0.2, 0.5));
		event.getPlayer().setGameMode(GameMode.CREATIVE);
	}
}