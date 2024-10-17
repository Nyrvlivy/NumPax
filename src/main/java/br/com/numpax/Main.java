package br.com.numpax;

import br.com.numpax.API.V1.dto.DetailedUserDTO;
import br.com.numpax.API.V1.dto.UserDTO;
import br.com.numpax.application.services.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        System.out.println("Testando as funcionalidades_____");

        // Observação para o avaliador!!
        // Descomente apenas a função que deseja testar
        // testUserFunctions();
        System.out.println("\nTeste Completo!");
    }

//    private static void testUserFunctions() {
//        UserServiceImpl userService = new UserServiceImpl();
//
//        // 1. Criar o primeiro usuário
//        System.out.println("\n1. Criar um novo usuário_____");
//        UserDTO firstUserDTO = new UserDTO(
//            null, "User Test 1", generateRandomEmail(), LocalDate.of(1999, 11, 10)
//        );
//        String firstPassword = "p@ssw0rd123";
//        UserDTO firstCreatedUser = userService.createOrUpdateUser(firstUserDTO, firstPassword);
//        System.out.println("Usuário Test 1 criado: " + firstCreatedUser);
//
//        // 2. Criar um segundo usuário
//        System.out.println("\n2. Criar um segundo usuário_____");
//        UserDTO secondUserDTO = new UserDTO(
//            null, "User Test 2", generateRandomEmail(), LocalDate.of(2002, 2, 28)
//        );
//        String secondPassword = "p@ssw0rd123";
//        UserDTO secondCreatedUser = userService.createOrUpdateUser(secondUserDTO, secondPassword);
//        System.out.println("Usuário Test 2 criado: " + secondCreatedUser);
//
//        // 3. Atualizar o nome e a senha do segundo usuário
//        System.out.println("\n3. Atualizar o nome e a senha do segundo usuário_____");
//        String newSecondPassword = "newP@ssw0rd456";
//        UserDTO updatedSecondUserDTO = new UserDTO(
//            secondCreatedUser.getId(),
//            "New User Test 2",
//            secondCreatedUser.getEmail(),
//            secondCreatedUser.getBirthdate(),
//            newSecondPassword
//        );
//
//        UserDTO updatedUser = userService.createOrUpdateUser(updatedSecondUserDTO, newSecondPassword);
//        System.out.println("Usuário Test 2 atualizado: " + updatedUser);
//
//        // 4. Recuperar o segundo usuário por ID
//        System.out.println("\n4. Recuperar o segundo usuário atualizado por ID_____");
//        UserDTO simpleUser = userService.getSimpleUserById(secondCreatedUser.getId());
//        System.out.println("Usuário simples recuperado: " + simpleUser);
//
//        // 5. Recuperar detalhes do segundo usuário por ID
//        System.out.println("\n5. Detalhar o segundo usuário por ID_____");
//        DetailedUserDTO detailedUser = userService.getDetailedUserById(secondCreatedUser.getId());
//        System.out.println("Usuário detalhado recuperado: " + detailedUser);
//
//        // 6. Desabilitar o segundo usuário
//        System.out.println("\n6. Desabilitar o segundo usuário pelo ID_____");
//        userService.disableUserById(secondCreatedUser.getId());
//        UserDTO disabledUser = userService.getSimpleUserById(secondCreatedUser.getId());
//        System.out.println("Usuário após desabilitar: " + disabledUser);
//
//        // 7. Recuperar todos os usuários
//        System.out.println("\n7. Lista de todos os usuários:");
//        userService.getAllUsers().forEach(System.out::println);
//
//        // 8. Recuperar todos os usuários ativos
//        System.out.println("\n8. Lista de todos os usuários ativos:");
//        userService.getAllActiveUsers().forEach(System.out::println);
//
//        // 9. Recuperar todos os usuários inativos
//        System.out.println("\n9. Lista de todos os usuários inativos:");
//        userService.getAllInactiveUsers().forEach(System.out::println);
//    }


    private static String generateRandomEmail() {
        String randomPart = UUID.randomUUID().toString().substring(0, 8);
        return "user_test_" + randomPart + "@gmail.com";
    }
}
