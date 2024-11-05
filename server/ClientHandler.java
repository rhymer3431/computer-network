package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{

    ArrayList<String[]> QAList; // list of question and answer

    Socket connection; // client socket that connected with server

    // about I/O
    BufferedReader serverReader;
    PrintWriter serverWriter;

    private int score; // total score

    boolean running; // used to exit loop

    // macro
    private static final int COMMAND_TYPE = 0;
    private static final int COMMAND_TARGET = 1;
    private static final int COMMAND_NUMBER = 2;

    private static final int QUESTION = 0;
    private static final int ANSWER = 1;

    public ClientHandler(Socket s, ArrayList<String[]> qalist){
        // initialize
        running = true;
        QAList = qalist;
        score = 0;
        connection = s;

        try {
            serverReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            serverWriter = new PrintWriter(connection.getOutputStream(),true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        System.out.printf("new client connected\n");

        String message = "";
        String[] command;
        while (running) { 
            try{
                message = serverReader.readLine();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            // read message from client, then split by paremeter '/'
            command = message.split("/");

            /*
             * commad type
             * 1. request/target/related_number -> request target[number]
             * 2. message/message to send (actually, not used) -> print message
             * 3. disconnect -> disconnect to client
             */
            
            switch (command[COMMAND_TYPE]){
                case "request":
                int related_number=0;
                try {
                    related_number = Integer.parseInt(command[COMMAND_NUMBER]);
                } catch (Exception e) {
                    // request score -> no need related number
                }
                    switch(command[COMMAND_TARGET]){
                        
                        case "question": 
                            serverWriter.println(QAList.get(related_number)[QUESTION]); 
                            break;
                        case "answer" : 
                            String answer = QAList.get(related_number)[ANSWER];
                            if(command[3].equals(answer)){ // compare user input and correct answer 
                                score += 10;
                                serverWriter.println(answer+"/true"); // send correct answer and result to client
                            }
                            else serverWriter.println(answer+"/false");
                            break;
                        case "score" :
                        serverWriter.println(String.valueOf(score)); // send score to client
                            
                    }
                    break;
                case "message": // not used
                    System.out.printf("%s\n",command[COMMAND_TARGET]);
                    break;
                case "disconnect": // make while condition false
                    System.out.printf("client disconnected\n");
                    running = false;
                default:
            }
          

        }
    }
}
