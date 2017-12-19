package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import entity.StoreNode;
import thread.node_client;
import thread.node_service;

public class main_node {
	static int length=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		//正式写Store_Node节点
		
		Properties properties =new Properties();
		try {
			properties.load(new FileInputStream("node.properties"));
			File file = new File(properties.getProperty("RootFolder"));
			//double result=length/1024/1024;
			  getDirSize(file);
			double size=Double.parseDouble( (String) properties.get("Volume"))-((double)length/(1024*1024));
			   StoreNode node = new StoreNode(properties.getProperty("NodeIp"), 
					Integer.parseInt((String) properties.get("NodePort"))
					,Integer.parseInt((String) properties.get("Volume")),  //volume
					Integer.parseInt((String) properties.get("Volume")),   //rvolume
					  size , //剩余的数量
					  file.listFiles().length,
					String.valueOf(properties.get("NodeName")),Integer.parseInt(properties.getProperty("NodeBord")),true);
			//向服务器注册消息
			   System.out.println(size);
			   System.out.println("1");
			  new node_client(node).start();
			  System.out.println("2");
			   new node_service(node, properties.getProperty("FileServerIP"),
				Integer.parseInt(properties.getProperty("FileServerPort"))).start();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}  
	//统计目录大小的方法  
	  public  static void getDirSize(File file) {  
	    if(file.isFile()) {  
	        //如果是文件，获取文件大小累加  
	    	length += file.length();  
	    }else if(file.isDirectory()) {  
	        //获取目录中的文件及子目录信息  
	        File[] f1 = file.listFiles();
	        for(int i = 0; i < f1.length; i++) {  
	            //调用递归遍历f1数组中的每一个对象  
	            getDirSize(f1[i]);  
	        }  
	    }  
	}
}
