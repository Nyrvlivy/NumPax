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
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        TransactionRepository transactionRepository = new TransactionRepositoryImpl(connectionManager.getConnection());
        CheckingAccountRepository checkingAccountRepository = new CheckingAccountRepositoryImpl(connectionManager.getConnection());
        SavingsAccountRepository savingsAccountRepository = new SavingsAccountRepositoryImpl(connectionManager.getConnection());
        GoalAccountRepository goalAccountRepository = new GoalAccountRepositoryImpl(connectionManager.getConnection());
        InvestmentAccountRepository investmentAccountRepository = new InvestmentAccountRepositoryImpl(connectionManager.getConnection());
        CategoryRepository categoryRepository = new CategoryRepositoryImpl(connectionManager.getConnection());

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
            User user = (User) request.getSession().getAttribute("user");

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/signin");
                return;
            }

            String userId = user.getUserId();

            String nature = request.getParameter("nature");
            String pageStr = request.getParameter("page");
            String linhasPorPaginaStr = request.getParameter("linhasPorPagina");

            int paginaAtual = 1;
            if (pageStr != null && !pageStr.isEmpty()) {
                paginaAtual = Integer.parseInt(pageStr);
            }

            int linhasPorPagina = 10;
            if (linhasPorPaginaStr != null && !linhasPorPaginaStr.isEmpty()) {
                linhasPorPagina = Integer.parseInt(linhasPorPaginaStr);
            }

            List<ActiveTransactionDTO> listaTransacoes = transactionService.listActiveTransactionsByUserId(userId, nature);

            int totalTransacoes = listaTransacoes.size();
            int totalPaginas = (int) Math.ceil((double) totalTransacoes / linhasPorPagina);

            int fromIndex = (paginaAtual - 1) * linhasPorPagina;
            int toIndex = Math.min(fromIndex + linhasPorPagina, totalTransacoes);
            List<ActiveTransactionDTO> transacoesPaginadas = listaTransacoes.subList(fromIndex, toIndex);

            request.setAttribute("listaTransacoes", transacoesPaginadas);
            request.setAttribute("nature", nature);
            request.setAttribute("paginaAtual", paginaAtual);
            request.setAttribute("totalPaginas", totalPaginas);
            request.setAttribute("linhasPorPagina", linhasPorPagina);

            request.getRequestDispatcher("/WEB-INF/views/transactions.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocorreu um erro ao carregar as transações.");
        }
    }
}