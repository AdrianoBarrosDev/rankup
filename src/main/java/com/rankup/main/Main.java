package com.rankup.main;

import com.rankup.system.IniciarSistema;

import java.io.IOException;

public class Main {
    static void main() throws IOException {
        IniciarSistema iniciarSistema = new IniciarSistema();
        iniciarSistema.start();
    }
}
