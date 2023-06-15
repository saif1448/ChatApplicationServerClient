package com.example.chatapplicationclient;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (IOException e){
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
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

    public void sendMsgToServer(String msgTxtToSend) {
        try {
            bufferedWriter.write(msgTxtToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMsgFromServer(VBox vboxMsg) {
        new Thread(()->{
            while (socket.isConnected()){
                try {
                    String msgFromClient  = bufferedReader.readLine();
                    ClientController.addLabel(msgFromClient, vboxMsg);
                } catch (IOException e) {
                    e.printStackTrace();
                    closeEverything(socket,bufferedReader,bufferedWriter);
                    break;
                }
            }
        }).start();
    }
}
