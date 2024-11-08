package br.com.numpax;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando testes do sistema financeiro...");

    }


    private static String generateRandomEmail() {
        String randomPart = UUID.randomUUID().toString().substring(0, 8);
        return "user_" + randomPart + "@example.com";
    }
}