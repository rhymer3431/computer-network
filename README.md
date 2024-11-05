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

### Protocol

#### Command Protocol
- Commands are separated by the ‘/’ character and formatted as `Type/Target/Number`.

**Command Types:**
1. **Request:** Used when a client requests something from the server.
2. **Message:** Just displays a message but is not used in this program (included for future reference).
3. **Disconnect:** Alerts the server to disconnect the client.

**Example:**
- To request question 5, send the string `request/question/4` to the server (since question 5 is stored in `QAList[4]`).
- For a simple message, send `message/hi` to the server, which will then display “hi”.
- The disconnect command sets the `ClientHandler`'s loop condition to false, ensuring safe disconnection.

#### Server_info Protocol
- A simple protocol to read information.
- **Data Format:** `DataName:Data`
- Use `:` to separate each data entry.

#### Question and Answer Protocol
- A simple protocol to read questions and answers.
- **Format:** `Question/Answer`
- Use `/` to separate each data entry.

#### Architecture Diagram
![Diagram](https://github.com/rhymer3431/computer-network/blob/main/%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90.png?raw=true)

## Writer Information
- **Name:** Kim Jinha
- **Major:** AI
- **Code:** 202135751
