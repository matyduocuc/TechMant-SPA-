package com.example.Gestion_de_ticket.repository;

import com.example.Gestion_de_ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
