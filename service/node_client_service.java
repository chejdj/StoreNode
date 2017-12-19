package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import entity.MyFile;
import entity.StoreNode;

public class node_client_service {
   private Socket socket;
   private StoreNode node;
   private MyFile f1;
     public node_client_service(Socket socket,StoreNode node,MyFile file){
    	   this.socket=socket;
    	   this.node=node; 
    	   this.f1=file;
     }
     public void Download(){
    	     try {
				 ObjectInputStream obj = new ObjectInputStream(socket.getInputStream());
					MyFile file = (MyFile)obj.readObject();
					File file1 = new File("E:/vovation_java/Test/node1/"+file.getID());
					System.out.println(file.getID());
					   if(file1.exists())
					   {
						  FileInputStream fis   = new FileInputStream(file1);
						  OutputStream outputStream=socket.getOutputStream();
						  byte[] data1 = new byte[1024];
						  while(fis.read(data1, 0, data1.length)>0)
						  {
							  System.out.println("д����");
							  outputStream.write(data1, 0,data1.length);
							  outputStream.flush();
						  }
						  if(fis!=null)fis.close();
						  if(outputStream!=null)outputStream.close();
						  if(socket!=null)socket.close();
						  System.out.println("�ļ�����ɹ���");
					}else{
						
						System.out.println("�ļ�������");
					}
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     }
    /* public void Remove()
     {
    	 ObjectInputStream obj;
		try {
			obj = new ObjectInputStream(socket.getInputStream());
				MyFile file = (MyFile)obj.readObject();
				File file1 = new File("E:/vovation_java/Test/node2"+file.getID());
				if(file1.exists())
				{
					file1.delete();
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }*/
      public void Upload()
      {
    	          //ֱ�ӽ���һ���ࣿ���� ���ļ���Ͷ���
    	          //  �ȷ���һ��MyFile�����ļ�ID������Ҳ��װ�˱��ݽڵ�
    	          //Ȼ��ÿ�η�100bytes
             InputStream inputStream;
			try {
				     inputStream = socket.getInputStream();
				     /*System.out.println("��ʼ���ܶ���");
			        ObjectInputStream obj =new ObjectInputStream(inputStream);
			        System.out.println("��ʼ���ܶ���1");
					MyFile f1 = (MyFile)obj.readObject();
					System.out.println("���ܶ���");*/
				     int sum =f1.getSize();
					File file = new File("E:/vovation_java/Test/node1/"+f1.getID());
					  if(!file.exists())
					  file.createNewFile();
					FileOutputStream out  = new FileOutputStream(file);
					byte[] data = new byte[1024];
					System.out.println("��ʼ��������");   //ÿ��д��1024bytes
					while(inputStream.read(data,0, data.length)>0){
						out.write(data, 0, data.length);
				//		sum=sum-data.length;
						out.flush();
					}
					System.out.println(sum);
				out.close();
				if(inputStream!=null)inputStream.close();
				if(socket!=null)socket.close();
				 node.setRem_volum(node.getRem_volum()-(double)sum/1024/1024);
				 System.out.println("�ڵ�ʣ����"+node.getRem_volum());
				//	if(sum!=0){
					//	file.delete();
						//System.out.println("�����ļ���û�д��꣬ɾ�����ļ���");
				//	}	
					//������������ļ��������������ڵ㴫��
				  if(!(f1.getB_node1().getName().equals(node.getName()))){
					  FileInputStream fis   = new FileInputStream(file);
					  Socket socket1 = new Socket(InetAddress.getByName(f1.getB_node1().getIp()), 
							  f1.getB_node1().getPort());
					  OutputStream outputStream1=socket1.getOutputStream();
					/*  String d_data = new String("Upload#123\n");
					  PrintWriter printWriter= new PrintWriter(outputStream1);
					   printWriter.write(d_data);
					  System.out.println("��ʼ�������ڵ㷢������");
					  printWriter.flush();*/
					  
					  ObjectOutputStream obj_out = new ObjectOutputStream(outputStream1);
					  System.out.println("��ʼ�������ڵ㷢��������");
					  MyFile f2 = new MyFile();
					  f2.setID(f1.getID());
					  f2.setSize(f1.getSize());
					  f2.setB_node1(f1.getB_node1());
					  f2.setCommand("Upload");
					  f2.setM_node(f1.getM_node());
					  obj_out.writeObject(f2);
					  obj_out.flush();
					  byte[] data1 = new byte[1024];
					  System.out.println("��ʼ�����ļ�");
					  while(fis.read(data1, 0, data1.length)>0)
					  {
						  outputStream1.write(data1, 0, data1.length);
						  outputStream1.flush();
					  }
					  System.out.println("�������ݳɹ�");
					if(obj_out!=null) obj_out.close();
					  if(fis!=null) fis.close();
					  if(outputStream1!=null)outputStream1.close();
					  if(socket1!=null)socket1.close();
				  }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      }
}
