package client;

import java.io.IOException;
import java.net.Socket;
public class ClientPicture {
	int port = 1234;
	String server_ip = "47.103.21.160";
	Socket soc ;
	
	public void sendPicture() {
		try {
			soc = new Socket(server_ip, port);
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
