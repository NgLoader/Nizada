package de.ng.nizada.freebuild;

import org.bukkit.plugin.java.JavaPlugin;

import de.ng.nizada.freebuild.callback.CallbackManager;
import de.ng.nizada.freebuild.command.CommandClaimTool;
import de.ng.nizada.freebuild.command.CommandEnderchest;
import de.ng.nizada.freebuild.command.CommandFreebuildReload;
import de.ng.nizada.freebuild.command.CommandPing;
import de.ng.nizada.freebuild.command.CommandSpawn;
import de.ng.nizada.freebuild.command.admin.CommandAdminTool;
import de.ng.nizada.freebuild.command.admin.CommandChangeName;
import de.ng.nizada.freebuild.command.admin.CommandFly;
import de.ng.nizada.freebuild.command.admin.CommandGameMode;
import de.ng.nizada.freebuild.command.admin.CommandGodMode;
import de.ng.nizada.freebuild.command.admin.CommandInvsee;
import de.ng.nizada.freebuild.command.admin.CommandSay;
import de.ng.nizada.freebuild.command.admin.CommandVanish;
import de.ng.nizada.freebuild.command.chatsystem.CommandClearChat;
import de.ng.nizada.freebuild.command.discord.CommandLink;
import de.ng.nizada.freebuild.command.help.CommandAddHelpMessage;
import de.ng.nizada.freebuild.command.help.CommandHelp;
import de.ng.nizada.freebuild.command.help.CommandListHelpMessage;
import de.ng.nizada.freebuild.command.help.CommandRemoveHelpMessage;
import de.ng.nizada.freebuild.command.help.CommandUpdateHelpMessage;
import de.ng.nizada.freebuild.command.home.CommandCreateHome;
import de.ng.nizada.freebuild.command.home.CommandDeleteHome;
import de.ng.nizada.freebuild.command.home.CommandHome;
import de.ng.nizada.freebuild.command.home.CommandListHomes;
import de.ng.nizada.freebuild.command.tpa.CommandTpa;
import de.ng.nizada.freebuild.command.tpa.CommandTpaAccept;
import de.ng.nizada.freebuild.command.tpa.CommandTpaDeny;
import de.ng.nizada.freebuild.command.tpa.CommandTpaHere;
import de.ng.nizada.freebuild.command.tpa.CommandTpaList;
import de.ng.nizada.freebuild.command.warp.CommandCreateAliasesWarp;
import de.ng.nizada.freebuild.command.warp.CommandCreateWarp;
import de.ng.nizada.freebuild.command.warp.CommandDeleteWarp;
import de.ng.nizada.freebuild.command.warp.CommandListWarps;
import de.ng.nizada.freebuild.command.warp.CommandRemoveAliasesWarp;
import de.ng.nizada.freebuild.command.warp.CommandWarp;
import de.ng.nizada.freebuild.crafting.CraftingRecipeManager;
import de.ng.nizada.freebuild.discord.DiscordManager;
import de.ng.nizada.freebuild.listener.ListenerBlockBreakEvent;
import de.ng.nizada.freebuild.listener.ListenerEntityDamageByEntityEvent;
import de.ng.nizada.freebuild.listener.ListenerEntityDamageEvent;
import de.ng.nizada.freebuild.listener.ListenerEntityPickupItemEvent;
import de.ng.nizada.freebuild.listener.ListenerEntitySpawnEvent;
import de.ng.nizada.freebuild.listener.ListenerFoodLevelChangeEvent;
import de.ng.nizada.freebuild.listener.ListenerPlayerChatEvent;
import de.ng.nizada.freebuild.listener.ListenerPlayerCommandPreprocessEvent;
import de.ng.nizada.freebuild.listener.ListenerPlayerInteractEntityEvent;
import de.ng.nizada.freebuild.listener.ListenerPlayerInteractEvent;
import de.ng.nizada.freebuild.listener.ListenerPlayerJoinEvent;
import de.ng.nizada.freebuild.listener.ListenerPlayerQuitEvent;
import de.ng.nizada.freebuild.mobcatcher.MobCatcherManager;
import de.ng.nizada.freebuild.scoreboard.ScoreboardManager;
import de.ng.nizada.freebuild.tpa.TpaManager;

public class Freebuild extends JavaPlugin {

	public static final String PREFIX = "§8[§4N§ci§4Z§ca§4D§ca§8] ";

	public static Freebuild instance;

	public Freebuild() {
		instance = this;
	}
	
