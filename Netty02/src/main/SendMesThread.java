package main;

import java.io.File;

public class SendMesThread extends Thread{
	
	public void work() {
		String hello_path = "C:\\server\\test\\hello.txt";
		File f_hello = new File(hello_path);
		if(f_hello.exists()) {
			
		}
	}
	
	public void run() {
		work();
	}

}
