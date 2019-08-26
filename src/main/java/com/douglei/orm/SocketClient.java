package com.douglei.orm;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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
			os.write(getHexByteArray(message.getBytes(StandardCharsets.UTF_8)));
			os.flush();
		} catch (IOException e) {
			logger.error("socket连接发送消息时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
		} finally {
			close();
		}
	}
	
	/**
	 * 将字符串转换为16进制byte数组
	 * @param bytes
	 * @return
	 */
	private byte[] getHexByteArray(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
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
