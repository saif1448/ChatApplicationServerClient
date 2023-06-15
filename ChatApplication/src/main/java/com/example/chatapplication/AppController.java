package com.example.chatapplication;

import com.example.chatapplication.utils.Server;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
//import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    AnchorPane root_pane;

    @FXML
    Button btn_send;

    @FXML
    TextField text_field_send_msg;

    @FXML
    ScrollPane sp_main;

    @FXML
    VBox vbox_msg;

    private Server server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("yo");
        try {
            System.out.println("asd");
            server = new Server(new ServerSocket(8003));
            System.out.println("server connected");
        }catch (IOException e){
            e.printStackTrace();
        }
        vbox_msg.heightProperty().addListener((observable, oldValue, newValue)-> sp_main.setVvalue((Double) newValue));

        server.receiveMsgFromClient(vbox_msg);

        btn_send.setOnAction(event -> {
            String msg_text = text_field_send_msg.getText();
            if(!msg_text.isEmpty()){
                System.out.println(msg_text);
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5,5,5,10));

                Text text = new Text(msg_text);
                text.setFill(Color.color(0.934, 0.945, 0.996));
                TextFlow textFlow = new TextFlow(text);
                textFlow.getStyleClass().add("text-flow");
                textFlow.setPadding(new Insets(5,10,5,10));

                hBox.getChildren().add(textFlow);
                vbox_msg.getChildren().add(hBox);

                server.sendMsgToClient(msg_text);

                text_field_send_msg.clear();
            }
        });

    }

    public static void addLabel(String msgFromClient, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(msgFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.getStyleClass().add("text-flow-silver");
        textFlow.setPadding(new Insets(5,10,5,10));

        hBox.getChildren().add(textFlow);

        Platform.runLater(()-> vBox.getChildren().add(hBox));




    }
}