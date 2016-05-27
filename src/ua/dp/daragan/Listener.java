package ua.dp.daragan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Listener{
    private static volatile Listener listener = null;
    private volatile List<Clients> clients;
    private volatile List<String> allMsg;
    private volatile int countOfMsgs = 0;
    
    private Listener() {
        clients = new ArrayList<>();
        allMsg = new LinkedList<>();
    }
    
    public static Listener getListener(){
        if(listener == null) {
            listener = new Listener();
        }
        return listener;
    }
    
    public synchronized void addClient(Clients cl) {
        clients.add(cl);
    }

    public synchronized void delClient(Clients cl) {
        int i = clients.indexOf(cl);
        if(i >=0){
            clients.remove(i);
        }
    }

    public synchronized void sendToAll() { //send to all connected clients
        for(int i = 0; i<clients.size(); i++){
            Clients client = clients.get(i);
            client.updateMsgs( allMsg );
        }
    }
    
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

    public synchronized void shutdown() {
        for(Clients c : clients){
            ( (Thread) c).interrupt();
            System.out.println("ID: " + c.getID() + " - disconnected");
        }
        clients.removeAll(clients);
        System.exit(0);
    }
}