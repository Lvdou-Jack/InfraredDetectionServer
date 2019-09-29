package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/*
 * 客户端
 */
public class Client {
	int port = 1234;
	String server_ip = "47.103.21.160";//
	String message = "HelloServer";
	Socket socket = null;
	OutputStream outputStream = null;
	byte id = 1;
	byte type = 1;

	public void work() {
		// 1、创建客户端Socket，指定服务器地址和端口
		try {
			socket = new Socket(server_ip, port);
			// 2、获取输出流，向服务器端发送信息
			outputStream = socket.getOutputStream();// 获取字节输出流

			// 首先将string换成字节数组
			byte[] sendBytes = message.getBytes("UTF-8");
			
			// 然后将客户端的类型发出去
			outputStream.write(id);
			
			//发送消息类型
			outputStream.write(type);
			
			// 然后将消息的长度发送出去
			outputStream.write(sendBytes.length >> 8);
			outputStream.write(sendBytes.length);
			System.out.println(sendBytes.length);
			
			//然后将消息发送出去
		    outputStream.write(sendBytes);
		    outputStream.flush();
		    
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			// 4、关闭资源
			try {
				outputStream.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	public static void main(String[] args) {
		new Client().work();// 客户端运行
	}
}
