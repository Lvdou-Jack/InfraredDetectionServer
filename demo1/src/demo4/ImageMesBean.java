package demo4;

import java.io.Serializable;
import java.util.Arrays;

public class ImageMesBean implements Serializable{
	//和app端的包名要一致
	
	private static final long serialVersionUID = 1L;
	/**
	 * 用来存储数据的JavaBean对象
	 */
	private double temp_average;//环境温度平均值
	private double[] grayValue;//图片归一化的灰度值数组
	public ImageMesBean(double temp_average, double[] grayValue) {
		super();
		this.temp_average = temp_average;
		this.grayValue = grayValue;
	}
	public double getTemp_average() {
		return temp_average;
	}
	public void setTemp_average(double temp_average) {
		this.temp_average = temp_average;
	}
	public double[] getGrayValue() {
		return grayValue;
	}
	public void setGrayValue(double[] grayValue) {
		this.grayValue = grayValue;
	}
	@Override
	public String toString() {
		return "ImageMesBean [temp_average=" + temp_average + ", grayValue=" + Arrays.toString(grayValue) + "]";
	}
	
	
	
}
