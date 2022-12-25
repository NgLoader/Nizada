package de.ng.nizada.freebuild.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Team;

import de.ng.nizada.api.rank.Rank;
import de.ng.nizada.freebuild.callback.CallbackManager;
import de.ng.nizada.freebuild.callback.CallbackType;
import de.ng.nizada.freebuild.callback.TypeCallback;
import de.ng.nizada.freebuild.chatsystem.ChatLog;
import de.ng.nizada.freebuild.discord.DiscordChatSync;

public class ListenerPlayerChatEvent implements Listener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		event.setCancelled(true);

		TypeCallback<Object> callback = CallbackManager.getCallback(player.getUniqueId(), CallbackType.CHAT);
		if(callback != null) {
			callback.done(event.getMessage(), null);
			return;
		}

		String message = "";
		Team team = player.getScoreboard().getEntryTeam(player.getName());
		if(team == null)
			message += "§8[" + Rank.PLAYER.getColorCode() + Rank.PLAYER.getDisplayName() + "§8] ";
		else
			message += "§8[" + team.getPrefix().substring(0, team.getPrefix().length() - 7) + "§8] ";

		message += "§7" + player.getDisplayName() + " §8» §7";
		message += player.hasPermission("nizada.chat.color") ? ChatColor.translateAlternateColorCodes('&', event.getMessage()) : event.getMessage();

		Bukkit.broadcastMessage(message);
		ChatLog.CHAT_LOG.addMessage(message);
		DiscordChatSync.sendMessage(ChatColor.stripColor(message));
	}
}