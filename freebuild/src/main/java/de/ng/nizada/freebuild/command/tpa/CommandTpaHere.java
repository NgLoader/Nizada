package de.ng.nizada.freebuild.command.tpa;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.tpa.TpaManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandTpaHere implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann keine §cTpa §7nutzen§8.");
			return true;
		}
		Player player = (Player) sender;
		if(args.length > 0) {
			Player target = Bukkit.getPlayer(args[0]);
			if(target != null)
				if(target.getUniqueId() != player.getUniqueId())
					if(TpaManager.addRequest(player, target, player.getLocation())) {
						player.sendMessage(Freebuild.PREFIX + "§7Du hast eine §aTeleportanfragen §7zu deiner §2Position §7an §a\"" + target.getName() + "\" §7gestellt§8.");
						TextComponent message = new TextComponent(Freebuild.PREFIX + "§7Du hast eine §aTeleportanfrage §7zu §8\"§2" + player.getName() + "§8\" §7erhalten§8.");
						TextComponent accept = new TextComponent("§aAnnehmen");
						TextComponent deny = new TextComponent("§cAblehnen");
	
						accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aNehme die §2Teleportanfrage §avon §8\"§2" + player.getName() + "§8\" §aan").create()));
						accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaaccept " + player.getName()));
	
						deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cLehne die §4Teleportanfrage §cvon §8\"§4" + player.getName() + "§8\" §cab").create()));
						deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpadeny " + player.getName()));

						message.addExtra(new TextComponent("\n" + Freebuild.PREFIX));
						message.addExtra(accept);
						message.addExtra(new TextComponent(" §8- "));
						message.addExtra(deny);
	
						target.spigot().sendMessage(message);
					} else
						player.sendMessage(Freebuild.PREFIX + "§7Du hast §8\"§c" + target.getName() + "§8\" §7bereits eine §cTeleportanfrage §7zu dir §cgestellt§8.");
				else
					player.sendMessage(Freebuild.PREFIX + "§7Du kannst dir nicht selber eine §cTeleportanfragen §7senden§8.");
			else
				player.sendMessage(Freebuild.PREFIX + "§7Der angegebene §cSpieler §8\"§4" + args[0] + "§8\" §7ist nicht §cOnline§8.");
		} else
			player.sendMessage(Freebuild.PREFIX + "§8/§7tpahere §8<§aSpieler§8>");
		return true;
	}
}