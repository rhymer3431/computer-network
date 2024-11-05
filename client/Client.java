package client;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    private static String server_ip;
    private static int port;
    private static ClientCommandHandler command;
    private static BufferedReader keyboard;
    private static BufferedReader clientReader;
    private static PrintWriter clientWriter;

    private static Socket client;


    public static void main(String[] args) throws IOException{
        getServerInfo();
        connect();
        start();
    

    }

    public static void connect(){
        try{
            client = new Socket(server_ip,port);

            keyboard = new BufferedReader(
                new InputStreamReader(System.in)
            );
            clientReader = new BufferedReader(
                new InputStreamReader(client.getInputStream())
            );
            clientWriter = new PrintWriter(client.getOutputStream(),true);

            command = new ClientCommandHandler(clientReader,clientWriter);
            System.out.printf("connected\n\n");
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void getServerInfo(){

        System.out.printf("get server information..\n");

        try(Scanner reader = new Scanner(new File("./client/server_info.dat"))){
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                String[] configs = line.split(":");

                switch(configs[0]){
                    case "server ip": server_ip = configs[1]; break;
                    case "port number": port = Integer.parseInt(configs[1]); break;
                }
                

            }
            
            System.out.printf("---------------------\n");
            System.out.printf("ip: %s\n",server_ip);
            System.out.printf("port: %d\n",port);
            System.out.printf("---------------------\n\n");
        }
        catch(Exception e){
            // default setting
            server_ip = "127.0.0.1";
            port = 1234;
            e.printStackTrace();
        }
    }
        
    
    public static void start() throws IOException{
        System.out.printf("\n*******************************\n");
        System.out.printf("*                             *\n");
        System.out.printf("*     Let's start a quiz!     *\n");
        System.out.printf("*                             *\n");
        System.out.printf("*******************************\n\n");

        // request quiz
        try {
            for (int i=0; i<10; i++){
                // request question to server
                String question = command.getQuestion(i);
                
                System.out.printf("[Quiz %d/10]\n%s\n",i+1,question);
                System.out.printf("\nEnter answer : ");
                String answer = keyboard.readLine();

                command.getResult(i,answer);
                
                System.out.println();
                System.out.println();
                
            }
            int score = command.getScore(); 
            System.out.printf("\nyour total score is %d!\n",score);

            clientWriter.println("disconnect");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
