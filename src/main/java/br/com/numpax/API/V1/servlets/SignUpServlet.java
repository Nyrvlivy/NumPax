package br.com.numpax.API.V1.servlets;

import br.com.numpax.API.V1.dto.request.UserRequestDTO;
import br.com.numpax.API.V1.dto.response.UserResponseDTO;
import br.com.numpax.application.services.UserService;
import br.com.numpax.application.services.impl.UserServiceImpl;
import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.repositories.CheckingAccountRepository;
import br.com.numpax.infrastructure.repositories.UserRepository;
import br.com.numpax.infrastructure.repositories.impl.CheckingAccountRepositoryImpl;
import br.com.numpax.infrastructure.repositories.impl.UserRepositoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        UserRepository userRepository = new UserRepositoryImpl(connectionManager.getConnection());
        CheckingAccountRepository checkingAccountRepository = new CheckingAccountRepositoryImpl(connectionManager.getConnection());
        this.userService = new UserServiceImpl(userRepository, checkingAccountRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UserRequestDTO userRequest = new UserRequestDTO();
            userRequest.setName(request.getParameter("name"));
            userRequest.setEmail(request.getParameter("email"));
            userRequest.setPassword(request.getParameter("password"));
            
            String birthdateStr = request.getParameter("birthdate");
            LocalDate birthdate = LocalDate.parse(birthdateStr, DateTimeFormatter.ISO_DATE);
            userRequest.setBirthdate(birthdate);

            UserResponseDTO userResponse = userService.createUser(userRequest);

            if (userResponse != null) {
                request.setAttribute("success", "Cadastro realizado com sucesso! Faça login para continuar.");
                response.sendRedirect(request.getContextPath() + "/signin");
            } else {
                request.setAttribute("error", "Erro ao realizar cadastro. Tente novamente.");
                request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
        }
    }
} 