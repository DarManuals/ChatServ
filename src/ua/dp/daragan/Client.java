package ua.dp.daragan;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class Client extends Thread implements Clients{
    private static int clientCount = 0;
    private int clientID = -1;
    private ClientsListner server;
    private Scanner sc;
    private PrintWriter pr;
    private Socket sock;

    public Client(ClientsListner cl, Socket s) throws IOException {
        server = cl;
        sock = s;
        server.addClient(this);
        clientCount++;
        clientID = clientCount;
        pr = new PrintWriter(sock.getOutputStream() );
        start();
    }

    @Override
    public void updateMsgs(LinkedList<String> allMsg) {
        pr.println("---previous msgs---"); //for test
        for(String s : allMsg) {
            System.out.print(clientID + " - ");
            System.out.println(s);           
            pr.println(s);
            //pr.flush();
        }
        pr.println("---end of msgs---");//for test
        pr.print("Your msg: ");
        pr.flush();
    }
    
    public void run(){
        outer : while(true){
            try{
                Scanner sc = new Scanner(sock.getInputStream() );
                System.err.println("Connected ID: " + clientID);
                while(sc.hasNextLine()){
                    String str = sc.nextLine();
                    if(str.equalsIgnoreCase("exit") ) break outer;
                    System.err.println(str);
                    server.addMsg("ID" + clientID + ":" + str);
                }
                
            }catch(Exception e){
                System.err.println(e);
            }
        }
        try {
            pr.close();
            sock.close();
            this.interrupt();
            server.delClient(this);
            System.err.println( "ID: " + clientID + " Disconnected");
        } catch (Exception ex) {
            System.err.println(ex);
        }       
    }  
}