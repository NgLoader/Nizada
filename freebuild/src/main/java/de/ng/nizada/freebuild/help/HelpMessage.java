package de.ng.nizada.freebuild.help;

public class HelpMessage {
	
	public String message;
	public String permission;
	
	public int lineNumber;
	
	public HelpMessage(String message, String permission, int lineNumber) {
		this.message = message;
		this.permission = permission;
		this.lineNumber = lineNumber;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getPermission() {
		return permission;
	}
}