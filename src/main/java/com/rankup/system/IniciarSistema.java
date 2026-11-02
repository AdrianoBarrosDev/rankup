package com.rankup.system;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class IniciarSistema {

    public void start() throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080),
                0
        );

        server.createContext("/", exchange -> {
            String response = "Olá, mundo!";

            exchange.getResponseHeaders()
                    .set("Content-Type", "text/plain; charset=UTF-8");

            exchange.sendResponseHeaders(200, response.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor rodando em http://localhost:8080");

    }

}
