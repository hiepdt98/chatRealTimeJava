package com.song.chat.message;

public class ChatMessage {
	private MessageType messageType;
	private String message;
	private String nameRoom;
	
	public void setMessageType(MessageType v) { this.messageType = v; }
	public MessageType getMessageType() { return messageType; }
	public void setMessage(String v) { this.message = v; }
	public String getMessage() { return this.message; }
	public String getNameRoom() {
		return this.nameRoom;
	}
	public void setNameRoom(String nameRoom) {
		this.nameRoom = nameRoom;
	}
}
