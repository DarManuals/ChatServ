package ua.dp.daragan;

import java.util.LinkedList;

public interface Clients {
    public void updateMsgs(LinkedList<String> allMsg);
    public void shutdown();
    public int getID();
}