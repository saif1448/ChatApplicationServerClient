package com.example.chatapplication.utils;

import com.example.chatapplication.AppController;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public Server(ServerSocket serverSocket) {

        try {
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (IOException e){
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }

    public void sendMsgToClient(String msgText) {
        try {
            bufferedWriter.write(msgText);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("msg sent to client");
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMsgFromClient(VBox vboxMsg) {
        new Thread(()->{
           while (socket.isConnected()){
               try {
                   String msgFromClient  = bufferedReader.readLine();
                   AppController.addLabel(msgFromClient, vboxMsg);
                   System.out.println("msg rcvd from client");
               } catch (IOException e) {
                   e.printStackTrace();
                   closeEverything(socket,bufferedReader,bufferedWriter);
                   break;
               }
           }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader != null) bufferedReader.close();
            if(bufferedWriter != null) bufferedWriter.close();
            if(socket != null) socket.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }



}
