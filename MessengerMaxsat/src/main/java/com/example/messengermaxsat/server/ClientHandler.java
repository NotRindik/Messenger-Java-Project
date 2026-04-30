package com.example.messengermaxsat.server;

import com.example.messengermaxsat.model.TextMessage;
import com.example.messengermaxsat.service.DatabaseManager;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private DatabaseManager dbManager;

    public ClientHandler(Socket socket, DatabaseManager dbManager) {
        this.socket = socket;
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            while (true) {
                Object obj = in.readObject();
                if (obj instanceof TextMessage) {
                    TextMessage msg = (TextMessage) obj;
                    dbManager.saveMessage(msg.getSender(), msg.getContent());
                }
            }
        } catch (Exception e) { System.out.println("Клиент отключился"); }
    }
}