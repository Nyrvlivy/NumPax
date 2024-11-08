package br.com.numpax.API.V1.exceptions.handler;

import br.com.numpax.API.V1.exceptions.AccountNotFoundException;
import br.com.numpax.API.V1.exceptions.DuplicateAccountException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvestmentAccountExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof AccountNotFoundException) {
            ErrorResponse error = new ErrorResponse(
                Response.Status.NOT_FOUND.getStatusCode(),
                "Conta não encontrada",
                e.getMessage()
            );
            return Response.status(Response.Status.NOT_FOUND)
                         .entity(error)
                         .build();
        }
        
        if (e instanceof DuplicateAccountException) {
            ErrorResponse error = new ErrorResponse(
                Response.Status.CONFLICT.getStatusCode(),
                "Conta duplicada",
                e.getMessage()
            );
            return Response.status(Response.Status.CONFLICT)
                         .entity(error)
                         .build();
        }

        // Erro genérico
        ErrorResponse error = new ErrorResponse(
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            "Erro interno do servidor",
            "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde."
        );
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                     .entity(error)
                     .build();
    }
} 