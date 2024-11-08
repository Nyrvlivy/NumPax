//package br.com.numpax.API.V1.controllers;
//
//import br.com.numpax.API.V1.dto.request.UserLoginRequestDTO;
//import br.com.numpax.API.V1.dto.request.UserUpdateRequestDTO;
//import br.com.numpax.API.V1.dto.response.DetailedUserResponseDTO;
//import br.com.numpax.API.V1.dto.response.UserResponseDTO;
//import br.com.numpax.application.services.UserService;
//import br.com.numpax.application.services.impl.UserServiceImpl;
//import br.com.numpax.infrastructure.config.auth.JwtUtil;
//import br.com.numpax.infrastructure.config.database.ConnectionManager;
//import br.com.numpax.infrastructure.entities.User;
//import br.com.numpax.infrastructure.repositories.impl.UserRepositoryImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@WebServlet("/api/v1/users/*")
//public class UserController extends HttpServlet {
//
//    private UserService userService;
//    private ObjectMapper objectMapper;
//
//    @Override
//    public void init() {
//        ConnectionManager connectionManager = ConnectionManager.getInstance();
//        UserRepositoryImpl userRepository = new UserRepositoryImpl(connectionManager.getConnection());
//        userService = new UserServiceImpl(userRepository, null); // Passe outros repositórios conforme necessário
//        objectMapper = new ObjectMapper();
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Rota: /api/v1/users/register
//        String pathInfo = request.getPathInfo();
//        if (pathInfo.equals("/register")) {
//            // Código existente para registro
//        } else if (pathInfo.equals("/login")) {
//            UserLoginRequestDTO loginRequestDTO = objectMapper.readValue(request.getReader(), UserLoginRequestDTO.class);
//            User user = userService.authenticateUser(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
//            if (user != null) {
//                String token = JwtUtil.generateToken(user.getUserId());
//                response.setContentType("application/json");
//                response.getWriter().write("{\"token\":\"" + token + "\"}");
//            } else {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("{\"message\":\"Credenciais inválidas\"}");
//            }
//        }
//    }
//
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        String pathInfo = request.getPathInfo();
//        if (pathInfo != null && pathInfo.length() > 1) {
//            // Obter usuário por ID
//            String userId = pathInfo.substring(1);
//            DetailedUserResponseDTO userResponseDTO = userService.getUserById(userId);
//            String jsonResponse = objectMapper.writeValueAsString(userResponseDTO);
//
//            response.setContentType("application/json");
//            response.getWriter().write(jsonResponse);
//        } else {
//            // Listar todos os usuários
//            var users = userService.listAllUsers();
//            String jsonResponse = objectMapper.writeValueAsString(users);
//
//            response.setContentType("application/json");
//            response.getWriter().write(jsonResponse);
//        }
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Atualizar usuário
//        String pathInfo = request.getPathInfo();
//        if (pathInfo != null && pathInfo.length() > 1) {
//            String userId = pathInfo.substring(1);
//            UserUpdateRequestDTO userUpdateRequestDTO = objectMapper.readValue(request.getReader(), UserUpdateRequestDTO.class);
//            UserResponseDTO userResponseDTO = userService.updateUser(userId, userUpdateRequestDTO);
//            String jsonResponse = objectMapper.writeValueAsString(userResponseDTO);
//
//            response.setContentType("application/json");
//            response.getWriter().write(jsonResponse);
//        }
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Deletar usuário
//        String pathInfo = request.getPathInfo();
//        if (pathInfo != null && pathInfo.length() > 1) {
//            String userId = pathInfo.substring(1);
//            userService.deleteUser(userId);
//            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//        }
//    }
//}
