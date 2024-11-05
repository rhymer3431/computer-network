package server;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    ServerSocket serverSocket;
    static final int port = 12345;

    public Server(){
        try {
            System.out.printf("trying to open server..\n");
            serverSocket = new ServerSocket(12345, 50, InetAddress.getByName("0.0.0.0"));
            System.out.printf("server opened\n");
            while(true){
                Socket connection = serverSocket.accept();
                new ClientHandler(connection).start();
            }
        } catch (IOException e) {
           e.printStackTrace(); 
        } 
    }

    public static void main(String[] args){
        getQuizAnswer();
        new Server();

    }
    



    public static ArrayList <String[]> getQuizAnswer(){
        ArrayList<String[]> QAList = new ArrayList<>();
        try(Scanner reader = new Scanner(new File("./server/quiz_ans.dat"))){
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                String[] format = line.split("/");
                QAList.add(format);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return QAList;
    }

    public class ClientHandler extends Thread{
        ArrayList<String[]> QAList;
        Socket connection;
        BufferedReader serverReader;
        PrintWriter serverWriter;
        int score;

        boolean running;
        private static final int COMMAND_TYPE = 0;
        private static final int COMMAND_TARGET = 1;
        private static final int COMMAND_NUMBER = 2;

        private static final int QUESTION = 0;
        private static final int ANSWER = 1;

        public ClientHandler(Socket s){
            running = true;
            score = 0;
            connection = s;
            try {
                QAList = getQuizAnswer();
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
                command = message.split("/");
                
                switch (command[COMMAND_TYPE]){
                    case "request":
                    int number=0;
                    try {
                        number = Integer.parseInt(command[COMMAND_NUMBER]);
                    } catch (Exception e) {
                        // not request question or answer
                    }
                    
                        switch(command[COMMAND_TARGET]){
                            
                            case "question": 

                                serverWriter.println(QAList.get(number)[QUESTION]); 
                                break;
                            case "answer" : 
                                String answer = QAList.get(number)[ANSWER];
                                if(command[3].equals(answer)){
                                    score += 10;
                                    serverWriter.println(answer+"/true");
                                }
                                else serverWriter.println(answer+"/false");
                                break;
                            case "score" :
                            serverWriter.println(String.valueOf(score));
                                
                        }
                        break;
                    case "message":
                        System.out.printf("%s\n",command[COMMAND_TARGET]);
                        break;
                    case "disconnect":
                        System.out.printf("client disconnected\n");
                        running = false;
                    default:
                }
              

            }
        }
    }



}
    

