package de.ng.nizada.build.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings("deprecation")
public class PlayerChat implements Listener {
	
	@EventHandler
	public void onChat(PlayerChatEvent event) {
		event.setCancelled(true);
		
		String rank = getRank(event.getPlayer());
		
		Bukkit.broadcastMessage("�8[" + rank + "�8] �e" + rank.substring(0, 2) + event.getPlayer().getName() + " �8>> �7" + event.getMessage());
	}
	
	private static String getRank(Player player) {
		if(player.hasPermission("build.chat.admin"))
			return "�cAdmin";
		else if(player.hasPermission("build.chat.builder"))
			return "�2Builder";
		return "�aSpieler";
	}
}