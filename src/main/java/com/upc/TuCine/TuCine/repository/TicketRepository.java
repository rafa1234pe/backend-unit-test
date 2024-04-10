package com.upc.TuCine.TuCine.repository;

import com.upc.TuCine.TuCine.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByUserId (Integer userId);
}
