package bean;

import java.io.Serializable;
import java.util.Arrays;
public class TestBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double tAverage;
	private double[] temp;
	public TestBean(double tAverage, double[] temp) {
		super();
		this.tAverage = tAverage;
		this.temp = temp;
	}
	public double gettAverage() {
		return tAverage;
	}
	public void settAverage(double tAverage) {
		this.tAverage = tAverage;
	}
	public double[] getTemp() {
		return temp;
	}
	public void setTemp(double[] temp) {
		this.temp = temp;
	}
	@Override
	public String toString() {
		return "TestBean [tAverage=" + tAverage + ", temp=" + Arrays.toString(temp) + "]";
	}
	
}
