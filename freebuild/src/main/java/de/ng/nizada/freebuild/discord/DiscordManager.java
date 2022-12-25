package de.ng.nizada.freebuild.discord;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

public class DiscordManager {
	
	public static final DiscordManager DISCORD_MANAGER = new DiscordManager();
	
	public static final Long GUILD_ID = 225240206524022784L;
	public static final Long LINKED_GROUP = 431987966827823105L;
	public static final Long[] CHANNEL_IDS = new Long[] {
			431529297011015692L
	};
	
	private static final String DISCORD_BOT_TOKEN = "---";
	
	private IDiscordClient discordClient;
	
	public void connect() {
		if(this.discordClient != null)
			return;
		
		ClientBuilder clientBuilder = new ClientBuilder();
		clientBuilder.withToken(DISCORD_BOT_TOKEN);
		this.discordClient = clientBuilder.build();
		
		EventDispatcher eventDispatcher = this.discordClient.getDispatcher();
		
		eventDispatcher.registerListener(new DiscordChatSync());
		
		this.discordClient.login();
	}
	
	public IDiscordClient getDiscordClient() {
		return discordClient;
	}
}