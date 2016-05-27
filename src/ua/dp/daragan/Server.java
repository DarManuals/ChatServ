package ua.dp.daragan;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static volatile ServerSocket servSock = null;
    
    private Server() {}
    
    public static ServerSocket getServerSocket(){
        if(servSock == null) {
            try {
                servSock = new ServerSocket(8080);
                System.out.println("Server started on port 8080!");
            } catch (IOException ex) {
                System.err.println("Server error: " + ex);
            }           
        }
        return servSock;
    }  
}
