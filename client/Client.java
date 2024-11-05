package client;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private static String server_ip;
    private static int port;
    private static ClientCommandHandler command;

    private static Socket client;

    public static void main(String[] args) throws IOException{
        // read server config information
        getServerInfo(); 
        
        // intialize socket, reader, and writer connected to server
        connect(); 

        // start quiz
        start();
    }

    public static void connect(){
        try{
            // initialize Socket
            client = new Socket(server_ip,port);

            // initialize reader and writer
            BufferedReader keyboard = new BufferedReader( // for input by user 
                new InputStreamReader(System.in)
            );
            BufferedReader clientReader = new BufferedReader(
                new InputStreamReader(client.getInputStream())
            );
            PrintWriter clientWriter = new PrintWriter(client.getOutputStream(),true);

            // ClientHandler: Class that defines various command to communicate server  
            command = new ClientCommandHandler(keyboard,clientReader,clientWriter);
            System.out.printf("connected\n\n");
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void getServerInfo(){

        System.out.printf("get server information..\n");

        // open info file, read line by line and separate according to parameters.
        try(Scanner reader = new Scanner(new File("./client/server_info.dat"))){
            while(reader.hasNextLine()){
                int NAME = 0;
                int CONTENT = 1;
                String line = reader.nextLine();
                String[] configs = line.split(":"); // format like name:content

                switch(configs[NAME]){ // allocate each data
                    case "server ip": server_ip = configs[CONTENT]; break;
                    case "port number": port = Integer.parseInt(configs[CONTENT]); break;
                }
                

            }
            
            // print received data
            System.out.printf("---------------------\n");
            System.out.printf("ip: %s\n",server_ip);
            System.out.printf("port: %d\n",port);
            System.out.printf("---------------------\n\n");
        }
        catch(Exception e){ // if server_info not founded,
            // default setting
            server_ip = "127.0.0.1";
            port = 1234;
        }
    }

    
    public static void start() throws IOException{
        System.out.printf("\n*******************************\n");
        System.out.printf("*                             *\n");
        System.out.printf("*     Let's start a quiz!     *\n");
        System.out.printf("*                             *\n");
        System.out.printf("*******************************\n\n");

        // a quiz of 10 questions
        try {
            for (int i=0; i<10; i++){
                command.question(i);
                command.answer(i);
                System.out.println();
                System.out.println();
                
            }

            // get total score from server
            int score = command.getScore(); 
            System.out.printf("\nyour total score is %d!\n",score);

            // alert disconnection to server
            command.disconnect();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
