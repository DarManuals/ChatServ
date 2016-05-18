package ua.dp.daragan;

import java.net.ServerSocket;

public interface ClientsListner {
    public void addClient(Clients cl);
    public void delClient(Clients cl);
    public void addMsg(String s);
    public void sendToAll();
    public ServerSocket getServSock ();
    public void shutdown();
}