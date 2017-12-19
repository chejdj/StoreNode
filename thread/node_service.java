package thread;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import entity.StoreNode;

public class node_service {
           private StoreNode node;
           private String serverIP;
           private int serverPort;
           public node_service(StoreNode node,String serverIP,int serverPort){
        	       this.node=node;
        	       this.serverIP=serverIP;
        	       this.serverPort=serverPort;
           }
            public void start(){
            	    //通过UDP发送注册信息
            	try{
            	DatagramSocket socket = new DatagramSocket(6001);
    			byte[] by=new byte[516];
    			System.out.println("开始发送");
                ByteArrayOutputStream bs=new ByteArrayOutputStream();
                ObjectOutputStream bo=new ObjectOutputStream(bs);
    				bo.writeObject(node);
    				System.out.println("发送成功");
                   by=bs.toByteArray(); //改动
    		 DatagramPacket packet = new DatagramPacket(by, by.length,InetAddress.getByName(serverIP), serverPort);
    			  System.out.println(InetAddress.getLocalHost());
    			  System.out.println(node.getRem_volum());
    			  socket.send(packet);
    	    //监听来自service的端口
    			  new Thread(){    
    				  @Override
    				  public void run()
    				  {
    					  try {
    					  DatagramSocket datagramSocket = new DatagramSocket(node.getBord_port());
    					  while(true){
    						 byte[] data = new byte[100];
    						 DatagramPacket datagramPacket=new DatagramPacket(data, data.length);
							 datagramSocket.receive(datagramPacket);
						     String remove = new String(datagramPacket.getData(), 0,datagramPacket.getLength());
						     String[]  remove_information = remove.split("#");
						     //删除文件
						     System.out.println("接收到删除命令");
						     System.out.println(remove_information[1]);
						 //    System.out.println(remove_information[1]);
						      File file1 = new File("E:/vovation_java/Test/node1/"+remove_information[1]);
								if(file1.exists())
								{
									node.setRem_volum(node.getRem_volum()+(double)file1.length()/1024/1024);
									file1.delete();
								}	
    					  }} catch (SocketException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
    					  }catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
    					  
    					  
    				  }
    			  }.start();
    			  //定时发送注册消息
    			  Timer timer = new Timer();
    			  timer.schedule(new TimerTask() {
    				@Override
    				public void run() {
    					// TODO Auto-generated method stub
    					String name="#"+node.getName();
    				   byte[] by = name.getBytes();
    					DatagramPacket packet;
    					try {
    						System.out.println(node.getRem_volum());
    						packet = new DatagramPacket(by, by.length,InetAddress.getByName(serverIP), serverPort);
    						//System.out.println(packet.getData().toString());
    						socket.send(packet);
    					} catch (IOException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    				}
    				  
    			  },2000,6000);
    			  //接受 数据 存到文件夹里面去
            	
            }catch  (UnknownHostException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}	catch (SocketException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}  catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
            }
}
