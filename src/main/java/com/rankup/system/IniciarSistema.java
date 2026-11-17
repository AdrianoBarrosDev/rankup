package com.rankup.system;

import com.rankup.dto.*;
import com.rankup.router.Router;
import com.rankup.sql.Banco;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class IniciarSistema {

    public void start() throws IOException {

        Banco.inicializar();

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080),
                0
        );

        Router router = new Router();
        router.register(server);

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor rodando em http://localhost:8080");

    }

}
