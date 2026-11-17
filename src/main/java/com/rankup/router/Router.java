package com.rankup.router;

import com.sun.net.httpserver.HttpServer;

public class Router {

    public UsuarioRouter usuarioRouter;
    public LoginRouter loginRouter;
    public GrupoRouter grupoRouter;
    public TarefaRouter tarefaRouter;

    public Router() {
        this.usuarioRouter = new UsuarioRouter();
        this.loginRouter = new LoginRouter();
        this.grupoRouter = new GrupoRouter();
        this.tarefaRouter = new TarefaRouter();
    }

    public void register(HttpServer server) {
        this.usuarioRouter.register(server);
        this.loginRouter.register(server);
        this.grupoRouter.register(server);
        this.tarefaRouter.register(server);
    }

}
