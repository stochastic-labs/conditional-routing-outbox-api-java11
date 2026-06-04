package com.stochasticlabs.conditionalroutingoutboxapijava11.controller;

import com.stochasticlabs.conditionalroutingoutboxapijava11.dto.InputDTO;
import com.stochasticlabs.conditionalroutingoutboxapijava11.service.RoutingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/router")
@Tag(name = "Router", description = "Endpoints to routing of payloads")
public class RoutingController {

    private final RoutingService service;

    public RoutingController(RoutingService service) {
        this.service = service;
    }

    @Operation(summary = "Process number", description = "Process number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payload processed success"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Failed all strategies")
    })
    @PostMapping("/input")
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@Validated @RequestBody InputDTO dto) {
        service.process(dto);
    }
}
