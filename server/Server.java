package server;
import java.io.*;
import java.net.*;

public class Server {
    private static ServerSocket serverSocket;
    static final int port = 12345;
    public Server(){
        try {
            System.out.printf("trying to open server..\n");
            serverSocket = new ServerSocket(12345, 50, InetAddress.getByName("0.0.0.0"));
            
            System.out.printf("server opened\n");
            // server is always opened
            while(true){
                Socket newClient = serverSocket.accept();
                new ClientHandler(newClient).start();
            }

        } catch (IOException e) {
           e.printStackTrace(); 
        } 
    }

    public static void main(String[] args){
        new Server();

    }
    
   
    




}
    


