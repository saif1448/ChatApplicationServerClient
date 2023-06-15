package com.example.chatapplicationclient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
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

    private Client client;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            client = new Client(new Socket("localhost",8003));
            System.out.println("Connect to server");
        } catch (IOException e) {
            e.printStackTrace();
        }

        vbox_msg.heightProperty().addListener((observable, oldValue, newValue)-> sp_main.setVvalue((Double) newValue));

        client.receiveMsgFromServer(vbox_msg);

        btn_send.setOnAction(event -> {
            String msgTxtToSend = text_field_send_msg.getText();
            if(!msgTxtToSend.isEmpty()){
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5,5,5,10));

                Text text = new Text(msgTxtToSend);
                text.setFill(Color.color(0.934, 0.945, 0.996));
                TextFlow textFlow = new TextFlow(text);
                textFlow.getStyleClass().add("text-flow");
                textFlow.setPadding(new Insets(5,10,5,10));

                hBox.getChildren().add(textFlow);
                vbox_msg.getChildren().add(hBox);

                client.sendMsgToServer(msgTxtToSend);

                text_field_send_msg.clear();
            }
        });

    }

    public static void addLabel(String msgFromServer, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(msgFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.getStyleClass().add("text-flow-silver");
        textFlow.setPadding(new Insets(5,10,5,10));

        hBox.getChildren().add(textFlow);

        Platform.runLater(()-> vBox.getChildren().add(hBox));


    }
}
