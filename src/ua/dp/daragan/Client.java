package ua.dp.daragan;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Thread implements Clients{
    private static int clientCount = 0;
    private int clientID = -1;
    private ClientsListner server;
    private Scanner sc;
    private Socket sock;

    public Client(ClientsListner cl, Socket s) {
        server = cl;
        sock = s;
        server.addClient(this);
        clientCount++;
        clientID = clientCount;
        start();
    }

    @Override
    public void updateMsgs(LinkedList<String> allMsg) {
        for(String s : allMsg) {
            System.out.print(clientID + " - ");
            System.out.println(s);
        }
    }
    
    public void run(){
        outer : while(true){
            try{
                Scanner sc = new Scanner(sock.getInputStream() );
                System.err.println("Connected ID: " + clientID);
                while(sc.hasNextLine()){
                    String str = sc.nextLine();
                    if(str.equalsIgnoreCase("exit") ) break outer;
                    System.err.println(str);
                    server.addMsg(str);
                }
                
            }catch(Exception e){
                System.err.println(e);
            }
        }
        try {
            sock.close();
            this.interrupt();
            server.delClient(this);
            System.err.println( "ID: " + clientID + " Disconnected");
        } catch (Exception ex) {
            System.err.println(ex);
        }       
    }  
}