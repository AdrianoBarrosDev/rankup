package com.rankup.system;

import com.rankup.controller.GrupoController;
import com.rankup.controller.LoginController;
import com.rankup.controller.TarefaController;
import com.rankup.controller.UsuarioController;
import com.rankup.dto.*;
import com.rankup.router.Router;
import com.rankup.sql.Banco;
import com.rankup.util.JsonMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

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
