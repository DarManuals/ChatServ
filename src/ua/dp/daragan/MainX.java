package ua.dp.daragan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainX {
    public static void main(String[] args) {
    
        MyChatServ mcs = MyChatServ.getInstance();
        ServerSocket servSock = mcs.getServSock();
    
    
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
                    //mcs.sock.close();

                } catch (IOException e){
                    System.err.println(e.getStackTrace());
                } 
            }
            
        } catch (Exception e){
            System.err.println(e.getStackTrace());
        }
    }
}
