package com.rankup.system;

import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);

    public void abrirMenu() {

        System.out.println("---------------------------------------");
        System.out.println("RankUp!\n");
        System.out.println("1- Login");
        System.out.println("2- Cadastro");
        System.out.println("3- Sair");
        System.out.println("Digite sua escolha: ");
        int option = scanner.nextInt();
        System.out.println("---------------------------------------");

        switch(option) {

            case 1:
                break;

            case 2:
                break;

            case 3:
                break;

            default:
                while(option > 3 && option < 1) {
                    System.out.println("Opção inválida, digite novamente: ");
                    option = scanner.nextInt();
                }
                break;
        }

    }

}
