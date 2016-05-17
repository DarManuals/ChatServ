package ua.dp.daragan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainX {
    public static void main(String[] args) {
    
        MyChatServ mcs = MyChatServ.getInstance();
        ServerSocket servSock = mcs.getServSock();
        
        Client cl = new Client(mcs);
        cl.sendMsgToServ("test1");
        Client cl2 = new Client(mcs);
        cl2.sendMsgToServ("test2");
        Client cl3 = new Client(mcs);
        cl3.sendMsgToServ("test3");
    
        try {
           
            //to do here
            one : while (true){
           
                try{
                    Socket sock = servSock.accept();
                    Scanner sc = new Scanner( sock.getInputStream() );

                    while(sc.hasNextLine()){
                        String str1 = sc.nextLine();
                        if(str1.equalsIgnoreCase("exit") ) break one;
                        System.out.println(str1);   
                    }
                    sock.close();

                } catch (IOException e){
                    System.err.println(e.getStackTrace());
                } 
            }
            
        } catch (Exception e){
            System.err.println(e.getStackTrace());
        }
    }
}
