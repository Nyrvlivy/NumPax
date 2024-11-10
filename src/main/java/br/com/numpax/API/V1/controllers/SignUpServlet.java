package br.com.numpax.API.V1.controllers;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String birthdateStr = request.getParameter("birthday_date");

        LocalDate birthdate = birthdateStr != null && !birthdateStr.isEmpty() ? LocalDate.parse(birthdateStr) : null;

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName(name);
        userRequestDTO.setEmail(email);
        userRequestDTO.setPassword(password);
        userRequestDTO.setBirthdate(birthdate);

        try {
            UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
            request.getSession().setAttribute("user", userResponseDTO);
            response.sendRedirect(request.getContextPath() + "/signin");
        } catch (Exception e) {
            request.setAttribute("error", "Não foi possível realizar o cadastro. Por favor, tente novamente.");
            request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
        }
    }
}
