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
            	    //ͨ��UDP����ע����Ϣ
            	try{
            	DatagramSocket socket = new DatagramSocket(6001);
    			byte[] by=new byte[516];
    			System.out.println("��ʼ����");
                ByteArrayOutputStream bs=new ByteArrayOutputStream();
                ObjectOutputStream bo=new ObjectOutputStream(bs);
    				bo.writeObject(node);
    				System.out.println("���ͳɹ�");
                   by=bs.toByteArray(); //�Ķ�
    		 DatagramPacket packet = new DatagramPacket(by, by.length,InetAddress.getByName(serverIP), serverPort);
    			  System.out.println(InetAddress.getLocalHost());
    			  System.out.println(node.getRem_volum());
    			  socket.send(packet);
    	    //��������service�Ķ˿�
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
						     //ɾ���ļ�
						     System.out.println("���յ�ɾ������");
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
    			  //��ʱ����ע����Ϣ
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
    			  //���� ���� �浽�ļ�������ȥ
            	
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
