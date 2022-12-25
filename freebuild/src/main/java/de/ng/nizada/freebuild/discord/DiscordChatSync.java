package de.ng.nizada.freebuild.discord;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;

import de.ng.nizada.freebuild.chatsystem.ChatLog;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;

public class DiscordChatSync {
	
	public static void sendMessage(String message) {
		if(DiscordManager.DISCORD_MANAGER.getDiscordClient() == null || !DiscordManager.DISCORD_MANAGER.getDiscordClient().isReady())
			return;
		
		IGuild guild = DiscordManager.DISCORD_MANAGER.getDiscordClient().getGuildByID(DiscordManager.GUILD_ID);
		
		if(guild == null)
			return;
		
		for(long channel_id : DiscordManager.CHANNEL_IDS) {
			IChannel channel = guild.getChannelByID(channel_id);
			if(channel != null)
				channel.sendMessage(message);
		}
	}
	
	@EventSubscriber
	public void onMessageRecivedEvent(MessageReceivedEvent event) {
		if(event.getChannel().isPrivate()) {
			if(event.getMessage().getContent().isEmpty())
				return;
			
			String[] args = event.getMessage().getContent().split(" ");
			String command = args[0];
			if(command.startsWith("/"))
				command = command.substring(1);
			
			if(args.length > 0)
				args = (String[]) ArrayUtils.remove(args, 0);
			
			switch(command) {
			case "link":
				if(args.length > 0)
					DiscordPlayerLinkManager.DISCORD_PLAYER_LINK_MANAGER.createVerification(event.getAuthor(), args[0]);
				break;
				
			case "unlink":
				DiscordPlayerLinkManager.DISCORD_PLAYER_LINK_MANAGER.unlink(event.getAuthor());
				break;
				
			default:
				event.getChannel().sendMessage("Dieser Command konnte nicht gefunden werden.");
				break;
			}
			return;
		} else if(event.getGuild().getLongID() != DiscordManager.GUILD_ID || !Arrays.asList(DiscordManager.CHANNEL_IDS).contains(event.getChannel().getLongID()))
			return;
		
		String message = "§8[§9Discord§8] " + event.getAuthor().getDisplayName(event.getGuild()) + " §8» §7" + event.getMessage();
		Bukkit.broadcastMessage(message);
		ChatLog.CHAT_LOG.addMessage(message);
	}
}