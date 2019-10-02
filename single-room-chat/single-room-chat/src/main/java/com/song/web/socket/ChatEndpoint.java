package com.song.web.socket;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.song.chat.message.ChatMessage;
import com.song.chat.message.MessageType;
import com.song.chat.room.Room;
import com.song.chat.room.MessageSend;
import com.song.chat.message.ResponseMessage;
import java.util.*;	
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

@ServerEndpoint(value = "/chat")
public class ChatEndpoint {
	private Logger log = Logger.getLogger(ChatEndpoint.class.getSimpleName());
	private static Set<Room> rooms = Collections.synchronizedSet(new HashSet<>());
	private static Set<Session> totalSession = Collections.synchronizedSet(new HashSet<>());
	@OnOpen
	public void onOpen(final Session session, EndpointConfig config) {}

	@OnMessage
	public void onMessage(final Session session, final String messageJson) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ChatMessage chatMessage = null;
		System.out.println(messageJson);
		try {
			chatMessage = mapper.readValue(messageJson, ChatMessage.class);
		} catch (IOException e) {
			String message = "Badly formatted message";
			try {
				session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, message));
			} catch (IOException ex) { log.severe(ex.getMessage()); }
		} ;

		Map<String, Object> properties = session.getUserProperties();
		if (chatMessage.getMessageType() == MessageType.LOGIN) {
			Gson gson = new Gson();
			String name = chatMessage.getMessage();
			//dinh danh cho session room va nguoi gui
			if(properties.get("name") == null) {
				properties.put("name", name);
				System.out.println("vao day");
			}
//			properties.put("room", name);
//			Room room = new Room();
//			room.setNameRoom(name);
//			room.join(session);
			// send user message
		
			MessageSend messageSendUser = new MessageSend();
			for(Session user : totalSession) {
				if(user.equals(session)) {
					continue;
				}
				else {
					messageSendUser.addRoom((String)user.getUserProperties().get("name"));
				}
			}
			String jsonUser = gson.toJson(messageSendUser);
			ResponseMessage responseUser = new ResponseMessage("SHOWUSER",jsonUser);
			String responseMessageUser = gson.toJson(responseUser);
			session.getBasicRemote().sendText(responseMessageUser);
			
			//send message Room for clinet
			
//			MessageSend messageSendRoom = new MessageSend();
//			String message = name + "You are joined the new chat room";
//			messageSendRoom.setMesage(message);
//			for(Room roomchat : rooms) {
//				messageSendRoom.addRoom(roomchat.getNameRoom());
//			}
//			//convert onject to json to send client
//			String json = gson.toJson(messageSendRoom);
////			// add name message and rooms into message to client
//			ResponseMessage responseRoom = new ResponseMessage("SHOWROOM",json);
//			String responseMessageRoom = gson.toJson(responseRoom);
//			room.sendMessage(responseMessageRoom);
//			rooms.add(room);
			// khi login thi se duoc luu total session 
			totalSession.add(session);
			
		}
		if(chatMessage.getMessageType() == MessageType.JOINROOM) {
			System.out.println("vao day join team");
			String nameRoom = chatMessage.getMessage();
			properties.put("room", nameRoom);
			for(Room room : rooms) {
				if(nameRoom.equals(room.getNameRoom())) {
					room.join(session);
				}
			}
		}
		if(chatMessage.getMessageType() == MessageType.MESSAGE) {
			System.out.println("vao day gui mess age");
			String message = chatMessage.getMessage();
			String nameRoom =(String) properties.get("room");
			String nameClient =(String) properties.get("name");
			System.out.println("name room : " + nameRoom);
			System.out.println("name : " + nameClient);
			System.out.println("message:" + message);
			System.out.println("peple in room : ");
			for(Room room : rooms) {
				if(room.getNameRoom().equals(nameRoom)) {
					ResponseMessage response = new ResponseMessage("CHAT", message);
					Gson gson = new Gson();
					String responseMessage = gson.toJson(response);
					room.sendMessage(responseMessage);
				}
			}
		}
		if(chatMessage.getMessageType() == MessageType.ADDROOM) {
			try {
				ArrayList<String> users = mapper.readValue(chatMessage.getMessage(), new TypeReference<ArrayList<String>>() {});
				String nameRoom = chatMessage.getNameRoom();
				
				properties.put("room", nameRoom);
				System.out.println("name room :" + nameRoom);
				System.out.println();
				Room newRoom = new Room();
				newRoom.setNameRoom(nameRoom);
				for(Session totalsession : totalSession) {
					for(String user: users) {
						if(totalsession.getUserProperties().get("name").equals(user)) {
							newRoom.join(totalsession);
						}
					}
					
				}
				newRoom.join(session);
				MessageSend messageSendRoom = new MessageSend();
				rooms.add(newRoom);
				String message = "You are joined the new chat room";
				messageSendRoom.setMesage(message);
				messageSendRoom.addRoom(nameRoom);
				Gson gson = new Gson();
				//convert onject to json to send client
				String json = gson.toJson(messageSendRoom);
//				// add name message and rooms into message to client
				ResponseMessage responseRoom = new ResponseMessage("SHOWROOM",json);
				String responseMessageRoom = gson.toJson(responseRoom);
				newRoom.sendMessage(responseMessageRoom);
				newRoom.disPlayAllMemberInRoom();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("tao phong thanh cong");
		}
	}

	@OnClose
	public void OnClose(Session session, CloseReason reason) {
//		room.leave(session);
//		room.sendMessage((String)session.getUserProperties().get("name") + " - Left the room");
	}

	@OnError
	public void onError(Session session, Throwable ex) { log.info("Error: " + ex.getMessage()); }
}
