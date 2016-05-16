package ua.dp.daragan;

public class Client implements Clients{
    private ClientsListner server;
    private String msg;

    public Client(ClientsListner cl) {
        server = cl;
        server.addClient(this);
    }

    @Override
    public void updateMsgs(String str) {
        System.out.println(str);
    }
    
}
