package com.rankup.router;

import com.rankup.controller.GrupoController;
import com.rankup.dto.GrupoDto;
import com.rankup.dto.UsuarioDto;
import com.rankup.dto.UsuarioGrupoDto;
import com.rankup.util.JsonMapper;
import com.sun.net.httpserver.HttpServer;

import java.util.List;

public class GrupoRouter extends BaseRouter {

    private final GrupoController grupoController = new GrupoController();

    public void register(HttpServer server) {
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
    }

}
