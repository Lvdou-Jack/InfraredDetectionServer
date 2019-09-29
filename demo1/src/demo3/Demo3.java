package demo3;

public class Demo3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		byte b = (byte)0xE1;
		byte b2 = (byte)-31;
		int type = (int) b;
		int type2 = (int) b2;
		float f1  = 0;
		double d1 = 28.96;
		f1 = (float)d1;
		System.out.println("d1->f1 :"+(float)d1);
		
		byte[] bytes = new byte[4];
		bytes[0] = 0x00;
        bytes[1] = 0x00;
        bytes[2] = 0x00;
        bytes[3] = b;
        
//        type = ((bytes[0] & 0xff) << 24   | (bytes[1] & 0xff) << 16
//               | (bytes[2] & 0xff) << 8  | (bytes[3] & 0xff));
		
		System.out.println("b"+b);
		System.out.println("b2"+b2);
		System.out.println("type"+(type&0xff));
		System.out.println("type2"+b2);
	}

}
