/*
 * ClientCommandHandler is a class that formats messages according to 
 * what client want to request to server.
 */

package client;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class ClientCommandHandler {
    private static BufferedReader keyboard; // user input
    private static BufferedReader clientReader; // read input from server
    private static PrintWriter clientWriter; // write output to server

    public ClientCommandHandler(BufferedReader key, BufferedReader reader, PrintWriter writer){
        keyboard = key;
        clientReader = reader;
        clientWriter = writer;
    }

    // request total score of current user
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

    // request question of the number
    public String getQuestion(int number){
        try{
            // setup request format
            String request_command = "request/question/"+ String.valueOf(number);

            // send request to server
            clientWriter.println(request_command);

            // receive question
            String question = clientReader.readLine();
            return question;  
        }
        catch(Exception e){
            return "error";
        }
          
    }
    public void getResult(int number, String answer){
        try {
            // result format(correct answer/true or false)
            int ANSWER = 0;
            int RESULT = 1;
            
            // send answer to server
            clientWriter.println("request/answer/"+String.valueOf(number)+"/"+answer);
            
            // receive result from server
            String[] result = clientReader.readLine().split("/");
            if(result[RESULT].equals("true")){
                System.out.printf("┌─────────────┐\n");
                System.out.printf("│  *correct*  │ (+10 points)\n"); // actual score increases in server
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

    // send disconnect request to server
    public void disconnect(){
        clientWriter.println("disconnect");
    }

    // request question and show up
    public void question(int number){
        // request question to server
        String question = getQuestion(number);
                
        // question format
        System.out.printf("[Question %d/10]\n%s\n",number+1,question);
    }

    // read user input, and request result for answer.
    public void answer(int number){
        // read answer from user
        try{
            System.out.printf("\nEnter answer : ");
            String answer = keyboard.readLine();
            // send to server, and get result
            getResult(number,answer);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
