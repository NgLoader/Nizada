package de.ng.nizada.freebuild.listener;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.ng.nizada.api.rank.Rank;
import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.chatsystem.ChatLog;
import de.ng.nizada.freebuild.discord.DiscordChatSync;
import de.ng.nizada.freebuild.scoreboard.ScoreboardManager;
import de.ng.nizada.freebuild.vanish.Vanish;
import de.ng.nizada.gamecore.util.CustomPlayerUtil;
import de.ng.nizada.gamecore.util.RankUtil;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;

public class ListenerPlayerJoinEvent implements Listener {
	
	private PacketPlayOutPlayerListHeaderFooter packetPlayOutPlayerListHeaderFooter;
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Rank rank = RankUtil.getRankByPermission(player);
		
		Vanish.VANISH.playerConnected(player);
		
		Bukkit.getScheduler().runTaskLaterAsynchronously(Freebuild.instance, new Runnable() {
			
			@Override
			public void run() {
				if(!player.isOnline())
					return;
				for(Player all : Bukkit.getOnlinePlayers())
						if(!all.getName().equals(all.getDisplayName()) && !all.equals(player))
								CustomPlayerUtil.sendPacket(player, CustomPlayerUtil.addToTabList(all, (player.hasPermission("nizada.change.name.see") ? !all.getName().equals(all.getDisplayName()) ? "§8{§7" + all.getName() + "§8} §7" : "" : "") + all.getDisplayName()));
			}
		}, 10);

		event.setJoinMessage("§8[§a+§8] " + player.getDisplayName());
		DiscordChatSync.sendMessage("§8[§a+§8] " + event.getPlayer().getDisplayName());
		ChatLog.CHAT_LOG.addMessage("§8[§a+§8] " + player.getDisplayName());
		ScoreboardManager.SCOREBOARD_MANAGER.addPlayerToScoreboard(player, rank);

		((CraftPlayer)player).getHandle().playerConnection.networkManager.sendPacket(packetPlayOutPlayerListHeaderFooter == null ? createPacketPlayOutPlayerListHeaderFooter() : packetPlayOutPlayerListHeaderFooter);
	}
	
	public PacketPlayOutPlayerListHeaderFooter createPacketPlayOutPlayerListHeaderFooter() {
		try {
			packetPlayOutPlayerListHeaderFooter = new PacketPlayOutPlayerListHeaderFooter();
			
			Field fieldA = packetPlayOutPlayerListHeaderFooter.getClass().getDeclaredField("a");
			fieldA.setAccessible(true);
			fieldA.set(packetPlayOutPlayerListHeaderFooter, ChatSerializer.a("{\"text\":\"§cNizada Netzwerk\n§aFreebuild\n§51.12.2\n\"}"));
			Field fieldB = packetPlayOutPlayerListHeaderFooter.getClass().getDeclaredField("b");
			fieldB.setAccessible(true);
			fieldB.set(packetPlayOutPlayerListHeaderFooter, ChatSerializer.a("{\"text\":\"\n§3Discord\n§7https://chat.nizada.net\"}"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return packetPlayOutPlayerListHeaderFooter;
	}
}