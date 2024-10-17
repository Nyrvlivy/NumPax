//package br.com.numpax;
//
//import br.com.numpax.application.enums.CategoryType;
//import br.com.numpax.infrastructure.dao.impl.AccountDAOImpl;
//import br.com.numpax.infrastructure.dao.impl.TransactionDAOImpl;
//import br.com.numpax.infrastructure.dao.impl.UserDAOImpl;
//import br.com.numpax.infrastructure.dao.impl.CategoryDAOImpl;
//import br.com.numpax.infrastructure.entities.*;
//import br.com.numpax.application.enums.AccountType;
//import br.com.numpax.application.enums.NatureOfTransaction;
//import br.com.numpax.application.enums.RepeatableType;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//public class Mainveiaquenaofunciona {
//
//    public static void main(String[] args) {
//        System.out.println("Testing Numpax DAOs and Entities...");
//
//        // Instanciar DAOs
//        UserDAOImpl userDAO = new UserDAOImpl();
//        AccountDAOImpl accountDAO = new AccountDAOImpl();
//        CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
//        TransactionDAOImpl transactionDAO = new TransactionDAOImpl();
//
//        // Criando um usuário
//        // AQUI CRIA, MAS PRECISO DEIXAR O ID CRIADO SOZINHO E O ACTIVE, CREATED E UPDATED sao criados na hora no user classe
//        User user = new User("123e4567-e89b-12d3-a456-426614175000",
//            "Test User", "user_email1@example.com",
//            "hashed_password", LocalDate.of(1990, 5, 10),
//            true, LocalDate.now().atStartOfDay(),
//            LocalDate.now().atStartOfDay());
//        userDAO.save(user);
//
//        // Criando uma conta regular
//        // nao verifiquei ainda
//        RegularAccount regularAccount = new RegularAccount(
//            "Main Account", "Personal expenses", user, AccountType.CHECKING);
//        accountDAO.save(regularAccount);
//
//        // Criando uma categoria
//        // a falha acontece aqui, mas é pq está instaciado errado e precisa arrumar o construtos, a CategoryDAOImpl em geral
//        Category category = new Category(
//            "Food",
//            "Expenses on food",
//            "food_icon.png",
//            CategoryType.EXPENSE,
//            true
//        );
//        categoryDAO.save(category);
//
//
//        // Criando uma transação
//        Transaction transaction = new Transaction(
//            "TR-001", "Grocery Shopping", "Bought groceries",
//            BigDecimal.valueOf(100.50), category, regularAccount,
//            NatureOfTransaction.EXPENSE, "Supermarket", "Test User",
//            LocalDate.now(), RepeatableType.NONE, "Monthly grocery shopping");
//
//        transactionDAO.save(transaction);
//
//        // Teste de recuperação de dados
//        System.out.println("Fetching user by ID: " + userDAO.findById(user.getId()).orElse(null));
//        System.out.println("Fetching account by ID: " + accountDAO.findById(regularAccount.getId()).orElse(null));
//        System.out.println("Fetching category by ID: " + categoryDAO.findById(category.getId()).orElse(null));
//        System.out.println("Fetching transaction by ID: " + transactionDAO.findById(transaction.getId()).orElse(null));
//
//        System.out.println("Testing completed!");
//    }
//}
