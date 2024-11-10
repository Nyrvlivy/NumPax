package br.com.numpax.API.V1.controllers;

import br.com.numpax.API.V1.dto.response.ActiveTransactionDTO;
import br.com.numpax.application.services.TransactionService;
import br.com.numpax.application.services.impl.TransactionServiceImpl;
import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.infrastructure.repositories.*;

import br.com.numpax.infrastructure.repositories.impl.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/transactions")
public class TransactionsServlet extends HttpServlet {

    private TransactionService transactionService;

    @Override
    public void init() throws ServletException {
        // Inicialize os repositórios necessários
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        TransactionRepository transactionRepository = new TransactionRepositoryImpl(connectionManager.getConnection());
        CheckingAccountRepository checkingAccountRepository = new CheckingAccountRepositoryImpl(connectionManager.getConnection());
        SavingsAccountRepository savingsAccountRepository = new SavingsAccountRepositoryImpl(connectionManager.getConnection());
        GoalAccountRepository goalAccountRepository = new GoalAccountRepositoryImpl(connectionManager.getConnection());
        InvestmentAccountRepository investmentAccountRepository = new InvestmentAccountRepositoryImpl(connectionManager.getConnection());
        CategoryRepository categoryRepository = new CategoryRepositoryImpl(connectionManager.getConnection());

        // Inicialize o TransactionService
        this.transactionService = new TransactionServiceImpl(
            transactionRepository,
            checkingAccountRepository,
            savingsAccountRepository,
            goalAccountRepository,
            investmentAccountRepository,
            categoryRepository
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Recuperar o usuário da sessão
            User user = (User) request.getSession().getAttribute("user");

            if (user == null) {
                // Se o usuário não estiver logado, redirecionar para a página de login
                response.sendRedirect(request.getContextPath() + "/signin");
                return;
            }

            // Use o método getter correto para obter o ID do usuário
            String userId = user.getUserId();

            // Buscar as transações ativas do usuário
            List<ActiveTransactionDTO> listaTransacoes = transactionService.listActiveTransactionsByUserId(userId);

            // Definir a lista de transações como atributo da requisição
            request.setAttribute("listaTransacoes", listaTransacoes);

            // Encaminhar para a JSP de transações
            request.getRequestDispatcher("/WEB-INF/views/transactions.jsp").forward(request, response);
        } catch (Exception e) {
            // Logue o erro e redirecione para uma página de erro ou exiba uma mensagem
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocorreu um erro ao carregar as transações.");
        }
    }
}
