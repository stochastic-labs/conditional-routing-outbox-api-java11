package com.stochasticlabs.conditionalroutingoutboxapijava11.repository;

import com.stochasticlabs.conditionalroutingoutboxapijava11.domain.OutboxStatus;
import com.stochasticlabs.conditionalroutingoutboxapijava11.model.Outbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    Page<Outbox> findByStatus(OutboxStatus status, Pageable pageable);
    List<Outbox> findTop10ByStatusOrderByIdAsc(OutboxStatus status);
}
