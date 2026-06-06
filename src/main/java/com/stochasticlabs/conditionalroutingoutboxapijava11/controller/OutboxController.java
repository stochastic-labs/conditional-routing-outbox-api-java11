package com.stochasticlabs.conditionalroutingoutboxapijava11.controller;

import com.stochasticlabs.conditionalroutingoutboxapijava11.domain.OutboxStatus;
import com.stochasticlabs.conditionalroutingoutboxapijava11.model.Outbox;
import com.stochasticlabs.conditionalroutingoutboxapijava11.repository.OutboxRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/outbox")
@Tag(name = "Outbox", description = "Endpoints to get outboxes.")
public class OutboxController {

    private final OutboxRepository outboxRepository;

    @GetMapping
    public ResponseEntity<Page<Outbox>> getPagedEvents(
            @RequestParam(required = false) OutboxStatus status,
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Outbox> result;

        if (status != null) {
            result = outboxRepository.findByStatus(status, pageable);
        } else {
            result = outboxRepository.findAll(pageable);
        }

        return ResponseEntity.ok(result);
    }
}
