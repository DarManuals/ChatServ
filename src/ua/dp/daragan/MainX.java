package ua.dp.daragan;

import java.io.IOException;
import java.net.ServerSocket;

public class MainX {
    public static void main(String[] args) {
    
        MyChatServ mcs = MyChatServ.getInstance();
        ServerSocket servSock = mcs.getServSock();
    
        try {
            
            while (true){ 
                
                try{
                    new Client(mcs, servSock.accept() );
                } catch (IOException e){
                    System.err.println(e.getStackTrace());
                } 
            }
            
        } catch (Exception e){
            System.err.println(e.getStackTrace());
        }
    }
}
