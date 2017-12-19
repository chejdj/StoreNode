package thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import entity.MyFile;
import entity.StoreNode;
import service.node_client_service;

public class deal_client {
    private Socket socket;
    private StoreNode node;
    private MyFile f;
    public deal_client(Socket socket,StoreNode node){
    	  this.socket=socket;
    	  this.node=node;
                }
       public void start(){
          try{
    	   InputStream  inputStream = socket.getInputStream();
       /*    int count = 0;  
           while (count == 0) {  
               count = inputStream.available();  
           }  
           byte[] data = new byte[count];*/
    	  ObjectInputStream objectInputStream=new ObjectInputStream(inputStream);
			f = (MyFile)objectInputStream.readObject();
			String[] command = f.getCommand().split("#");
		//	objectInputStream.close();
    	  // BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));
          //String da = bReader.readLine();
			//inputStream.read(data);
           //获取命令  以 #隔开 ,只有文件的上传和删除需要告知服务器！！
         //   String da = new String(data, 0, data.length);
          // System.out.println(da);
        //   String[] command =da.split("#");
          // System.out.println(command[0]);
           switch(command[0]){
           case "Upload" :     //文件名字，文件大小   
           	  new node_client_service(socket, node,f).Upload();
           	break;
           case "Download":  //文件名字 
              new node_client_service(socket, node,f).Download();
           break;
           default:
           	System.out.println(command);
           }
    		} catch (ClassNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}