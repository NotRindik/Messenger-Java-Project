package com.example.messengermaxsat.server;

import com.example.messengermaxsat.service.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class SocketServerRunner implements CommandLineRunner {
    @Autowired
    private DatabaseManager dbManager;

    @Override
    public void run(String... args) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(8081)) {
                System.out.println("Сервер запущен");
                while (true) {
                    Socket client = serverSocket.accept();
                    new Thread(new ClientHandler(client, dbManager)).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}