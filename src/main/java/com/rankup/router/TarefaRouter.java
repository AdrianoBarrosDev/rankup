package com.rankup.router;

import com.rankup.controller.TarefaController;
import com.rankup.dto.TarefaDto;
import com.rankup.util.JsonMapper;
import com.sun.net.httpserver.HttpServer;

import java.util.List;

public class TarefaRouter extends BaseRouter {

    private final TarefaController tarefaController = new TarefaController();

    public void register(HttpServer server) {

        server.createContext("/tarefas/criarTarefa", exchange -> {
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
                TarefaDto dto = JsonMapper.map(
                        body,
                        TarefaDto.class
                );

                TarefaController tarefaController = new TarefaController();
                String response = tarefaController.criarTarefa(dto);
                sendResponse(exchange, 200, response);

            } catch (Exception e) {
                sendResponse(exchange, 400, "Erro ao processar JSON: " + e.getMessage());
            }

        });

        server.createContext("/tarefas", exchange -> {
            String method = exchange.getRequestMethod();

            if(!"GET".equalsIgnoreCase(method)) {
                sendResponse(exchange, 405, "Method Not Allowed");
                return;
            }

            try {

                TarefaController tarefaController = new TarefaController();
                List<TarefaDto> listaTarefas = tarefaController.listarTarefas();
                String response = JsonMapper.toJson(listaTarefas);
                sendResponse(exchange, 200, response);

            } catch (Exception e) {
                sendResponse(exchange, 400, "Erro ao processar JSON: " + e.getMessage());
            }

        });

    }

}
