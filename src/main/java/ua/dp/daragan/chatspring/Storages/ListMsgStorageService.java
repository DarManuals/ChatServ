package ua.dp.daragan.chatspring.Storages;

import java.util.Collection;
import java.util.LinkedList;

public class ListMsgStorageService implements MsgStorageService{
    
    private static Collection<String> allMsgs = new LinkedList<>();
    private static int countOfMsgs = 0;

    @Override
    public void pushNewMsg(String msg) {
        if(countOfMsgs <10){ //save only last 10 msgs
            allMsgs.add(msg);
            countOfMsgs++;
        }else{
            allMsgs.remove(0);
            allMsgs.add(msg);
        }
    }

    @Override
    public Collection<String> getMsgs() {
        return allMsgs;
    }    
}