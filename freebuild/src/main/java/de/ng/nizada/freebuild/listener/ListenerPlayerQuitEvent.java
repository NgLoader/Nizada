package de.ng.nizada.freebuild.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.ng.nizada.freebuild.chatsystem.ChatLog;
import de.ng.nizada.freebuild.discord.DiscordChatSync;
import de.ng.nizada.freebuild.scoreboard.ScoreboardManager;
import de.ng.nizada.freebuild.tpa.TpaManager;
import de.ng.nizada.freebuild.vanish.Vanish;

public class ListenerPlayerQuitEvent implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage("§8[§c-§8] " + event.getPlayer().getDisplayName());
		DiscordChatSync.sendMessage("§8[§c-§8] " + event.getPlayer().getDisplayName());

		if(!Vanish.VANISH.isVanish(player)) {
			ChatLog.CHAT_LOG.addMessage("§8[§c-§8] " + player.getDisplayName());
		} else {
//			event.setQuitMessage("");
			Vanish.VANISH.removePlayer(player);
		}
		TpaManager.reset(player.getUniqueId());

		ScoreboardManager.SCOREBOARD_MANAGER.removePlayerFromScoreboard(player);
	}
}