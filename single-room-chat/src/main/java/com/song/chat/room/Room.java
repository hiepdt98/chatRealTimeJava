package com.song.chat.room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.Session;

public class Room {
	private Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
	private String nameRoom;
	
	public String getNameRoom() {
		return nameRoom;
	}
	public void setNameRoom(String nameRoom) {
		this.nameRoom = nameRoom;
	}
	public synchronized void join(Session session) { sessions.add(session); }
	public synchronized void leave(Session session) { sessions.remove(session); }
	public synchronized void sendMessage(String message) {
		for (Session session: sessions) {
			if (session.isOpen()) {
				try {
					session.getBasicRemote().sendText(message);
					System.out.println("send message success");
				}
				catch (IOException e) { e.printStackTrace(); }
			}
		}
	}
	public void disPlayAllMemberInRoom() {
		for(Session session : sessions) {
			System.out.println(session.getUserProperties().get("name"));
		}
	}
	public Set<Session> getSessions() {
		return sessions;
	}
	public void setSessions(Set<Session> sessions) {
		this.sessions = sessions;
	}
	
}
