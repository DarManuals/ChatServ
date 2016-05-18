package ua.dp.daragan;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;

public class MyChatServ implements ClientsListner{
    private static volatile MyChatServ mcs = null;
    private volatile ArrayList<Clients> clients;
    private volatile LinkedList<String> allMsg;
    private volatile int countOfMsgs = 0;
    private volatile ServerSocket servSock = null;
    

    private MyChatServ() {
        clients = new ArrayList<Clients>();
        allMsg = new LinkedList<String>();
        try{
            servSock = new ServerSocket(8080);
            System.out.println("Server started on port 8080!");
        } catch (IOException e){
            System.err.println("Server error: " + e);
        }
    }
    
    public static MyChatServ getInstance(){
        if(mcs == null) {
            mcs = new MyChatServ();
        }
        return mcs;
    }
    
    public void shutdown(){
        try{
            for( Clients c : clients){
                c.shutdown();
                System.out.println( "Client with ID=" + c.getID() + " - Closed");
            }
            if( !servSock.isClosed() ) servSock.close();
            System.exit(0);
        }catch(Exception e){
            System.err.println(e + " - " + this.getClass().getName() );
        }
    }
    
    @Override
    public synchronized ServerSocket getServSock (){
        return servSock;
    }

    @Override
    public synchronized void addClient(Clients cl) {
        clients.add(cl);
    }

    @Override
    public synchronized void delClient(Clients cl) {
        int i = clients.indexOf(cl);
        if(i >=0){
            clients.remove(i);
        }
    }

    @Override
    public synchronized void sendToAll() { //send to all connected clients
        for(int i = 0; i<clients.size(); i++){
            Clients client = clients.get(i);
            client.updateMsgs( allMsg );
        }
    }
    
    @Override
    public synchronized void addMsg(String s){ //add msg from client to stack
        if(countOfMsgs <10){ //save only last 10 msgs
            this.allMsg.add(s);
            countOfMsgs++;
            sendToAll();
        }else{
            this.allMsg.remove(0);
            this.allMsg.add(s);
            sendToAll();
        }        
    }  
}