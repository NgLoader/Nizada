package de.ng.nizada.freebuild.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import de.ng.nizada.freebuild.Freebuild;

public class ListenerBlockBreakEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		if(item.getType() == Material.GOLD_SPADE && item.hasItemMeta() && item.getItemMeta().hasLore()) {
			event.setExpToDrop(0);
			event.setDropItems(false);
			event.setCancelled(true);
			
			event.getPlayer().sendMessage(Freebuild.PREFIX + " §7Du darfst mit der §cClaim Schaufel §7keine bl§cke §4abbauen§8.");
		}
	}
}