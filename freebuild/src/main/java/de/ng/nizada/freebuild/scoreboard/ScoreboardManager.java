package de.ng.nizada.freebuild.scoreboard;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.ng.nizada.api.rank.Rank;
import de.ng.nizada.gamecore.util.RankUtil;

public class ScoreboardManager {
	
	public static final ScoreboardManager SCOREBOARD_MANAGER = new ScoreboardManager();
	
	private HashMap<Rank, Team> teams;
	
	public ScoreboardManager() {
		teams = new HashMap<>();
	}
	
	public void setup() {
		for(Rank rank : Rank.values())
			createTeam(rank);
		Bukkit.getOnlinePlayers().forEach(player -> addPlayerToScoreboard(player, RankUtil.getRankByPermission(player)));
	}
	
	public void reloadScoreboard() {
		removeAllScoreboards();
		Bukkit.getOnlinePlayers().forEach(player -> addPlayerToScoreboard(player, RankUtil.getRankByPermission(player)));
	}
	
	public void addPlayerToScoreboard(Player player, Rank rank) {
		if(teams.containsKey(rank))
			teams.get(rank).addEntry(player.getName());
		else
			createTeam(rank).addEntry(player.getName());
	}
	
	public void removePlayerFromScoreboard(Player player) {
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

		Team team = scoreboard.getEntryTeam(player.getName());
		if (team != null)
			team.removeEntry(player.getName());
	}
	
	public void addAllPlayersToScoreboard(List<Player> players) {
		players.forEach(player -> addPlayerToScoreboard(player, RankUtil.getRankByPermission(player)));
	}
	
	public void removeAllScoreboards() {
		teams.clear();
		for(Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams())
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard teams remove " + team.getName());
	}
	
	public Team createTeam(Rank rank) {
		String teamName = getTeamName(rank);
		
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = scoreboard.getTeam(teamName);
		if(team == null) {
			team = scoreboard.registerNewTeam(teamName);
			String prefix = rank.getColorCode() + rank.getDisplayName() + " §8» §7";
			int i = rank.getDisplayName().length();
			
			while(prefix.length() > 16)
				prefix = rank.getColorCode() + rank.getDisplayName().substring(0, (i -= 1)) + " §8» §7";
			
			team.setPrefix(prefix);
		}
		if(!teams.containsKey(rank))
			teams.put(rank, team);
		return team;
	}
	
	public String getTeamName(Rank rank) {
		String teamName = "ABCEDFGHIJKLMNOPQRSTUFWXYZ".charAt(rank.ordinal()) + "_" + rank.getFullDisplayName().toUpperCase();
		if(teamName.length() > 16)
			teamName = teamName.substring(0, 16);
		return teamName;
	}
}