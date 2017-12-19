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
		//��ʽдStore_Node�ڵ�
		
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
					  size , //ʣ�������
					  file.listFiles().length,
					String.valueOf(properties.get("NodeName")),Integer.parseInt(properties.getProperty("NodeBord")),true);
			//�������ע����Ϣ
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
	//ͳ��Ŀ¼��С�ķ���  
	  public  static void getDirSize(File file) {  
	    if(file.isFile()) {  
	        //������ļ�����ȡ�ļ���С�ۼ�  
	    	length += file.length();  
	    }else if(file.isDirectory()) {  
	        //��ȡĿ¼�е��ļ�����Ŀ¼��Ϣ  
	        File[] f1 = file.listFiles();
	        for(int i = 0; i < f1.length; i++) {  
	            //���õݹ����f1�����е�ÿһ������  
	            getDirSize(f1[i]);  
	        }  
	    }  
	}
}
