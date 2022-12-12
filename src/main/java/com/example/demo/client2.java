package com.example.demo;

import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

import static com.example.demo.HelloController.*;

public class client2 {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public void closer(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
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

    public client2(Socket socket, String username){
        try {
            this.socket =socket;
            this. bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username=username;

        } catch (IOException e) {
            closer(socket,bufferedReader,bufferedWriter);


        }
    }

    public void sendmessage(){
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner  scanner = new Scanner(se);
            while (socket.isConnected()){
                if(!Objects.equals(se, "")){

                bufferedWriter.write(username+": "+ se);
                bufferedWriter.newLine();
                bufferedWriter.flush();;}
                System.out.println("done");


            }



        } catch (Exception e) {
            closer(socket,bufferedReader,bufferedWriter);
        }


    }
    public static Text thisone2;
    public void  listen(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgfrom;
                while (socket.isConnected()){
                    try {
                        msgfrom =bufferedReader.readLine();
                        thisone2= new Text(msgfrom);

                        System.out.println(msgfrom);



                        System.out.println(msgfrom);
                    } catch (IOException e) {
                        closer(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }) .start();




    }
    public static void  main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("username");
        //String username= scanner.nextLine();
        String username="user1";
        Socket socket1 =new Socket("localhost",9000);
        client2 client =new client2(socket1,username);
        client.listen();
        client.sendmessage();
    }

}
