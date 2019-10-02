var CreateProxy = function(wsUri) {
	var websocket = null;
	var audio = null;
	var elements = null;
	
	var playSound = function() {
		if (audio == null) {
			audio = new Audio('content/sounds/beep.wav');
		}
		
		audio.play();
	};
	
	var showRoom = function() {
			elements.loginPanel.style.display = "none";
			elements.room.style.display = "flex";
			elements.msgContainer.style.display = "block";
			elements.txtMsg.focus();
	};
			
	var hideMsgPanel = function() {
			elements.loginPanel.style.display = "block";
			elements.msgPanel.style.display = "none";
			elements.txtLogin.focus();
	};
	
	var displayMessage = function(msg) {
		if (elements.msgContainer.childNodes.length == 100) {
			elements.msgContainer.removeChild(elements.msgContainer.childNodes[0]);
		}
		
		var div = document.createElement('div');
		div.className = 'msgrow';
		var textnode = document.createTextNode(msg);
		div.appendChild(textnode); 
		elements.msgContainer.appendChild(div);
	};
	
	var displayRoom = function(room) {
//		const myNode = document.getElementById("showroom");
//		  while (myNode.firstChild) {
//		    myNode.removeChild(myNode.firstChild);
//		  }
		for(var i = 0;i<room.length;i++){
			var button = document.createElement('BUTTON');
			button.className = 'btn-room';
			button.innerHTML = room[i];
			
			var nameRoom = room[i];
			button.onclick = function(e){
				sendMessageJoinRoom(e.target.innerHTML);
			}
			document.getElementById('showroom').appendChild(button)
//			elements.roomChat.appendChild(button);
		}
	};
	var displayUser = function(room){
		const myNode = elements.addNewMember;
		  while (myNode.firstChild) {
		    myNode.removeChild(myNode.firstChild);
		  }
		for(var i = 0;i<room.length;i++){
			var div = document.createElement('div');
			div.style = "display: flex"
			var input = document.createElement('input');
			input.className = 'user_input';
			input.type = "checkbox";
			input.style = "margin: 5px 10px";
			input.value = room[i];
			var p = document.createElement('p');
			p.style = "margin: 0";
			p.innerHTML = room[i];
			div.appendChild(input);
			div.appendChild(p);
			elements.addNewMember.appendChild(div);
		}
	}
	var clearMessage = function() {   
		elements.msgContainer.innerHTML = '';
	};
	var sendMessageJoinRoom = function(nameRoom) {
		if (websocket != null && websocket.readyState == 1) {		
			var message = { messageType: 'JOINROOM', message: nameRoom };
			// Send a message through the web-socket
			websocket.send(JSON.stringify(message));
		}
	}
	return {
		login: function() {
			elements.txtLogin.focus();
			
			var name = elements.txtLogin.value.trim();
			if (name == '') { return; }
			
			elements.txtLogin.value = '';
			
			// Initiate the socket and set up the events
			if (websocket == null) {
		    	websocket = new WebSocket(wsUri);
		    	websocket.onopen = function() {
		    		var message = { messageType: 'LOGIN', message: name };
		    		websocket.send(JSON.stringify(message));
		    		console.log(JSON.stringify(message));
		        };
		        websocket.onmessage = function(e) {
		        	showRoom();
		        	playSound();
		        	var obj = JSON.parse(e.data);
		        	if(obj.MessageType == "SHOWROOM"){
		        		console.log(obj);
		        		message = JSON.parse(obj.Message);
			        	displayRoom(message.arrRoom);
			        	displayMessage(message.mesage);
		        	}
		        	if(obj.MessageType =="CHAT"){
		        		console.log(obj);
		        		displayMessage(obj.Message)
		        	}
		        	if(obj.MessageType == "SHOWUSER"){
		        		console.log(obj);
		        		message = JSON.parse(obj.Message);
			        	displayUser(message.arrRoom);
		        	}
		        	
		        };
		        websocket.onerror = function(e) {};
		        websocket.onclose = function(e) {
		        	websocket = null;
		        	clearMessage();
		        	hideMsgPanel();
		        };
			}
		},
		sendMessage: function() {
			elements.txtMsg.focus();
			
			if (websocket != null && websocket.readyState == 1) {
				var input = elements.txtMsg.value.trim();
				if (input == '') { return; }
				elements.txtMsg.value = '';
				var message = { messageType: 'MESSAGE', message: input };
				
				// Send a message through the web-socket
				websocket.send(JSON.stringify(message));
			}
		},
		login_keyup: function(e) { if (e.keyCode == 13) { this.login(); } },
		sendMessage_keyup: function(e) { if (e.keyCode == 13) { this.sendMessage(); } },
		logout: function() {
			if (websocket != null && websocket.readyState == 1) { websocket.close();}
		},
		initiate: function(e) {
			elements = e;
			elements.txtLogin.focus();
		},
		addMember: function() {
			document.getElementById('id01').style.display='block';
			if (websocket != null && websocket.readyState == 1) {
				var name = elements.txtLogin.value.trim();
	    		var message = { messageType: 'LOGIN', message: name };
	    		console.log('vao day add room');
	    		websocket.send(JSON.stringify(message));
			}
		},
		createNewRoom: function(){
			checkboxes = elements.addNewMember.querySelectorAll("input[type=checkbox]");
			var nameRoom = document.getElementById('nameChat').value;
			var checked = [];
			for (var i = 0; i < checkboxes.length; i++) {
				var checkbox = checkboxes[i];
				if (checkbox.checked) checked.push(checkbox.value);
			}
			
			var message = { messageType: 'ADDROOM', message: JSON.stringify(checked), nameRoom:nameRoom };	
				// Send a message through the web-socket
			console.log(message)
			websocket.send(JSON.stringify(message));
			document.getElementById('id01').style.display='none'

		}
	};
};