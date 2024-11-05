package client;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class ClientCommandHandler {
    private static BufferedReader clientReader;
    private static PrintWriter clientWriter;

    public ClientCommandHandler(BufferedReader reader, PrintWriter writer){
        clientReader = reader;
        clientWriter = writer;
    }

    public int getScore(){
        try {
            clientWriter.println("request/score");
            String score_str = clientReader.readLine();
            int score = Integer.parseInt(score_str);
            return score;
        } catch (Exception e) {
            return -1;
        }
    }
    public String getQuestion(int number){
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
    public void getResult(int number, String answer){
        try {
            int RESULT = 1;
            int ANSWER = 0;
            // send answer to server
            clientWriter.println("request/answer/"+String.valueOf(number)+"/"+answer);
            
            // receive result from server format like "result/answer"
            String[] result = clientReader.readLine().split("/");
            if(result[RESULT].equals("true")){
                System.out.printf("┌─────────────┐\n");
                System.out.printf("│  *correct*  │ (+10 points)\n");
                System.out.printf("└─────────────┘\n");
            }
            else{
                System.out.printf("┌───────────────┐\n");
                System.out.printf("│  *incorrect*  │\n");
                System.out.printf("└───────────────┘\n");
                System.out.printf("Correct Answer: %s\n",result[ANSWER]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
