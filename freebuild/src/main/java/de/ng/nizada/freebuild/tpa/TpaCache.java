package de.ng.nizada.freebuild.tpa;

import java.util.UUID;

import org.bukkit.Location;

public class TpaCache {

	public UUID sender;
	public UUID target;
	public String senderName;
	public String targetName;
	public Long duration;
	public Location location;

	public TpaCache(UUID sender, UUID target, String senderName, String targetName, Long duration, Location location) {
		this.sender = sender;
		this.target = target;
		this.senderName = senderName;
		this.targetName = targetName;
		this.duration = duration;
		this.location = location;
	}
}