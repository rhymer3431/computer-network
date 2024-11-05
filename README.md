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

## Protocol
Command Protocol
Command is separated by ‘/’ character, and format like Type/Target/Number.
Command Type
1.	Request : request is used when a client requests something from a server
2.	Message : just show up message, but not used in this program. Add just in case.
3.	Disconnect : client alert server to disconnect

For example, if client want to request question 5, send string “request/question/4” to server. because question 5 is stored in QAList[4].
Message command is simple, send “message/hi” to server, then server will show up “hi”.
Disconnect command is set ClientHandler’s loop condition to false, so it makes disconnect safely.
request is used when a client requests something from a server.

Server_info Protocol
Simple protocol to read information.
Data name:Data
Separate by ‘:’ to allocate each data.

Question and answer Protocol
Simple protocol to read question and answer
Question/Answer
Separated by ‘/’ to allocate each data.

#### Architecture Diagram
![Diagram](https://github.com/rhymer3431/computer-network/blob/main/%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90.png?raw=true)

## Writer Information

- **Name:** Kim Jinha
- **Major:** AI
- **Code:** 202135751