	@Override
	public void onLoad() { }

	@Override
	public void onEnable() {
		registerListener();
		registerCommands();
		
		new CraftingRecipeManager();
		new MobCatcherManager();

		CallbackManager.startScheduler();
		TpaManager.startScheduler();
		ScoreboardManager.SCOREBOARD_MANAGER.setup();
		DiscordManager.DISCORD_MANAGER.connect();
	}

	@Override
	public void onDisable() {
		if(DiscordManager.DISCORD_MANAGER.getDiscordClient() != null)
			DiscordManager.DISCORD_MANAGER.getDiscordClient().logout();
		ScoreboardManager.SCOREBOARD_MANAGER.removeAllScoreboards();
	}

	public void registerListener() {
		getServer().getPluginManager().registerEvents(new ListenerPlayerChatEvent(), this);
		getServer().getPluginManager().registerEvents(new ListenerPlayerJoinEvent(), this);
		getServer().getPluginManager().registerEvents(new ListenerPlayerQuitEvent(), this);
		getServer().getPluginManager().registerEvents(new ListenerBlockBreakEvent(), this);
		getServer().getPluginManager().registerEvents(new ListenerEntityDamageEvent(), this);
		getServer().getPluginManager().registerEvents(new ListenerEntityDamageByEntityEvent(), this);
		getServer().getPluginManager().registerEvents(new ListenerFoodLevelChangeEvent(), this);
		getServer().getPluginManager().registerEvents(new ListenerEntitySpawnEvent(), this);
		getServer().getPluginManager().registerEvents(new ListenerPlayerInteractEvent(), this);
		getServer().getPluginManager().registerEvents(new ListenerPlayerInteractEntityEvent(), this);
		getServer().getPluginManager().registerEvents(new ListenerEntityPickupItemEvent(), this);
		getServer().getPluginManager().registerEvents(new ListenerPlayerCommandPreprocessEvent(), this);
	}

	public void registerCommands() {
		getCommand("gamemode").setExecutor(new CommandGameMode());
		getCommand("invsee").setExecutor(new CommandInvsee());
		getCommand("claimtool").setExecutor(new CommandClaimTool());
		getCommand("admintool").setExecutor(new CommandAdminTool());
		getCommand("fly").setExecutor(new CommandFly());
		getCommand("godmode").setExecutor(new CommandGodMode());
		getCommand("warp").setExecutor(new CommandWarp());
		getCommand("createwarp").setExecutor(new CommandCreateWarp());
		getCommand("deletewarp").setExecutor(new CommandDeleteWarp());
		getCommand("listwarp").setExecutor(new CommandListWarps());
		getCommand("createaliaseswarp").setExecutor(new CommandCreateAliasesWarp());
		getCommand("removealiaseswarp").setExecutor(new CommandRemoveAliasesWarp());
		getCommand("home").setExecutor(new CommandHome());
		getCommand("createhome").setExecutor(new CommandCreateHome());
		getCommand("deletehome").setExecutor(new CommandDeleteHome());
		getCommand("listhome").setExecutor(new CommandListHomes());
		getCommand("say").setExecutor(new CommandSay());
		getCommand("ping").setExecutor(new CommandPing());
		getCommand("clearchat").setExecutor(new CommandClearChat());
		getCommand("vanish").setExecutor(new CommandVanish());
		getCommand("changename").setExecutor(new CommandChangeName());
		getCommand("enderchest").setExecutor(new CommandEnderchest());
		getCommand("spawn").setExecutor(new CommandSpawn());
		getCommand("help").setExecutor(new CommandHelp());
		getCommand("addhelpmessage").setExecutor(new CommandAddHelpMessage());
		getCommand("removehelpmessage").setExecutor(new CommandRemoveHelpMessage());
		getCommand("updatehelpmessage").setExecutor(new CommandUpdateHelpMessage());
		getCommand("listhelpmessage").setExecutor(new CommandListHelpMessage());
		getCommand("freebuildreload").setExecutor(new CommandFreebuildReload());
		getCommand("link").setExecutor(new CommandLink());
		getCommand("tpa").setExecutor(new CommandTpa());
		getCommand("tpaaccept").setExecutor(new CommandTpaAccept());
		getCommand("tpadeny").setExecutor(new CommandTpaDeny());
		getCommand("tpalist").setExecutor(new CommandTpaList());
		getCommand("tpahere").setExecutor(new CommandTpaHere());
	}
}