package ua.dp.daragan.chatspring;

import java.util.Collection;

public interface Clients{
    public void updateMsgs(Collection<String> allMsg);
    public int getID();
}