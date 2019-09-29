package demo4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Demo4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = "test";
		String app_keep_path = "F:\\test\\"+test+".txt";
		
		double t1 = 25.0;
		double[] d1 = new double[768];
		for(int i=0; i < 768;i++) {
			d1[i] = 1.0;
		}
		ImageMesBean b1 = new ImageMesBean(t1,d1) ;
		
		try {
			FileOutputStream app_out = new FileOutputStream(app_keep_path);
			ObjectOutputStream app_oos = new ObjectOutputStream(app_out);
			app_oos.writeObject(b1);
			
			app_out.close();
			app_oos.close();
			
			FileInputStream app_in = new FileInputStream(app_keep_path);
			@SuppressWarnings("resource")
			ObjectInputStream app_ois = new ObjectInputStream (app_in);
			ImageMesBean b2 = (ImageMesBean) app_ois.readObject();
			System.out.println(b2);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
