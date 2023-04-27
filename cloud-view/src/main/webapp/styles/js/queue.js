var stompClient = null;
$(function() {
	try {
		connect();
	} catch (err) {
	}
})
window.onunload = function() {
	try {
		disconnect();
	} catch (err) {
	}
}
function connect() {
	var userName = sessionStorage.getItem("user_name");
	try {
		if(WEBSOCKET_URL){
			var socket = new SockJS(WEBSOCKET_URL+'/queueServer'); // 连接SockJS的endpoint名称为"endpointOyzc"
			stompClient = Stomp.over(socket);// 使用STMOP子协议的WebSocket客户端
			stompClient.connect({}, function(frame) {// 连接WebSocket服务端
				stompClient.subscribe("/user/" + userName + "/queue/message", function(response) {
					var code = JSON.parse(response.body);
					showResponse(code)
				});
			});
		}
	}catch (err) {
	}
}
function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	console.log("Disconnected");
}
function showResponse(res) {
	if(res.success){
		showMsgBRN(t_alert_title, res.message, 5);
	}else{
		showMsgBRN(t_alert_title, res.message, 1);
	}
}