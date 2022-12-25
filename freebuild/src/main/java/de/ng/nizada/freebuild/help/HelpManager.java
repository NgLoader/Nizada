package de.ng.nizada.freebuild.help;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.permissions.Permissible;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.gamecore.util.PropertiesUtil;

public class HelpManager {
	
	public static final HelpManager HELP_MANAGER = new HelpManager();
	
	private List<HelpMessage> helpMessage;
	
	public HelpManager() {
		this.helpMessage = new ArrayList<>();
		load();
	}
	
	public void reload() {
		this.helpMessage.clear();
		load();
	}
	
	public void load() {
		Properties properties = PropertiesUtil.load(new File(Freebuild.instance.getDataFolder(), "help.properties"));
		
		properties.forEach((key, value) -> {
			int lineNumber = Integer.valueOf(((String) key).split("-")[0]);
			String permission = ((String) key).substring(String.valueOf(lineNumber).length() + 1);
			String message = (String) value;
			
			this.helpMessage.add(new HelpMessage(message, permission, lineNumber));
		});
	}
	
	public void save() {
		Properties properties = new Properties();
		
		for(HelpMessage message : this.helpMessage)
			properties.put(message.lineNumber + "-" + message.permission, message.message);
		
		PropertiesUtil.save(new File(Freebuild.instance.getDataFolder(), "help.properties"), properties);
	}
	
	public void editLine(int line, String message) {
		this.helpMessage.get(line).message = message;
		save();
	}
	
	public void removeLine(int line) {
		this.helpMessage.remove(getLine(line));
		save();
	}
	
	public void setLine(int line, String message) {
		getLine(line).message = message;
		save();
	}
	
	public void setLine(int line, String message, String permission) {
		HelpMessage helpMessage = getLine(line);
		helpMessage.message = message;
		helpMessage.permission = permission;
		save();
	}
	
	public void addLine(String message) {
		addLine(message, "");
	}
	
	public void addLine(String message, String permission) {
		this.helpMessage.add(new HelpMessage(message, permission, this.helpMessage.size()));
		save();
	}
	
	public void addLine(int line, String message) {
		addLine(line, message, "");
	}
	
	public void addLine(int line, String message, String permission) {
		this.helpMessage.add(line, new HelpMessage(message, permission, line));
		save();
	}
	
	public HelpMessage getLine(int line) {
		for(HelpMessage message : this.helpMessage)
			if(message.lineNumber == line)
				return message;
		return null;
	}
	
	public String buildHelpMessage(Permissible permissible) {
		/*
		return ChatColor.translateAlternateColorCodes('&',
				String.join("\n", this.helpMessage.stream()
				.filter(message -> message.getValue().isEmpty() ? true : permissible.hasPermission(message.getValue()))
				.map(message -> message.getKey())
				.collect(Collectors.toSet()))
				.replaceAll("%PREFIX%", Freebuild.PREFIX));
		*/
		
		return ChatColor.translateAlternateColorCodes('&',
				String.join("\n", this.helpMessage.stream()
						.filter(helpMessage -> permissible.hasPermission(helpMessage.getPermission()))
						.sorted(Comparator.comparing(HelpMessage::getLineNumber))
						.map(helpMessage -> helpMessage.getMessage())
						.collect(Collectors.toList()))
				.replaceAll("%PREFIX%", Freebuild.PREFIX));
	}
	
	public List<HelpMessage> getHelpMessage() {
		return Collections.unmodifiableList(this.helpMessage);
	}
}