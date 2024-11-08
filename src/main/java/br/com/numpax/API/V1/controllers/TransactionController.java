package br.com.numpax.API.V1.controllers;

import br.com.numpax.API.V1.dto.request.TransactionRequestDTO;
import br.com.numpax.API.V1.dto.request.TransactionUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.TransactionResponseDTO;
import br.com.numpax.application.services.TransactionService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

@Path("/api/v1/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @POST
    public Response createTransaction(@Valid TransactionRequestDTO dto) {
        TransactionResponseDTO response = transactionService.createTransaction(dto);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @GET
    @Path("/{transactionId}")
    public Response getTransactionById(@PathParam("transactionId") String transactionId) {
        TransactionResponseDTO response = transactionService.getTransactionById(transactionId);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{transactionId}")
    public Response updateTransaction(
            @PathParam("transactionId") String transactionId,
            @Valid TransactionUpdateRequestDTO dto) {
        TransactionResponseDTO response = transactionService.updateTransaction(transactionId, dto);
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{transactionId}/effective")
    public Response markAsEffective(@PathParam("transactionId") String transactionId) {
        transactionService.markAsEffective(transactionId);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{transactionId}/deactivate")
    public Response deactivateTransaction(@PathParam("transactionId") String transactionId) {
        transactionService.deactivateTransaction(transactionId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{transactionId}")
    public Response deleteTransaction(@PathParam("transactionId") String transactionId) {
        transactionService.deleteTransaction(transactionId);
        return Response.noContent().build();
    }

    @GET
    public Response listAllTransactions() {
        List<TransactionResponseDTO> transactions = transactionService.listAllTransactions();
        return Response.ok(transactions).build();
    }

    @GET
    @Path("/active")
    public Response listAllActiveTransactions() {
        List<TransactionResponseDTO> transactions = transactionService.listAllActiveTransactions();
        return Response.ok(transactions).build();
    }

    @GET
    @Path("/inactive")
    public Response listAllInactiveTransactions() {
        List<TransactionResponseDTO> transactions = transactionService.listAllInactiveTransactions();
        return Response.ok(transactions).build();
    }

    @GET
    @Path("/account/{accountId}")
    public Response findByAccountId(@PathParam("accountId") String accountId) {
        List<TransactionResponseDTO> transactions = transactionService.findByAccountId(accountId);
        return Response.ok(transactions).build();
    }

    @GET
    @Path("/category/{categoryId}")
    public Response findByCategoryId(@PathParam("categoryId") String categoryId) {
        List<TransactionResponseDTO> transactions = transactionService.findByCategoryId(categoryId);
        return Response.ok(transactions).build();
    }

    @GET
    @Path("/date-range")
    public Response findByDateRange(
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate) {
        List<TransactionResponseDTO> transactions = transactionService.findByDateRange(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
        return Response.ok(transactions).build();
    }

    @GET
    @Path("/effective")
    public Response findEffectiveTransactions() {
        List<TransactionResponseDTO> transactions = transactionService.findEffectiveTransactions();
        return Response.ok(transactions).build();
    }

    @GET
    @Path("/pending")
    public Response findPendingTransactions() {
        List<TransactionResponseDTO> transactions = transactionService.findPendingTransactions();
        return Response.ok(transactions).build();
    }
} 