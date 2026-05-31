package com.stochasticlabs.conditionalroutingoutboxapijava11.controller;

import com.stochasticlabs.conditionalroutingoutboxapijava11.dto.InputDTO;
import com.stochasticlabs.conditionalroutingoutboxapijava11.service.RoutingService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/router")
public class RoutingController {

    private final RoutingService service;

    public RoutingController(RoutingService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@Validated @RequestBody InputDTO dto) {
        service.process(dto.getInteger());
    }
}
