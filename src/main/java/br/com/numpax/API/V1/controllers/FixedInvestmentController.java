package br.com.numpax.API.V1.controllers;

import br.com.numpax.API.V1.dto.request.FixedInvestmentRequestDTO;
import br.com.numpax.API.V1.dto.request.FixedInvestmentUpdateRequestDTO;
import br.com.numpax.API.V1.dto.response.FixedInvestmentResponseDTO;
import br.com.numpax.application.services.FixedInvestmentService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

@Path("/api/v1/fixed-investments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FixedInvestmentController {

    private final FixedInvestmentService fixedInvestmentService;

    public FixedInvestmentController(FixedInvestmentService fixedInvestmentService) {
        this.fixedInvestmentService = fixedInvestmentService;
    }

    @POST
    public Response createInvestment(@Valid FixedInvestmentRequestDTO dto) {
        FixedInvestmentResponseDTO response = fixedInvestmentService.createInvestment(dto);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @GET
    @Path("/{investmentId}")
    public Response getInvestmentById(@PathParam("investmentId") String investmentId) {
        FixedInvestmentResponseDTO response = fixedInvestmentService.getInvestmentById(investmentId);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{investmentId}")
    public Response updateInvestment(
            @PathParam("investmentId") String investmentId,
            @Valid FixedInvestmentUpdateRequestDTO dto) {
        FixedInvestmentResponseDTO response = fixedInvestmentService.updateInvestment(investmentId, dto);
        return Response.ok(response).build();
    }

    @POST
    @Path("/{investmentId}/redeem")
    public Response redeemInvestment(
            @PathParam("investmentId") String investmentId,
            @QueryParam("redemptionDate") String redemptionDate,
            @QueryParam("redemptionAmount") double redemptionAmount) {
        fixedInvestmentService.redeemInvestment(
            investmentId,
            LocalDate.parse(redemptionDate),
            redemptionAmount
        );
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{investmentId}/deactivate")
    public Response deactivateInvestment(@PathParam("investmentId") String investmentId) {
        fixedInvestmentService.deactivateInvestment(investmentId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{investmentId}")
    public Response deleteInvestment(@PathParam("investmentId") String investmentId) {
        fixedInvestmentService.deleteInvestment(investmentId);
        return Response.noContent().build();
    }

    @GET
    public Response listAllInvestments() {
        List<FixedInvestmentResponseDTO> investments = fixedInvestmentService.listAllInvestments();
        return Response.ok(investments).build();
    }

    @GET
    @Path("/active")
    public Response listAllActiveInvestments() {
        List<FixedInvestmentResponseDTO> investments = fixedInvestmentService.listAllActiveInvestments();
        return Response.ok(investments).build();
    }

    @GET
    @Path("/inactive")
    public Response listAllInactiveInvestments() {
        List<FixedInvestmentResponseDTO> investments = fixedInvestmentService.listAllInactiveInvestments();
        return Response.ok(investments).build();
    }

    @GET
    @Path("/account/{accountId}")
    public Response findByInvestmentAccountId(@PathParam("accountId") String accountId) {
        List<FixedInvestmentResponseDTO> investments = fixedInvestmentService.findByInvestmentAccountId(accountId);
        return Response.ok(investments).build();
    }

    @GET
    @Path("/maturity-range")
    public Response findByMaturityDateRange(
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate) {
        List<FixedInvestmentResponseDTO> investments = fixedInvestmentService.findByMaturityDateRange(
            LocalDate.parse(startDate),
            LocalDate.parse(endDate)
        );
        return Response.ok(investments).build();
    }

    @GET
    @Path("/redeemed")
    public Response findRedeemedInvestments() {
        List<FixedInvestmentResponseDTO> investments = fixedInvestmentService.findRedeemedInvestments();
        return Response.ok(investments).build();
    }

    @GET
    @Path("/active-investments")
    public Response findActiveInvestments() {
        List<FixedInvestmentResponseDTO> investments = fixedInvestmentService.findActiveInvestments();
        return Response.ok(investments).build();
    }

    @GET
    @Path("/matured")
    public Response findMaturedInvestments() {
        List<FixedInvestmentResponseDTO> investments = fixedInvestmentService.findMaturedInvestments();
        return Response.ok(investments).build();
    }
} 