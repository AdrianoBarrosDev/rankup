package com.rankup.system;

import com.rankup.controller.GrupoController;
import com.rankup.controller.LoginController;
import com.rankup.controller.UsuarioController;
import com.rankup.dto.*;
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

        UsuarioController usuarioController = new UsuarioController();

        server.createContext("/", exchange -> {
            String response = "Olá, mundo!";

            exchange.getResponseHeaders()
                    .set("Content-Type", "text/plain; charset=UTF-8");

            exchange.sendResponseHeaders(200, response.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });

        server.createContext("/cadastro", exchange -> {
            String method = exchange.getRequestMethod();

            if(!"POST".equalsIgnoreCase(method)) {
                sendResponse(exchange, 405, "Method Not Allowed");
                return;
            }

            try {
                String body = new String(
                        exchange.getRequestBody().readAllBytes(),
                        java.nio.charset.StandardCharsets.UTF_8
                );

                // JSON -> DTO
                CadastroUsuarioDto dto = JsonMapper.map(
                    body,
                    CadastroUsuarioDto.class
                );

                String response = usuarioController.criarUsuario(dto);
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 400, "Erro ao processar JSON: " + e.getMessage());
            }

        });

        server.createContext("/usuarios", exchange -> {

            String method = exchange.getRequestMethod();

            if (!"GET".equalsIgnoreCase(method)) {
                sendResponse(exchange, 405, "Method Not Allowed");
                return;
            }

            try {

                List<UsuarioDto> listarUsuarios = usuarioController.listarUsuarios();

                String response = JsonMapper.toJson(listarUsuarios);

                exchange.getResponseHeaders()
                        .set("Content-Type", "application/json; charset=UTF-8");

                sendResponse(exchange, 200, response);

            } catch (Exception e) {

                sendResponse(exchange, 500, "Erro ao listar usuários");
            }

        });

        server.createContext("/login", exchange -> {

            String method = exchange.getRequestMethod();

            if (!"POST".equalsIgnoreCase(method)) {
                sendResponse(exchange, 405, "Method Not Allowed");
                return;
            }

            try {

                String body = new String(
                        exchange.getRequestBody().readAllBytes(),
                        java.nio.charset.StandardCharsets.UTF_8
                );

                // JSON -> DTO
                LoginDto dto = JsonMapper.map(
                        body,
                        LoginDto.class
                );

                LoginController loginController = new LoginController();
                String response = loginController.logar(dto.getUsername(), dto.getSenha());
                System.out.println("SAIU");

                exchange.getResponseHeaders()
                        .set("Content-Type", "application/json; charset=UTF-8");

                sendResponse(exchange, 200, response);

            } catch (Exception e) {

                sendResponse(exchange, 500, "Erro ao logar");
            }

        });

        server.createContext("/grupos/criarGrupo", exchange -> {
            String method = exchange.getRequestMethod();

            if(!"POST".equalsIgnoreCase(method)) {
                sendResponse(exchange, 405, "Method Not Allowed");
                return;
            }

            try {
                String body = new String(
                        exchange.getRequestBody().readAllBytes(),
                        java.nio.charset.StandardCharsets.UTF_8
                );

                // JSON -> DTO
                GrupoDto dto = JsonMapper.map(
                        body,
                        GrupoDto.class
                );

                GrupoController grupoController = new GrupoController();
                String response = grupoController.criarGrupo(dto);
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 400, "Erro ao processar JSON: " + e.getMessage());
            }

        });

        server.createContext("/grupos", exchange -> {
            String method = exchange.getRequestMethod();

            if(!"GET".equalsIgnoreCase(method)) {
                sendResponse(exchange, 405, "Method Not Allowed");
                return;
            }

            try {

                GrupoController grupoController = new GrupoController();
                List<GrupoDto> listaGrupos = grupoController.listarGrupos();
                String response = JsonMapper.toJson(listaGrupos);
                sendResponse(exchange, 200, response);

            } catch (Exception e) {
                sendResponse(exchange, 400, "Erro ao processar JSON: " + e.getMessage());
            }

        });

        server.createContext("/grupos/addUsuarioGrupo", exchange -> {
            String method = exchange.getRequestMethod();

            if(!"POST".equalsIgnoreCase(method)) {
                sendResponse(exchange, 405, "Method Not Allowed");
                return;
            }

            try {
                String body = new String(
                        exchange.getRequestBody().readAllBytes(),
                        java.nio.charset.StandardCharsets.UTF_8
                );

                // JSON -> DTO
                UsuarioGrupoDto dto = JsonMapper.map(
                        body,
                        UsuarioGrupoDto.class
                );

                GrupoController grupoController = new GrupoController();
                String response = grupoController.adicionarUsuarioAoGrupo(dto);
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 400, "Erro ao processar JSON: " + e.getMessage());
            }

        });

        server.createContext("/grupos/", exchange -> {
            String method = exchange.getRequestMethod();

            if(!"POST".equalsIgnoreCase(method)) {
                sendResponse(exchange, 405, "Method Not Allowed");
                return;
            }

            try {
                String body = new String(
                        exchange.getRequestBody().readAllBytes(),
                        java.nio.charset.StandardCharsets.UTF_8
                );

                String[] partes = exchange.getRequestURI().getPath().split("/");
                if (partes.length < 2) {
                    sendResponse(exchange, 400, "Código do grupo não informado");
                    return;
                }

                GrupoController grupoController = new GrupoController();
                List<UsuarioDto> listaUsuarios = grupoController.listarUsuariosGrupo(partes[1]);
                String response = JsonMapper.toJson(listaUsuarios);
                sendResponse(exchange, 200, response);

            } catch (Exception e) {
                sendResponse(exchange, 400, "Erro ao processar JSON: " + e.getMessage());
            }

        });

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor rodando em http://localhost:8080");

    }

    private static void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        byte[] bytes = response.getBytes("UTF-8");

        exchange.getResponseHeaders()
                .set("Content-Type", "text/plain; charset=UTF-8");

        exchange.sendResponseHeaders(status, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

}
