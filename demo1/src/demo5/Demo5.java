package demo5;

import java.awt.Color;

public class Demo5 {

	//1.定义色阶变化范围fromColor, toColor
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		

	}
	
	public Color Compute(double value) {
		int r;
		//r
		if(value < 128)
			r = 0;
		else if(value <192)
			r =  (int) (255/64*(value-128));
		else
			r = 255;
		
		//g
		int g;
		if(value < 64)
			g = (int) (255/64*value);
		else if(value <192)
			g =  255;
		else
			g= (int) (-255/63*(value - 192)+255);
		
		//b
		int b;
		if(value < 64)
			b = 255;
		else if(value <128)
			b = (int) (-255/63*(value - 192)+255);
		else
			b= 0;
		
		Color newC = new Color(r, g, b);
		return newC;
	}


}
