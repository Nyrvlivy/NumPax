//package br.com.numpax.API.V1.controllers;
//
//import br.com.numpax.API.V1.dto.request.CategoryRequestDTO;
//import br.com.numpax.API.V1.dto.response.CategoryResponseDTO;
//import br.com.numpax.application.services.CategoryService;
//import br.com.numpax.application.services.impl.CategoryServiceImpl;
//import br.com.numpax.infrastructure.config.database.ConnectionManager;
//import br.com.numpax.infrastructure.repositories.impl.CategoryRepositoryImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@WebServlet("/api/v1/categories/*")
//public class CategoryController extends HttpServlet {
//
//    private CategoryService categoryService;
//    private ObjectMapper objectMapper;
//
//    @Override
//    public void init() {
//        ConnectionManager connectionManager = ConnectionManager.getInstance();
//        CategoryRepositoryImpl categoryRepository = new CategoryRepositoryImpl(connectionManager.getConnection());
//        categoryService = new CategoryServiceImpl(categoryRepository);
//        objectMapper = new ObjectMapper();
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Criar categoria
//        CategoryRequestDTO categoryRequestDTO = objectMapper.readValue(request.getReader(), CategoryRequestDTO.class);
//        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);
//        String jsonResponse = objectMapper.writeValueAsString(categoryResponseDTO);
//
//        response.setContentType("application/json");
//        response.getWriter().write(jsonResponse);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        String pathInfo = request.getPathInfo();
//        if (pathInfo != null && pathInfo.length() > 1) {
//            // Obter categoria por ID
//            String categoryId = pathInfo.substring(1);
//            CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(categoryId);
//            String jsonResponse = objectMapper.writeValueAsString(categoryResponseDTO);
//
//            response.setContentType("application/json");
//            response.getWriter().write(jsonResponse);
//        } else {
//            // Listar todas as categorias
//            var categories = categoryService.listAllCategories();
//            String jsonResponse = objectMapper.writeValueAsString(categories);
//
//            response.setContentType("application/json");
//            response.getWriter().write(jsonResponse);
//        }
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Atualizar categoria
//        String pathInfo = request.getPathInfo();
//        if (pathInfo != null && pathInfo.length() > 1) {
//            String categoryId = pathInfo.substring(1);
//            CategoryRequestDTO categoryRequestDTO = objectMapper.readValue(request.getReader(), CategoryRequestDTO.class);
//            CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(categoryId, categoryRequestDTO);
//            String jsonResponse = objectMapper.writeValueAsString(categoryResponseDTO);
//
//            response.setContentType("application/json");
//            response.getWriter().write(jsonResponse);
//        }
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Deletar categoria
//        String pathInfo = request.getPathInfo();
//        if (pathInfo != null && pathInfo.length() > 1) {
//            String categoryId = pathInfo.substring(1);
//            categoryService.deleteCategory(categoryId);
//            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//        }
//    }
//}
