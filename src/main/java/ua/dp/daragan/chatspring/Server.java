package ua.dp.daragan.chatspring;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static volatile ServerSocket servSock = null;
    private static final int SEVRER_PORT = 8080;
    
    private Server() {}
    
    public static ServerSocket getServerSocket() throws IOException{
        if(servSock == null) {
            servSock = new ServerSocket(SEVRER_PORT);
            System.out.println("Server started on port " + SEVRER_PORT + "!");           
        }
        return servSock;
    }  
}