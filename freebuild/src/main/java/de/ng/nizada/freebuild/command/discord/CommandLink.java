package de.ng.nizada.freebuild.command.discord;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.discord.DiscordPlayerLinkManager;

public class CommandLink implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player && args.length > 1)
			DiscordPlayerLinkManager.DISCORD_PLAYER_LINK_MANAGER.verificate((Player) sender, args[0], args[1]);
		else
			sender.sendMessage(Freebuild.PREFIX + "§7/link §8<§7code§8>");
		return true;
	}
}