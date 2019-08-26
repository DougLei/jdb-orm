package com.douglei.orm;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.tools.utils.ExceptionUtil;

public class SocketClient {
	private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);
	
	private Socket socket;
	private OutputStream os;
	private String serverHost;
	private int serverPort;
	
	public SocketClient(String serverHost, int serverPort) {
		this.serverHost = serverHost;
		this.serverPort = serverPort;
	}
	
	public void sendMessage(String message) {
		try {
			socket = new Socket(serverHost, serverPort);
			os = socket.getOutputStream();
			os.write(getHexByteArray(message));
			os.flush();
		} catch (IOException e) {
			logger.error("socket连接发送消息时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
		} finally {
			close();
		}
	}
	
	/**
	 * 将消息转换为16进制byte数组
	 * @param message
	 * @return
	 */
	private static byte[] getHexByteArray(String message) {
		String[] ms = message.split(" ");
		byte[] b = new byte[ms.length];
		for (int i = 0; i < b.length; i++) {
			b[i] = Byte.parseByte(ms[i], 16);
			System.out.print(b[i] + "  ");
		}
		return b;
	}
	
	public static void main(String[] args) {
		SocketClient client = new SocketClient("192.168.1.252", 504);
		client.sendMessage("01 00 02 05 07 08 09 10 11 12 13 14 15 16 17 18 70");
	}

	private void close() {
		if(os != null) {
			try {
				os.close();
			} catch (IOException e) {
				logger.error("在关闭OutputStream时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			}
			os = null;
		}
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				logger.error("在关闭socket连接时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			}
			socket = null;
		}
	}
}
