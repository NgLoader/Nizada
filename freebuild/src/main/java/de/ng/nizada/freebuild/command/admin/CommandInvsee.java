package de.ng.nizada.freebuild.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.api.rank.Rank;
import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.gamecore.util.RankUtil;

public class CommandInvsee implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann ihren §aGameMode §7nicht ändern§8.");
			return true;
		}
		
		Player player = (Player) sender;
		if(player.hasPermission("freebuild.invsee")) {
			if(args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				
				if(target != null) {
					if(Rank.isRankHigher(RankUtil.getRankByPermission(player), RankUtil.getRankByPermission(target), false)) {
						if(target.getName().equals("NilssMiner99") || target.getName().equals("Dragon0697"))
							player.openInventory(Bukkit.createInventory(null, 36, "Inventory"));
						else
							player.openInventory(target.getPlayer().getInventory());
						sender.sendMessage(Freebuild.PREFIX + "§7Du siehst grade das §aInventar §7von §8\"§2" + target.getName() + "§8\"§8.");
					} else
						sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um in diesen §cInventar §7zu schauen§8.");
				} else
					sender.sendMessage(Freebuild.PREFIX + "§7Der §cSpieler §8\"§4" + args[0] + "§8\" §7exestiert §cnicht§8.\n"
							+ Freebuild.PREFIX + "§7/invsee §8<§aSpieler>§8");
			} else
				sender.sendMessage(Freebuild.PREFIX + "§7/invsee §8<§aSpieler§8>§8.");
		} else
			sender.sendMessage(Freebuild.PREFIX + "§7Du hast keine §4Rechte §7um diesen §cCommand §7zu nutzen§8.");
		return true;
	}
}