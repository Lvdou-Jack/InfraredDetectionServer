package demo2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

public class Demo2 {
	public byte [] w1(){
		
		byte [] data = null;
		FileImageInputStream input = null;
		ByteArrayOutputStream output = null;
		String path = "C:\\server\\test\\test.txt";

		try {
			input = new FileImageInputStream(new File(path));
			output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				       output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			System.out.println(data.length);
			
		}
		catch (Exception e) {}
		finally {
			try {
				input.close();
				output.close();
			}
			catch (IOException e) {}
		}
		
		return data;

	}
	
	public void w2(byte[] data,String path) {
		if(data.length<3||path.equals("")) return;
	    try{
	      FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
	      imageOutput.write(data, 0, data.length);
	      imageOutput.close();
	      System.out.println("Make Picture success,Please find image in " + path);
	    } catch(Exception ex) {
	      System.out.println("Exception: " + ex);
	      ex.printStackTrace();
	    }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Demo2 d2 = new Demo2();
		byte[] data = d2.w1();
		d2.w2(data, "C:\\server\\test\\test.png");
//		d2.w2(data, "F:" + File.separator +  "test"  + File.separator +  "test2" + File.separator + "2.png");

	}

}
