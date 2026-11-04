package com.rankup.security;

import org.mindrot.jbcrypt.BCrypt;

public class AutenticacaoSenha {

    public static String criptografar(String valor) {
        return BCrypt.hashpw(valor, BCrypt.gensalt(12));
    }

    public static boolean verificar(String valor, String senhaCriptografada) {
        return BCrypt.checkpw(valor, senhaCriptografada);
    }

}
