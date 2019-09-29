package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class Client2 {
	public void work() {
		new Thread(() -> {
            try {
                Socket socket = new Socket("47.103.21.160", 1);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        socket.getOutputStream().flush();
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
	}

    public static void main(String[] args) {
        new Client2();
    }
}
