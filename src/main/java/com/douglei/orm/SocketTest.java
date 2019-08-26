package com.douglei.orm;

public class SocketTest {
	public static void main(String[] args) {
		SocketClient client = new SocketClient("192.168.1.252", 504);
		client.sendMessage("1");
	}
}
