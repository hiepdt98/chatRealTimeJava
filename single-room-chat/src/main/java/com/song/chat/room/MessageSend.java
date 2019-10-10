package com.song.chat.room;

import java.util.ArrayList;

public class MessageSend {
	private String mesage;
	private ArrayList<String> arrRoom = new ArrayList<>();
	

	public String getMesage() {
		return mesage;
	}
	public void setMesage(String mesage) {
		this.mesage = mesage;
	}
	public ArrayList<String> getArrRoom() {
		return arrRoom;
	}
	public void setArrRoom(ArrayList<String> arrRoom) {
		this.arrRoom = arrRoom;
	}
	public void addRoom(String nameRoom) {
		this.arrRoom.add(nameRoom);
	}
	
}
