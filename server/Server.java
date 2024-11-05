package server;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private static ServerSocket serverSocket;
    public static ArrayList <String[]> QAList;
    static final int port = 12345;

    public static void main(String[] args){
        QAList = getQuizAnswer();
        new Server();

    }

    public Server(){
        try {
            System.out.printf("trying to open server..\n");
            serverSocket = new ServerSocket(12345, 50, InetAddress.getByName("0.0.0.0"));
            
            System.out.printf("server opened\n");
            // server is always opened
            while(true){
                Socket newClient = serverSocket.accept();
                new ClientHandler(newClient,QAList).start();
            }

        } catch (IOException e) {
           e.printStackTrace(); 
        } 
    }

    public static ArrayList <String[]> getQuizAnswer(){
        ArrayList<String[]> QAList = new ArrayList<>();
        try(Scanner reader = new Scanner(new File("./server/quiz_ans.dat"))){
            while(reader.hasNextLine()){
                // read question information, format like Q/A
                String line = reader.nextLine();
                String[] format = line.split("/");

                //format[0]: question, format[1]: answer
                QAList.add(format);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return QAList;
    }
    
}
