package de.ng.nizada.gamecore.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.EnumGamemode;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class CustomPlayerUtil extends Reflection {

	private static Constructor<?> PlayerInfoDataClass;
	
	public static void sendPacket(Packet<?> packet) {
		Bukkit.getOnlinePlayers().forEach(player -> sendPacket(player, packet));
	}

	public static void sendPacket(List<Player> players, Packet<?> packet) {
		players.forEach(player -> sendPacket(player, packet));
	}

	public static void sendPacket(Player player, Packet<?> packet) {
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public static PacketPlayOutPlayerInfo addToTabList(Player player) {
		return addToTabList(player, player.getName());
	}

	public static PacketPlayOutPlayerInfo addToTabList(Player player, String displayName) {
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER);
		set(packet, "b", Arrays.asList(searchPlayerInfoDataClass(packet, ((CraftPlayer) player).getProfile(), 0, EnumGamemode.NOT_SET, new ChatComponentText((player.getScoreboard().getEntryTeam(player.getName()) != null ? player.getScoreboard().getEntryTeam(player.getName()).getPrefix() : "") + displayName))));
		return packet;
	}

	public static PacketPlayOutPlayerInfo removeFromTabList(Player player) {
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER);
		set(packet, "b", Arrays.asList(searchPlayerInfoDataClass(packet, ((CraftPlayer) player).getProfile(), 0, null, null)));
		return packet;
	}
	
	public static Object searchPlayerInfoDataClass(Packet<?> packet, GameProfile gameProfile, int i, EnumGamemode enumGamemode, IChatBaseComponent iChatBaseComponent) {
		if(PlayerInfoDataClass == null) {
			for (Class<?> clazz : PacketPlayOutPlayerInfo.class.getClasses())
				if (clazz != null && clazz.getName().endsWith("PlayerInfoData"))
					PlayerInfoDataClass = clazz.getDeclaredConstructors()[0];
		}
		try {
			return PlayerInfoDataClass.newInstance(packet, gameProfile, i, enumGamemode, iChatBaseComponent);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}