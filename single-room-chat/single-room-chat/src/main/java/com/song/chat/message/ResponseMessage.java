package com.song.chat.message;

public class ResponseMessage {
	private String MessageType;
	private String Message;
	
	
	ResponseMessage(){
		
	}
	public ResponseMessage(String messageType, String message) {
		MessageType = messageType;
		Message = message;
	}
	public String getMessageType() {
		return MessageType;
	}
	public void setMessageType(String messageType) {
		MessageType = messageType;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	
	
}
