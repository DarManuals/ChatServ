package ua.dp.daragan.chatspring;
//use: telnet 127.0.0.1 8080
//write "exit" to close current connection
//write "shutdown" to stop server

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("SpringXML.xml");

            while (true){ 
                Client cl = (Client) ctx.getBean("Client");
                cl.start();
            }           
    }   
}