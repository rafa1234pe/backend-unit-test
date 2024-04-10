package com.upc.TuCine.TuCine.dto;

import com.upc.TuCine.TuCine.model.Showtime;
import com.upc.TuCine.TuCine.user.domain.model.User;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
public class TicketDto {
    private Integer id;
    private Integer numberSeats;
    private Float totalPrice;
    private String status;
    private LocalDate dateEmition;
    private User user;
    private Showtime showtime;
}
