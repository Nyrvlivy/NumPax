package br.com.numpax.API.V1.controllers;

import br.com.numpax.API.V1.dto.request.VariableInvestmentRequestDTO;
import br.com.numpax.API.V1.dto.request.VariableInvestmentUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.VariableInvestmentResponseDTO;
import br.com.numpax.application.services.VariableInvestmentService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Path("/api/v1/variable-investments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VariableInvestmentController {

    private final VariableInvestmentService variableInvestmentService;

    public VariableInvestmentController(VariableInvestmentService variableInvestmentService) {
        this.variableInvestmentService = variableInvestmentService;
    }

    @POST
    public Response createInvestment(@Valid VariableInvestmentRequestDTO dto) {
        VariableInvestmentResponseDTO response = variableInvestmentService.createInvestment(dto);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @GET
    @Path("/{investmentId}")
    public Response getInvestmentById(@PathParam("investmentId") String investmentId) {
        VariableInvestmentResponseDTO response = variableInvestmentService.getInvestmentById(investmentId);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{investmentId}")
    public Response updateInvestment(
            @PathParam("investmentId") String investmentId,
            @Valid VariableInvestmentUpdateRequestDTO dto) {
        VariableInvestmentResponseDTO response = variableInvestmentService.updateInvestment(investmentId, dto);
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{investmentId}/price")
    public Response updateCurrentPrice(
            @PathParam("investmentId") String investmentId,
            @QueryParam("price") BigDecimal newPrice) {
        variableInvestmentService.updateCurrentPrice(investmentId, newPrice);
        return Response.noContent().build();
    }

    @POST
    @Path("/batch-update-prices")
    public Response batchUpdatePrices(Map<String, BigDecimal> tickerPrices) {
        List<String> tickers = tickerPrices.keySet().stream().toList();
        List<BigDecimal> prices = tickerPrices.values().stream().toList();
        
        variableInvestmentService.batchUpdatePrices(tickers, prices);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{investmentId}/deactivate")
    public Response deactivateInvestment(@PathParam("investmentId") String investmentId) {
        variableInvestmentService.deactivateInvestment(investmentId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{investmentId}")
    public Response deleteInvestment(@PathParam("investmentId") String investmentId) {
        variableInvestmentService.deleteInvestment(investmentId);
        return Response.noContent().build();
    }

    @GET
    public Response listAllInvestments() {
        List<VariableInvestmentResponseDTO> investments = variableInvestmentService.listAllInvestments();
        return Response.ok(investments).build();
    }

    @GET
    @Path("/active")
    public Response listAllActiveInvestments() {
        List<VariableInvestmentResponseDTO> investments = variableInvestmentService.listAllActiveInvestments();
        return Response.ok(investments).build();
    }

    @GET
    @Path("/inactive")
    public Response listAllInactiveInvestments() {
        List<VariableInvestmentResponseDTO> investments = variableInvestmentService.listAllInactiveInvestments();
        return Response.ok(investments).build();
    }

    @GET
    @Path("/account/{accountId}")
    public Response findByInvestmentAccountId(@PathParam("accountId") String accountId) {
        List<VariableInvestmentResponseDTO> investments = variableInvestmentService.findByInvestmentAccountId(accountId);
        return Response.ok(investments).build();
    }

    @GET
    @Path("/ticker/{ticker}")
    public Response findByTicker(@PathParam("ticker") String ticker) {
        List<VariableInvestmentResponseDTO> investments = variableInvestmentService.findByTicker(ticker);
        return Response.ok(investments).build();
    }

    @GET
    @Path("/sector/{sector}")
    public Response findBySector(@PathParam("sector") String sector) {
        List<VariableInvestmentResponseDTO> investments = variableInvestmentService.findBySector(sector);
        return Response.ok(investments).build();
    }

    @GET
    @Path("/purchase-date-range")
    public Response findByPurchaseDateRange(
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate) {
        List<VariableInvestmentResponseDTO> investments = variableInvestmentService.findByPurchaseDateRange(
            LocalDate.parse(startDate),
            LocalDate.parse(endDate)
        );
        return Response.ok(investments).build();
    }

    @GET
    @Path("/profit-range")
    public Response findByProfitRange(
            @QueryParam("minProfit") BigDecimal minProfit,
            @QueryParam("maxProfit") BigDecimal maxProfit) {
        List<VariableInvestmentResponseDTO> investments = variableInvestmentService.findByProfitRange(
            minProfit,
            maxProfit
        );
        return Response.ok(investments).build();
    }

    @GET
    @Path("/profitable")
    public Response findProfitableInvestments() {
        List<VariableInvestmentResponseDTO> investments = variableInvestmentService.findProfitableInvestments();
        return Response.ok(investments).build();
    }

    @GET
    @Path("/unprofitable")
    public Response findUnprofitableInvestments() {
        List<VariableInvestmentResponseDTO> investments = variableInvestmentService.findUnprofitableInvestments();
        return Response.ok(investments).build();
    }
} 