package server3;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import edu.whut.ruansong.demo_display3.bean.ImageMesBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler3 extends ChannelInboundHandlerAdapter{
	private double temp_average = 0;
	private double[] temp;//温度值
	private double[] temp_normalization;//归一化后的灰度值
	private ImageMesBean storage_bean;
	private String test = "test";
	private String micro_keep_path = "C:\\server\\image_micro\\"+test+".jpg";
	private String app_keep_path = "C:\\server\\image_app\\"+test+".txt";
	private String state = "Server_state";
	private String client = "From client";
	/**
	 * 构造方法*/
	public ServerHandler3() {
		System.out.println(state+"--进入构造函数");
	}
	 
	/**
	 * type       data
	 * 1byte      1542byte
	 * type代表请求类型,0是app请求,1是单片机传输数据*/
	@Override
	
	public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception {
		System.out.println(state+"--调用channelRead ");
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];//获取缓冲区可读的字节数创建数组
		buf.readBytes(req);//将缓冲区的字节数组复制到新的byte数组中去
//		System.out.println(client+"--请求的总长度是"+req.length);//调试语句
		
		/**
		 * 读一个字节,判断type*/
		int type = (req[0]&0xff);
		System.out.println(client+"---type:"+type);
//		int type = 1;//测试语句
		switch(type) {
		case 0:
			System.out.println(client+"---request from app");
			/**
			 * hello测试语句*/
			//Unpooled是用来创建缓冲区的工具类
//			ByteBuf reply = Unpooled.copiedBuffer("Hello client,I had got your req!".getBytes());
//			ctx.write(reply);
			
			/**
			 * 回复图片测试语句*/
			//往app传输MesBean数据对象,包含环境温度值,和图片归一化的灰度值数组
			// 回写数据给客户端
			/*1.测试语句*/
//			double t1 = 25.0;
//			double[] d1 = new double[768];
//			for(int i=0; i < 768;i++) {
//				d1[i] = 1.0;
//			}
//			ImageMesBean response = new ImageMesBean(t1,d1);
//			ctx.writeAndFlush(response);
			/**
			 * 2.实际语句*/
			//(1).读取app目录中的Bean对象
			FileInputStream app_in = new FileInputStream(app_keep_path);
			ObjectInputStream app_ois = new ObjectInputStream (app_in);
			ImageMesBean response = (ImageMesBean) app_ois.readObject();
			System.out.println(state+"--temp_max "+response.getTemp_max());
			System.out.println(state+"--temp_min "+response.getTemp_min());
			app_in.close();
			app_ois.close();
			//(2).传输
			ctx.writeAndFlush(response);
			break;
		case 225:
			System.out.println(client+"---request from micro");
//			/**
//			 * 将req数组中的data数据复制*/
			byte[] data_micro = new byte[req.length-1];
			for(int i = 1;i <req.length;i++) {
				data_micro[i-1] = req[i];
			}
			answerMICRO(data_micro);//响应单片机请求
			ctx.write("Success".getBytes());//返回Success
			break;
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)throws Exception{
//		System.out.println(state+"--channelReadComplete被执行");
		ctx.flush();//将消息发送队列中的消息写入到SocketChannel中发送给对方
		System.out.println("---------------------------------");
	} 
	
	/**
	 * 响应单片机请求*/
	public void answerMICRO(byte[] data) {
		System.out.println("server3--answerMICRO响应");
		/**
		 * 保存单片机数据到本地并处理*/
		
		//1.处理图像数据
		//把字节数据处理为温度值数组,归一化数组
		dealData(data);
		
		try {
			//存储对应ImageMesBean()对象到image_app目录
			FileOutputStream app_out = new FileOutputStream(app_keep_path);
			ObjectOutputStream app_oos = new ObjectOutputStream(app_out);
			double[] maxAndMin = new double[2];//先是max,后是min,最大值和最小值的下标
			maxAndMin = searchMaxAndMin(temp);
			storage_bean = new ImageMesBean(temp_average, temp_normalization,maxAndMin[0],maxAndMin[1]);
			app_oos.writeObject(storage_bean);
			
			app_out.close();
			app_oos.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//将归一化的数据变成灰度值
		for(int i =0; i  < temp_normalization.length; i++) {
			temp_normalization[i] = temp_normalization[i] * 255;
		}
		
		//创建图像对象
		int w = 32,h = 24;
		BufferedImage gray_image = new BufferedImage(w, h,
                BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster rOut = gray_image.getRaster();
		rOut.setPixels(0, 0, w, h, temp_normalization);
		
		//图像存到本地
		 try {
			 File micro_out = new File(micro_keep_path);
			//图片存储在image_micoro目录
			ImageIO.write(gray_image, "jpg", micro_out);
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	}
	
	public void dealData(byte[] data) {
		System.out.println("server3--dealData处理数据");
		/**
		 * 一个像素点的色温T由两个字节组成,一共768个像素点8*2  8*2*3   16*48;  8*2*2  8*3  32*24
		 * 数据=图像数据(1536 字节) +本底温度(2 字节)+探测器编号(4 字节)
		 * 实际温度temperature = (T - 2731) / 10.0;*/
		temp_normalization = new double[(data.length-6)/2];
		temp = new double[temp_normalization.length];
		try {
			int temp1,temp2;
			int i = 0,j = 0;
			for(; i < data.length-6; i = i+2,j++) {
				temp1 = data[i];
				if(temp1 < 0) {//转换的时候似乎把最高位当作符号位了,所以需要这么处理一下
					temp1 = -temp1+128;
				}
				temp2 = data[i+1];
				if(temp2 < 0) {
					temp2 = -temp2+128;
				}
				temp_normalization[j] = (temp1 << 8)+temp2;
				temp_normalization[j] = ((double)(temp_normalization[j]-2731))/10.0;//转换为实际温度
				temp_average = temp_normalization[j]+temp_average;
				temp[j] = temp_normalization[j];//实际温度值存进数组
				//归一化处理
				if(temp_normalization[j]<20) {
					temp_normalization[j] = 20.1;
				}
				if(temp_normalization[j]>40) {
					temp_normalization[j] = 39.9;
				}
				temp_normalization[j] = (temp_normalization[j] - 20)/20.0;
			}
	}catch(Exception e) {
		e.printStackTrace();
	}
//		//测试语句
//		for(int k =0; k < 10;k++) {
//			System.out.println(temp[k]*20.0 + 20);
//		}
		temp_average = temp_average/768.0;
	}
	
	/**
	 * 寻找一个数组中最大值和最小值的下标*/
	public double[] searchMaxAndMin(double[] data) {
		int length = data.length;
		int max = 0;//最大值的下标
		int min = 0;//最小值的下标
		double[] maxAndMin = new double[2];
		for(int i = 0; i < length; i++) {
			if(data[i] > data[max])
				max = i;
			if(data[i] < data[min])
				min = i;
		}
		maxAndMin[0] = temp[max];
		maxAndMin[1] = temp[min];
		return maxAndMin;
	}

}
