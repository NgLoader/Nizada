package de.ng.nizada.freebuild.command.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.warp.Warp;
import de.ng.nizada.freebuild.warp.WarpManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandListWarps implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(Freebuild.PREFIX + "§8[]§7------§a{ §2Warp Liste §a}§7------§8[]");
		sender.sendMessage(Freebuild.PREFIX + " ");
		
		for(Warp warp : WarpManager.WARP_MANAGER.getWarps().values())
			if(!warp.getPermission().equals("") && !sender.hasPermission(warp.getPermission()))
				continue;
			else if(!sender.hasPermission("nizada.warp.default"))
				continue;
			else {
//				sender.sendMessage(Freebuild.PREFIX + "§8- §a" + warp.getName() + (warp.getDescription().isEmpty() ? "" : " §8(§7" + warp.getDescription() + "§8)"));
				TextComponent textComponent = new TextComponent(Freebuild.PREFIX + "§8- §a" + warp.getName() + (warp.getDescription().isEmpty() ? "" : " §8(§7" + warp.getDescription() + "§8)"));
				textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aKlick zum §2teleportieren").create()));
				textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warp " + warp.getName()));
				sender.spigot().sendMessage(textComponent);
				
				for(String alias : warp.getAliases()) {
					TextComponent textComponent2 = new TextComponent(Freebuild.PREFIX + "     §8> §a" + alias);
					textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aKlick zum §2teleportieren").create()));
					textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warp " + warp.getName()));
					sender.spigot().sendMessage(textComponent2);
				}
			}
		
		
		sender.sendMessage(Freebuild.PREFIX + " ");
		sender.sendMessage(Freebuild.PREFIX + "§8[]§7------§a{ §2Warp Liste §a}§7------§8[]");
        return true;
    }
}