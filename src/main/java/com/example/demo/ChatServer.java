package com.example.demo;

import java.net.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the chat client program.
 * Type 'bye' to terminte the program.
 *
 * @author www.codejava.net
 */
public class ChatServer{
    private int port;
    private Set<String> userNames = new HashSet<>();
    private ServerSocket serverSocket;

    //private Set<UserThread> userThreads = new HashSet<>();

    public ChatServer(ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
    }
    public void closeHand(){
        try {
            if(serverSocket!=null){
                serverSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start()  {
        try {
            System.out.println("Chat Server is listening on port " + serverSocket.getLocalPort());

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");
                ClientHand clientHand = new ClientHand(socket);
                Thread thread = new Thread(clientHand);
                thread.start();

        }

    }
        catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }

}
public static void main(String[] args) throws IOException {
    //int port = Integer.parseInt(args[0]);

    ServerSocket serverSocket1= new ServerSocket(9000);
    ChatServer  server = new ChatServer(serverSocket1);

    server.start();
}

}
