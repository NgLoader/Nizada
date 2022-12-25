package de.ng.nizada.gamecore.util;

import org.bukkit.entity.Player;

import de.ng.nizada.api.rank.Rank;

public class RankUtil {
	
	public static Rank getRankByPermission(Player player) {
		for(Rank rank : Rank.values())
			if(player.hasPermission("nizada." + rank.name().toLowerCase()))
				return rank;
		return Rank.PLAYER;
	}
}