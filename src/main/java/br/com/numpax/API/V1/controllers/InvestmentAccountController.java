package br.com.numpax.API.V1.controllers;

import br.com.numpax.API.V1.dto.request.InvestmentAccountRequestDTO;
import br.com.numpax.API.V1.dto.response.InvestmentAccountResponseDTO;
import br.com.numpax.API.V1.exceptions.AccountNotFoundException;
import br.com.numpax.API.V1.exceptions.DuplicateAccountException;
import br.com.numpax.application.services.InvestmentAccountService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.validation.Valid;
import java.util.List;

@Path("/api/v1/investment-accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvestmentAccountController {

    @Inject
    private InvestmentAccountService investmentAccountService;

    @POST
    public Response createAccount(@Valid InvestmentAccountRequestDTO requestDTO) {
        try {
            InvestmentAccountResponseDTO response = investmentAccountService.createAccount(requestDTO);
            return Response.status(Response.Status.CREATED)
                         .entity(response)
                         .build();
        } catch (DuplicateAccountException e) {
            throw new WebApplicationException(
                Response.status(Response.Status.CONFLICT)
                       .entity(new ErrorResponse(409, "Conta duplicada", e.getMessage()))
                       .build()
            );
        }
    }

    @GET
    @Path("/{accountId}")
    public Response getAccount(@PathParam("accountId") String accountId) {
        try {
            InvestmentAccountResponseDTO response = investmentAccountService.getAccountById(accountId);
            return Response.ok(response).build();
        } catch (AccountNotFoundException e) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND)
                       .entity(new ErrorResponse(404, "Conta não encontrada", e.getMessage()))
                       .build()
            );
        }
    }

    @PUT
    @Path("/{accountId}")
    public Response updateAccount(
            @PathParam("accountId") String accountId,
            @Valid InvestmentAccountRequestDTO requestDTO) {
        try {
            InvestmentAccountResponseDTO response = investmentAccountService.updateAccount(accountId, requestDTO);
            return Response.ok(response).build();
        } catch (AccountNotFoundException e) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND)
                       .entity(new ErrorResponse(404, "Conta não encontrada", e.getMessage()))
                       .build()
            );
        }
    }

    @DELETE
    @Path("/{accountId}")
    public Response deleteAccount(@PathParam("accountId") String accountId) {
        try {
            investmentAccountService.deleteAccount(accountId);
            return Response.noContent().build();
        } catch (AccountNotFoundException e) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND)
                       .entity(new ErrorResponse(404, "Conta não encontrada", e.getMessage()))
                       .build()
            );
        }
    }

    @PATCH
    @Path("/{accountId}/deactivate")
    public Response deactivateAccount(@PathParam("accountId") String accountId) {
        try {
            investmentAccountService.deactivateAccount(accountId);
            return Response.noContent().build();
        } catch (AccountNotFoundException e) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND)
                       .entity(new ErrorResponse(404, "Conta não encontrada", e.getMessage()))
                       .build()
            );
        }
    }

    @GET
    public Response listAllAccounts() {
        List<InvestmentAccountResponseDTO> accounts = investmentAccountService.listAllAccounts();
        return Response.ok(accounts).build();
    }

    @GET
    @Path("/active")
    public Response listAllActiveAccounts() {
        List<InvestmentAccountResponseDTO> accounts = investmentAccountService.listAllActiveAccounts();
        return Response.ok(accounts).build();
    }

    @GET
    @Path("/inactive")
    public Response listAllInactiveAccounts() {
        List<InvestmentAccountResponseDTO> accounts = investmentAccountService.listAllInactiveAccounts();
        return Response.ok(accounts).build();
    }

    @GET
    @Path("/institution/{institution}")
    public Response findAccountsByInstitution(@PathParam("institution") String institution) {
        List<InvestmentAccountResponseDTO> accounts = investmentAccountService.findAccountsByInstitution(institution);
        return Response.ok(accounts).build();
    }

    @GET
    @Path("/account-number/{accountNumber}")
    public Response findByAccountNumber(@PathParam("accountNumber") String accountNumber) {
        try {
            InvestmentAccountResponseDTO response = investmentAccountService.findByAccountNumber(accountNumber);
            return Response.ok(response).build();
        } catch (AccountNotFoundException e) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND)
                       .entity(new ErrorResponse(404, "Conta não encontrada", e.getMessage()))
                       .build()
            );
        }
    }

    @GET
    @Path("/agency/{agency}/account-number/{accountNumber}")
    public Response findByAgencyAndAccountNumber(
            @PathParam("agency") String agency,
            @PathParam("accountNumber") String accountNumber) {
        try {
            InvestmentAccountResponseDTO response = 
                investmentAccountService.findByAgencyAndAccountNumber(agency, accountNumber);
            return Response.ok(response).build();
        } catch (AccountNotFoundException e) {
            throw new WebApplicationException(
                Response.status(Response.Status.NOT_FOUND)
                       .entity(new ErrorResponse(404, "Conta não encontrada", e.getMessage()))
                       .build()
            );
        }
    }
} 