package ua.dp.daragan;

import java.util.LinkedList;

public class Client implements Clients{
    private static int clientCount = 0;
    private int clientID = -1;
    private ClientsListner server;
    private String msg;

    public Client(ClientsListner cl) {
        server = cl;
        server.addClient(this);
        clientCount++;
        clientID = clientCount;
    }

    @Override
    public void updateMsgs(LinkedList<String> allMsg) {
        for(String s : allMsg) {
            System.out.print(clientID + " - ");
            System.out.println(s);
        }
    }

    @Override
    public void sendMsgToServ(String s) {
        server.addMsg(s);
    }
   
}
