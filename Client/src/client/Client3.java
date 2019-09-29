package client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.imageio.stream.FileImageInputStream;

public class Client3 {
	int port = 1234;
	String server_ip = "47.103.21.160";
	Socket socket = null;
	OutputStream outputStream = null;
	byte type = (byte) 255;
	
//	String path = "F:\\test\\netty03.png";//F:\test\netty3
	String path = "F:\\test\\netty3\\netty03.png";
	FileImageInputStream inputImage = null;
	ByteArrayOutputStream outputArrayStream = null;
	byte [] data = null;
	
	public void work() {
		try {
			//1.获取本地数据
			inputImage = new FileImageInputStream(new File(path));
			outputArrayStream = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = inputImage.read(buf)) != -1) {
				       outputArrayStream.write(buf, 0, numBytesRead);
			}
			data = outputArrayStream.toByteArray();
			System.out.println("将要发送的数据长度为"+data.length+"(byte)额外还有1byte  type数据");
			
			// 2.创建客户端Socket，指定服务器地址和端口
			socket = new Socket(server_ip, port);
			// 3.获取输出流，向服务器端发送信息
			outputStream = socket.getOutputStream();// 获取字节输出流
			
			/**
			 * type       data
			 * 1byte      2333byte
			 * type代表请求类型,0是app请求,1是单片机传输数据*/
			
			//4.发送消息类型
			outputStream.write(type);
			
			//5.然后将消息发送出去
		    outputStream.write(data);
		    outputStream.flush();
		    
		    System.out.println("接收返回消息");
		    InputStream inputStream = socket.getInputStream();
		    BufferedReader b1 = new BufferedReader(new InputStreamReader(inputStream));
		    System.out.println("readLine");
		    String mes = b1.readLine();
		    System.out.println("消息:"+mes);
		    
//		  //接收服务器返回的消息
//		    InputStream inputStream = socket.getInputStream();
//            System.out.println("接收返回消息");
//            inputStream.read();
//            int size = 0;
//            do{
//            	size = inputStream.available();
//            	System.out.println("size:"+size);
//            }while(size==0);
//            
//            
//            byte[] d = new byte[size];
//            int i =0;
//            while(inputStream.read()!=-1){
//                inputStream.read(d,0,size);
//                if(i<10){
//                	System.out.println("i<10");
//                	System.out.println("收到消息"+d.toString());
//                    i++;
//                }
//            }
		  
		    
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
		new Client3().work();// 客户端运行

	}

}
