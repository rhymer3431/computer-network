# Computer Network



## Socket Homework - Quiz Game

### Project Overview
Create a quiz game program using sockets.

### Goals
1. Read server information from `server_info.dat` in the client directory.
2. Request questions from the server, receive answers from the user, and then request results from the server.
3. The server should save the score of the current user and send it to the client when all questions are completed.
4. Use threads to allow multiple clients to connect simultaneously.

### Key Points

#### Server
- Read `quiz_ans.dat`, saving each line containing a question and answer separated by `/` (Q/A) using an `ArrayList`.
- Parallelize by delegating socket connections to threads to handle multiple clients.
- Specify command format to detail client requests.

#### Client
- Implement simple decorations for the quiz game interface.
- The Client class focuses on connecting to the server, delegating communication to the `Client CommandHandler`.
- Send a disconnect notification to the server to prevent abnormal termination.

## Writer Information

- **Name:** Kim Jinha
- **Major:** AI
- **Code:** 202135751
