package de.ng.nizada.build.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.ng.nizada.build.scoreboard.ScoreboardManager;
import de.ng.nizada.gamecore.util.RankUtil;

public class PlayerConnection implements Listener {
	
	@EventHandler
	public void onChat(PlayerJoinEvent event) throws InterruptedException {
		Player player = event.getPlayer();
		
		event.setJoinMessage("§8[§a+§8] §7" + player.getName());
		
		ScoreboardManager.SCOREBOARD_MANAGER.addPlayerToScoreboard(player, RankUtil.getRankByPermission(player));
		
		player.setGameMode(GameMode.CREATIVE);
		player.teleport(new Location(Bukkit.getWorld("build"), -35.5, 76.2, -35.5));
	}
	
	@EventHandler
	public void onChat(PlayerQuitEvent event) {
		event.setQuitMessage("§8[§c-§8] §7" + event.getPlayer().getName());
		
		ScoreboardManager.SCOREBOARD_MANAGER.removePlayerFromScoreboard(event.getPlayer());
	}
}