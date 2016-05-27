package ua.dp.daragan;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client extends Thread implements Clients{
    private static int clientCount = 0;
    private final int clientID;
    private final Listener clListner;
    private final Scanner sc;
    private final PrintWriter pr;
    private final Socket sock;

    public Client(Listener cl, Socket s) throws IOException {
        clListner = cl;
        sock = s;
        clListner.addClient(this);
        clientCount++;
        clientID = clientCount;
        sc = new Scanner(sock.getInputStream() );
        pr = new PrintWriter(sock.getOutputStream() );
        start();
    }

    @Override
    public synchronized void updateMsgs(List<String> allMsg) {
        pr.println("---BEGIN---");
        for(String s : allMsg) pr.println(s);
        pr.println("---END---");
        pr.print("Your msg: ");
        pr.flush();
    }
    
    @Override
    public void run(){
        
        if( !sock.isClosed() ){
            
            try {
                System.out.println("Connected ID: " + clientID);
                while(sc.hasNextLine() ){
                    String str = sc.nextLine();
                    if(str.equalsIgnoreCase("exit") ){
                        break;
                    }else{}
                    if(str.equalsIgnoreCase("shutdown") ){
                        clListner.shutdown();
                        break;
                    }else{}
                    System.err.println(str);
                    clListner.addMsg("ID" + clientID + ":" + str);                   
                }

                clListner.delClient(this);
                if (sc != null) sc.close();
                if (pr != null) pr.close();
                if ( !sock.isClosed() ) sock.close();            
                System.out.println("ID: " + clientID + " - disconnected");

            }catch(Exception e){
                System.err.println("Error in Run method of Clien.java -> " + e);
            }finally{
                if (sc != null) sc.close();
                if (pr != null) pr.close();
                if ( !sock.isClosed() ) try {
                    sock.close();
                } catch (IOException ex) {
                    System.err.println(ex);
                } 
            }    
        }
    }
    
    @Override
    public int getID() {
        return this.clientID;
    }
}