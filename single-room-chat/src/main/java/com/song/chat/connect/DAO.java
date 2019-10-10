package com.song.chat.connect;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.song.chat.Models.Message;

/**
 *
 * @author Thinkpad
 */
public class DAO{
    private Connection conn;
    public DAO(){
    }
    public Connection getConnect(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;instance=ADALINE;databaseName=ChatRealTime";
            String user = "sa";
            String password = "123" ;
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("connect thanh cong");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn; 
    }
    public boolean addMessage(Message message) {
   	 String sql = "INSERT INTO tblMessages(CONTENT, TIME, NAMEROOM) VALUES(?,?,?)";
   	 System.out.println("voa day ghi file");
   	 try {
   		 	Connection cnn = new DAO().getConnect();
   		 	PreparedStatement ps = cnn.prepareStatement(sql);
            ps.setString(1, message.getContent());
            ps.setString(2, message.getDate());
            ps.setString(3, message.getName());
            int check = ps.executeUpdate();
            cnn.close();
            System.out.println("add message complete");
            return check>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
   	 	
        return false;
    }
    public ArrayList<Message> getMessage(String name) {
    	ArrayList<Message> listMessage  = new ArrayList<Message>();
    	String sql ="SELECT * FROM tblMessages WHERE nameRoom= ?";
    	
    	 try {
    		 Connection cnn = new DAO().getConnect();
    		 PreparedStatement ps = cnn.prepareStatement(sql);
             ps.setString(1, name);
             ResultSet rs = ps.executeQuery();
             while(rs.next()){
            	 Message message = new Message();
            	 message.setContent(rs.getString("CONTENT"));
            	 message.setDate(rs.getString("TIME"));
            	 message.setName(rs.getString("NAMEROOM"));
            	 listMessage.add(message);
             }
            cnn.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
         return listMessage;
    }
    public static void main(String[] args) {
    	ArrayList<Message> listMessage = new DAO().getMessage("phong5");
    	for(Message mess : listMessage) {
    		System.out.println(mess.getContent());
    	}
	}
}
