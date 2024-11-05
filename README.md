# computer-network

Writer Information

Name: Kim Jinha

Major: AI

Code: 202135751


Socket Homwork - Quiz game

Make a quiz game program using socket.

Goals
1. read server information from "server_info.dat" in client program
2. request question to server, get answer from user, and then request result to server.
3. server should save a score of current user, and send to client when all questions end.
4. use thread to allow multiple client.

Key Points (Server)
1. Server read qiz_ans.dat, and save each line contains question and answer separated by '/' (Q/A), using ArrayList.
2. It was parallelized by turning the socket over to the thread so that the server could receive multiple clients.
3. The command format was specified to indicate the client request in detail.

Key Points (Client)
1. Simple decoration to implement quiz games
2. The Client class focuses only on connecting to the server, and delegates communication with the server to the Client CommandHandler
3. Disconnect notification to server to prevent abnormal termination
