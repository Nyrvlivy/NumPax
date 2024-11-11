package br.com.numpax.API.V1.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/modal/*")
public class ModalServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome do modal está faltando.");
            return;
        }

        String modalName = pathInfo.substring(1);

        String jspPath = null;

        switch(modalName) {
            case "new-transaction-menu":
                jspPath = "/WEB-INF/jsp/partials/modals/new-transaction-menu-modal.jsp";
                break;
            case "new-expense":
                jspPath = "/WEB-INF/jsp/partials/modals/new-expense-modal.jsp";
                break;
            case "new-income":
                jspPath = "/WEB-INF/jsp/partials/modals/new-income-modal.jsp";
                break;
            case "new-transfer":
                jspPath = "/WEB-INF/jsp/partials/modals/new-transfer-modal.jsp";
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Modal não encontrado.");
                return;
        }

        request.getRequestDispatcher(jspPath).forward(request, response);
    }
}
