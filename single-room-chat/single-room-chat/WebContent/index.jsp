<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% String WsUrl = getServletContext().getInitParameter("WsUrl");
System.out.println(WsUrl);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name='viewport' content='minimum-scale=1.0, initial-scale=1.0,
	width=device-width, maximum-scale=1.0, user-scalable=no'/>
<title>single-room-chat</title>
<link rel="stylesheet" type="text/css" href="content/styles/site.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="scripts/chatroom.js"></script>
<script type="text/javascript">
var wsUri = '<%=WsUrl%>';
var proxy = CreateProxy(wsUri);

document.addEventListener("DOMContentLoaded", function(event) {
	console.log(document.getElementById('loginPanel'));
	proxy.initiate({
		loginPanel: document.getElementById('loginPanel'),
		msgPanel: document.getElementById('msgPanel'),
		txtMsg: document.getElementById('txtMsg'),
		txtLogin: document.getElementById('txtLogin'),
		msgContainer: document.getElementById('msgContainer'),
		room: document.getElementById('room'),
		roomChat: document.getElementById('roomChat'),
		addNewMember: document.getElementById('addNewMember'),
	});
});

</script>
</head>
<body>
<div id="container">
	<div id="loginPanel">
		<div id="infoLabel">Type a name to join the room</div>
		<div style="padding: 10px;">
			<input id="txtLogin" type="text" class="loginInput"
				onkeyup="proxy.login_keyup(event)" />
			<button type="button" class="loginInput" onclick="proxy.login()">Login</button>
		</div>
	</div>
	<div id="room" style="display: none">
		<div id="roomChat">
			<button class="btn-room" onclick="proxy.addMember()">Tạo nhóm chát</button>
			<button class="btn-room" onclick="proxy.addFriend()" style="margin-top: 20px :">Kết bạn</button>
			<hr />
			<div id="showroom"></div>
		</div>
		 <div id="id01" class="w3-modal">
		    <div class="w3-modal-content w3-card-4">
		      <header class="w3-container w3-teal"> 
		        <span onclick="document.getElementById('id01').style.display='none'" 
		        class="w3-button w3-display-topright">&times;</span>
		        <h2>Thêm nhóm trò chuyện</h2>
		      </header>
		      <div class="w3-container">
		        <p>Tên nhóm trò chuyện</p>
		        <input id="nameChat" value="">
		        <div>
		        <span>Thêm thành viên</span>
		        <div id="addNewMember">
		        
		        </div>
		        </div>
		        <p>Some text..</p>
		      </div>
		      <footer class="w3-container w3-teal">
		         <div class="btn-group text-right" style= "float: right">
				    <button type="button" onclick="document.getElementById('id01').style.display='none'" class="btn btn-danger">hủy</button>
				    <button type="button" onclick="proxy.createNewRoom()" id="submit" class="btn btn-success">Ok</button>
				  </div>
		      </footer>
		    </div>
		  </div>
		<div id="msgPanel" style="width: 500px">
			<div id="msgContainer" style="overflow-y: auto;" >
			<section  id ="boxContainer">
				<div class = "chatBox">
				</div>
			</section >
			</div>
			<div id="msgController">
				<textarea id="txtMsg" 
					title="Enter to send message"
					onkeyup="proxy.sendMessage_keyup(event)"
					style="height: 20px; width: 100%"></textarea>
				<button style="height: 30px; width: 100px" type="button"
					onclick="proxy.logout()">Logout</button>
			</div>
		</div>
	</div>>
	
</div>
</body>
</html>