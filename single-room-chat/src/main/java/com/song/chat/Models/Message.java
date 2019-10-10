package com.song.chat.Models;

public class Message {
	private String content;
	private String date;
	private String name;
	
	public Message(){
		
	}
	public Message(String content, String date, String name) {
		this.content = content;
		this.date = date;
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
