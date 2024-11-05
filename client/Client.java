package client;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    private static String server_ip;
    private static int port;

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
            
            System.out.printf("------------------\n");
            System.out.printf("ip: %s\n",server_ip);
            System.out.printf("port: %d\n",port);
            System.out.printf("------------------\n\n");
        }
        catch(Exception e){
            // default setting
            server_ip = "127.0.0.1";
            port = 1234;
            e.printStackTrace();
        }
    }

    public static int getScore(){
        try {
            clientWriter.println("request/score");
            String score_str = clientReader.readLine();
            int score = Integer.parseInt(score_str);
            return score;
        } catch (Exception e) {
            return -1;
        }
    }
    public static String getQuestion(int number){
        try{
            String request_command = "request/question/"+ String.valueOf(number);
            clientWriter.println(request_command);
            String question = clientReader.readLine();
            return question;  
        }
        catch(Exception e){
            return "error";
        }
          
    }
    public static void getResult(int number, String answer){
        try {
            int RESULT = 1;
            int ANSWER = 0;
            // send answer to server
            clientWriter.println("request/answer/"+String.valueOf(number)+"/"+answer);
            
            // receive result from server format like "result/answer"
            String[] result = clientReader.readLine().split("/");
            if(result[RESULT].equals("true")){
                System.out.printf("correct! (+10 points)\n");
            }
            else{
                System.out.printf("incorrect.\n");
                System.out.printf("Feedback: %s\n",result[ANSWER]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static void start() throws IOException{
        System.out.printf("Let's start a quiz!\n");
        

        // request quiz
        try {
            for (int i=0; i<10; i++){
                String question = getQuestion(i);
                
                System.out.printf("Quiz %d. %s\n",i+1,question);
                System.out.printf("Enter answer : ");
                String answer = keyboard.readLine();
                getResult(i,answer);

                
                System.out.println();
                
            }
            System.out.printf("your total score is %s!\n",getScore());

            clientWriter.println("disconnect");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
