module com.example.chatapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.example.chatapplication to javafx.fxml;
    exports com.example.chatapplication;
    exports com.example.chatapplication.client;
    opens com.example.chatapplication.client to javafx.fxml;
}