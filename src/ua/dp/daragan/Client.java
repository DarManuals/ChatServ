package ua.dp.daragan;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class Client extends Thread implements Clients{
    private static int clientCount = 0;
    private int clientID = -1;
    private ClientsListner server;
    private Scanner sc;
    private PrintWriter pr;
    private Socket sock;

    public Client(ClientsListner cl, Socket s) throws IOException {
        server = cl;
        sock = s;
        server.addClient(this);
        clientCount++;
        clientID = clientCount;
        pr = new PrintWriter(sock.getOutputStream() );
        start();
    }

    @Override
    public synchronized void updateMsgs(LinkedList<String> allMsg) {
        pr.println("---BEGIN---"); //for test
        for(String s : allMsg) pr.println(s);
        pr.println("---END---");//for test
        pr.print("Your msg: ");
        pr.flush();
    }
    
    public synchronized void shutdown(){
        try{
            pr.close();
            if( !sock.isClosed() ) sock.close();
            this.interrupt();
        }catch(Exception e){
            System.err.println(e + " - " + this.getClass().getName() );
        }
    }
    
    public void run(){
        boolean stop = false;
        outer : while(!stop){
            try{
                sc = new Scanner(sock.getInputStream() );
                System.out.println("Connected ID: " + clientID);
                while(sc.hasNextLine()){
                    String str = sc.nextLine();
                    if(str.equalsIgnoreCase("shutdown") ){//test
                        server.shutdown();
                        stop = true;
                        break outer;
                    }
                    if(str.equalsIgnoreCase("exit") ){
                        stop = true;
                        break outer;
                    }
                    System.err.println(str);
                    server.addMsg("ID" + clientID + ":" + str);                   
                }
                sc.close();
                
            }catch(Exception e){
                //System.err.println("Fix this in run method of Clien.java");
            }
        }
        try {
            pr.close();
            if( !sock.isClosed() ) sock.close();
            server.delClient(this);
            this.interrupt();            
            System.err.println( "ID: " + clientID + " Disconnected");
        } catch (Exception ex) {
            System.err.println(ex + " - " + this.getClass().getName() );
        }       
    }  

    @Override
    public int getID() {
        return this.clientID;
    }
}