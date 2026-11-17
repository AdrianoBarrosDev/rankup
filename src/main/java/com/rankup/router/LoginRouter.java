package com.rankup.router;

import com.rankup.controller.LoginController;
import com.rankup.dto.LoginDto;
import com.rankup.util.JsonMapper;
import com.sun.net.httpserver.HttpServer;

public class LoginRouter extends BaseRouter {

    private final LoginController loginController = new LoginController();

    public void register(HttpServer server) {

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

                exchange.getResponseHeaders()
                        .set("Content-Type", "application/json; charset=UTF-8");

                sendResponse(exchange, 200, response);

            } catch (Exception e) {

                sendResponse(exchange, 500, "Erro ao logar");
            }

        });

    }


}
