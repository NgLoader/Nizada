package de.ng.nizada.freebuild.command.home;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.api.rank.Rank;
import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.home.Home;
import de.ng.nizada.freebuild.home.HomeManager;
import de.ng.nizada.gamecore.util.RankUtil;

public class CommandCreateHome implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann kein §cHome §7erstellen§8.");
			return true;
		}
		Player player = (Player) sender;
        
		if(!player.hasPermission("nizada.home.create")) {
			player.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}
		
        if(args.length >= 1) {
        	String homeName = args[0];
        	int homes = Rank.isRankHigher(RankUtil.getRankByPermission(player), Rank.PREMIUM, true) ? 10 : 3;

        	if(!player.hasPermission("nizada.admin") && !player.hasPermission("nizada.home.nohomelimit") && HomeManager.HOME_MANAGER.getHomesFromPlayer(player.getUniqueId()).size() >= homes) {
    			player.sendMessage(Freebuild.PREFIX + "§7Du darfst nur maximal §c3 §7Homes §cbesitzen§8.");
        		return true;
        	}

        	List<String> description = new ArrayList<>();
        	for(int i = 1; i < args.length; i++)
        		description.add(args[i]);
        	
        	if(HomeManager.HOME_MANAGER.existHome(player.getUniqueId(), homeName.toLowerCase())) {
                player.sendMessage(Freebuild.PREFIX + "§7Dieser §cHome §7exestiert schon§8.");
        		return true;
        	}
        	
        	Home home = HomeManager.HOME_MANAGER.createHome(player.getUniqueId(), homeName, player.getLocation(), String.join(" ", description));
            player.sendMessage(Freebuild.PREFIX + "§7Du hast erfolgreich dein §aHome §8\"§a" + home.getName() + "§8\" §aerstellt§8.");
        } else {
        	player.sendMessage(Freebuild.PREFIX + "§7/SetHome §8<§7name§8> §8[§7beschreibung§8]");
        }
        return true;
    }
}