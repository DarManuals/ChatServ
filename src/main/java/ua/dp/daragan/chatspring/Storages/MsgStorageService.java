package ua.dp.daragan.chatspring.Storages;

import java.util.Collection;

public interface MsgStorageService {
    void pushNewMsg(String msg);
    Collection<String> getMsgs();    
}