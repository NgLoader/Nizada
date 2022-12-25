package de.ng.nizada.freebuild.vanish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.command.admin.CommandAdminTool;
import de.ng.nizada.gamecore.util.CustomPlayerUtil;
import de.ng.nizada.gamecore.util.Reflection;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;

public class Vanish extends Reflection {
	
	public static final Vanish VANISH = new Vanish();
	
	private static final PacketPlayOutChat ACTION_BAR_PACKET_IN_VANISH = createActionBar("§aDu bist unsichtbar");
	
	public static PacketPlayOutChat createActionBar(String message) {
		IChatBaseComponent baseComponent = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		return new PacketPlayOutChat(baseComponent, ChatMessageType.GAME_INFO);
	}
	
	private final List<Player> players;
	private final HashMap<Player, GameMode> playerGamemode;
	private final HashMap<Player, ItemStack[][]> playerInventory;
	
	private int scheduleId;
	
	public Vanish() {
		players = new ArrayList<>();
		playerGamemode = new HashMap<>();
		playerInventory = new HashMap<>();
	}
	
	public void checkScheduler() {
		if(!Bukkit.getScheduler().isCurrentlyRunning(scheduleId))
			scheduleId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Freebuild.instance, new Runnable() {
				
				@Override
				public void run() {
					if(players.isEmpty()) {
						Bukkit.getScheduler().cancelTask(scheduleId);
						scheduleId = -1;
					} else {
						for(Player vanish : players) {
							CustomPlayerUtil.sendPacket(vanish, ACTION_BAR_PACKET_IN_VANISH);
							for (Player all : Bukkit.getOnlinePlayers())
								if (!vanish.equals(all) && all.hasPermission("nizada.vanish.seeother"))
									all.spawnParticle(Particle.FLAME, vanish.getGameMode() == GameMode.SPECTATOR ? vanish.getLocation().add(0, 1.2, 0) : vanish.getLocation().add(0, 2.2, 0), 0, 0D, 0D, 0D);
						}
					}
				}
			}, 2, 2);
	}
	
	public void playerConnected(Player player) {
		if(!player.hasPermission("nizada.vanish.seeother")) {
			players.forEach(hidden -> {
				player.hidePlayer(Freebuild.instance, hidden);
				CustomPlayerUtil.sendPacket(player, CustomPlayerUtil.addToTabList(hidden, hidden.getDisplayName()));
			});
		}
	}
	
	public void addPlayer(Player player) {
		if(players.contains(player))
			return;
		
		PacketPlayOutPlayerInfo packetPlayOutPlayerInfo = CustomPlayerUtil.addToTabList(player, player.getDisplayName());
		for(Player all : Bukkit.getOnlinePlayers())
			if(all.hasPermission("nizada.vanish.seeother"))
				all.showPlayer(Freebuild.instance, player);
			else {
				all.hidePlayer(Freebuild.instance, player);
				CustomPlayerUtil.sendPacket(all, packetPlayOutPlayerInfo);
			}
		
		ItemStack[][] inventory = new ItemStack[4][];
		inventory[0] = player.getInventory().getContents();
		inventory[1] = player.getInventory().getArmorContents();
		inventory[2] = player.getInventory().getExtraContents();
		inventory[3] = player.getInventory().getStorageContents();
		
		players.add(player);
		playerGamemode.put(player, player.getGameMode());
		playerInventory.put(player, inventory);
		
		player.getInventory().clear();
		CommandAdminTool.setAdminTool(player.getInventory());
		
		player.setGameMode(GameMode.SPECTATOR);
		
		checkScheduler();
	}
	
	public void removePlayer(Player player) {
		if(!players.contains(player))
			return;
		
		PacketPlayOutPlayerInfo packetPlayOutPlayerInfo = CustomPlayerUtil.addToTabList(player, player.getDisplayName());
		for(Player all : Bukkit.getOnlinePlayers()) {
			all.showPlayer(Freebuild.instance, player);
			CustomPlayerUtil.sendPacket(all, packetPlayOutPlayerInfo);
		}
		
		player.setGameMode(playerGamemode.get(player));
		
		ItemStack[][] inventory = playerInventory.get(player);
		player.getInventory().setContents(inventory[0]);
		player.getInventory().setArmorContents(inventory[1]);
		player.getInventory().setExtraContents(inventory[2]);
		player.getInventory().setStorageContents(inventory[3]);
		
		players.remove(player);
		playerGamemode.remove(player);
		playerInventory.remove(player);
	}
	
	public boolean isVanish(Player player) {
		return players.contains(player);
	}
}