package de.ng.nizada.freebuild.chatsystem;

import java.util.ArrayList;
import java.util.List;

public class ChatLog {
	
	public static final ChatLog CHAT_LOG = new ChatLog();
	
	private List<String> chatMessages;
	
	public ChatLog() {
		chatMessages = new ArrayList<>();
	}
	
	public void clearMessages() {
		chatMessages.clear();
	}
	
	public void removeMessage(String message) {
		chatMessages.remove(message);
	}
	
	public void addMessage(String message) {
		chatMessages.add(message);
		if(chatMessages.size() > 100)
			chatMessages.remove(0);
	}
	
	public void setChatMessages(List<String> chatMessages) {
		this.chatMessages = chatMessages;
	}
	
	public List<String> getChatMessages() {
		return chatMessages;
	}
}