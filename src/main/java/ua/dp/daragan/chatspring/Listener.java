package ua.dp.daragan.chatspring;

import ua.dp.daragan.chatspring.Storages.MsgStorageService;
import java.util.ArrayList;
import java.util.List;

public class Listener{
    private static volatile Listener listener = null;
    private volatile List<Clients> clients;
    private static volatile MsgStorageService msgStorage = null;
    
    private Listener() {
        clients = new ArrayList<>();
    }
    
    public static Listener getListener(){
        if(listener == null) {
            listener = new Listener();
        }
        return listener;
    }

    public static MsgStorageService getMsgStorage() {
        return msgStorage;
    }

    public static void setMsgStorage(MsgStorageService msgStorage) {
        Listener.msgStorage = msgStorage;
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
            client.updateMsgs( msgStorage.getMsgs() );
        }
    }
    
    public synchronized void addMsg(String s){ //add msg from client to stack
        msgStorage.pushNewMsg(s);        
        sendToAll();
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