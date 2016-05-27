package ua.dp.daragan;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {
        try (ServerSocket serv = Server.getServerSocket()) {
            Listener listener = Listener.getListener();
            
            while (true){ 
                
                try{
                    new Client(listener, serv.accept() );
                } catch (IOException e){
                    System.err.println("Error in main: " + e); 
                } 
            }           
        } catch (IOException ex) {
            System.err.println("Error in main: " + ex);
        }
    }   
}