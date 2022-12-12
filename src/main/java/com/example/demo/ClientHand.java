package com.example.demo;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHand implements Runnable {
    public static ArrayList<ClientHand> clientHands =new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientusername;

    public ClientHand(Socket socket){
        try {
            this.socket =socket;
            this. bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientusername = bufferedReader.readLine();
            clientHands.add(this);
            broadcastmessage("server: "+clientusername+ " has entered the chat");

        } catch (Exception e) {
            closer(socket,bufferedReader,bufferedWriter);
        }
    }

    @Override
    public void run() {
    String messagefromc;
    while(socket.isConnected()){
        try {
            messagefromc = bufferedReader.readLine();
            broadcastmessage(messagefromc);
        } catch (IOException e) {
            closer(socket,bufferedReader,bufferedWriter);
            break;
        }
    }
    }
    public void broadcastmessage(String messageout){
        for (ClientHand clientHand:clientHands) {
            try {
                if(!clientHand.clientusername.equals(clientusername)){
                    clientHand.bufferedWriter.write(messageout);
                    clientHand.bufferedWriter.newLine();
                    clientHand.bufferedWriter.flush();
                }
            } catch (Exception e) {
                closer(socket,bufferedReader,bufferedWriter);
            }

        }
    }
    public void removeCleint(){
        clientHands.remove(this);
        broadcastmessage("server: "+clientusername+ " has left the chat");
    }
    public void closer(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeCleint();
        try {
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
