package com.rankup.router;

import com.rankup.controller.UsuarioController;
import com.rankup.dto.CadastroUsuarioDto;
import com.rankup.dto.UsuarioDto;
import com.rankup.util.JsonMapper;
import com.sun.net.httpserver.HttpServer;

import java.util.List;

public class UsuarioRouter extends BaseRouter {

    private final UsuarioController usuarioController = new UsuarioController();

    public void register(HttpServer server) {

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

    }

}
