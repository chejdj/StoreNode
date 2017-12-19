package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import entity.StoreNode;

public class node_client {
	private StoreNode node;
             public node_client(StoreNode node){
            	 this.node=node;
             }
            public void start(){
            		new Thread(){
            			public void run(){
            				try {
            				ServerSocket serverSocket =new ServerSocket(node.getPort());
        					while(true){
        					  Socket socket;
								socket = serverSocket.accept();
								new deal_client(socket, node).start();
							                }
        					} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
            		}}.start();	
              }
}
