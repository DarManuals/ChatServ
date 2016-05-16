package ua.dp.daragan;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;

public class MyChatServ implements ClientsListner{
    private ArrayList<Clients> clients;
    private LinkedList<String> allMsg;
    private int countOfMsgs = 0;
    private ServerSocket ss = null;

    public MyChatServ() {
        clients = new ArrayList();
    }
    

    public static void main(String[] args) {
        MyChatServ mcs = new MyChatServ();
        
        System.out.println("Server started");
        
        
        Client a,b;
        a = new Client(mcs);
        b = new Client(mcs);
        
        mcs.sendToAll();
    }

    @Override
    public void addClient(Clients cl) {
        clients.add(cl);
    }

    @Override
    public void delClient(Clients cl) {
        int i = clients.indexOf(cl);
        if(i >=0){
            clients.remove(i);
        }
    }

    @Override
    public void sendToAll() {
        for(int i = 0; i<clients.size(); i++){
            Clients client = clients.get(i);
            client.updateMsgs("test");//to do
        }
    }
    
}
