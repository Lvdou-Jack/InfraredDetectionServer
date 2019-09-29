package demo1;

/**
 * 为了弄明白在Thread子类中到底是构造函数先执行还是run函数*/
public class Demo1 extends Thread{
	
	public Demo1() {
		System.out.println("构造函数执行了");
	}
	
	public void run() {
		System.out.println("run函数执行了");
	}

	public static void main(String[] args) {
		Demo1 d1 = new Demo1();
		d1.start();

	}

}
