package com.example.chatapplicationclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplicationClient
        extends Application
{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatApplicationClient.class.getResource("chatapp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {

        launch(args);
    }

}
