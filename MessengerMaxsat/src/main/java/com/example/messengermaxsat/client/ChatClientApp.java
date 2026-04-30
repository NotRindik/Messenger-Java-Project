package com.example.messengermaxsat.client;

import com.example.messengermaxsat.model.TextMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatClientApp extends Application {
    private ObjectOutputStream out;
    private TextArea chatArea = new TextArea();
    private TextField inputField = new TextField();
    private String userName = "User_" + (int)(Math.random() * 100);

    @Override
    public void start(Stage stage) {
        TextInputDialog dialog = new TextInputDialog("User");
        dialog.setTitle("Вход в Maxsat");
        dialog.setHeaderText("Выберите ваш никнейм");
        dialog.setContentText("Введите имя:");
        dialog.showAndWait().ifPresent(name -> this.userName = name);
        chatArea.setEditable(false);
        VBox root = new VBox(new Label("Вы вошли как: " + userName), chatArea, inputField);

        inputField.setOnAction(e -> sendMessage());

        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Maxsat");
        stage.show();

        connectToServer();
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8081);
                out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    Object obj = in.readObject();
                    if (obj instanceof TextMessage msg) {
                        Platform.runLater(() -> chatArea.appendText(msg.getSender() + ": " + msg.getContent() + "\n"));
                    }
                }
            } catch (Exception e) {
                Platform.runLater(() -> chatArea.appendText("Ошибка подключения к серверу\n"));
            }
        }).start();
    }

    private void sendMessage() {
        String text = inputField.getText();
        if (!text.isEmpty()) {
            try {
                out.writeObject(new TextMessage(userName, text));
                inputField.clear();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}