package br.com.numpax.API.V1.servlets;

import br.com.numpax.API.V1.dto.request.LoginRequestDTO;
import br.com.numpax.API.V1.dto.response.LoginResponseDTO;
import br.com.numpax.application.services.AuthService;
import br.com.numpax.application.services.impl.AuthServiceImpl;
import br.com.numpax.infrastructure.config.auth.JwtUtil;
import br.com.numpax.infrastructure.config.database.ConnectionManager;
import br.com.numpax.infrastructure.entities.User;
import br.com.numpax.infrastructure.repositories.UserRepository;
import br.com.numpax.infrastructure.repositories.impl.UserRepositoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {

    private AuthService authService;
    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        this.userRepository = new UserRepositoryImpl(connectionManager.getConnection());
        JwtUtil jwtUtil = new JwtUtil();
        this.authService = new AuthServiceImpl(userRepository, jwtUtil);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail(email);
        loginRequestDTO.setPassword(password);

        try {
            LoginResponseDTO loginResponseDTO = authService.login(loginRequestDTO);
            
            // Set token in session
            request.getSession().setAttribute("token", loginResponseDTO.getToken());

            // Fetch user and set in session
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                request.getSession().setAttribute("user", user);
            }

            response.sendRedirect(request.getContextPath() + "/WEB-INF/views/protected/home.jsp");
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
        }
    }
} 