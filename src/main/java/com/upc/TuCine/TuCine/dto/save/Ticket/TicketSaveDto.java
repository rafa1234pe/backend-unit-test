package com.upc.TuCine.TuCine.dto.save.Ticket;

import lombok.Data;

@Data
public class TicketSaveDto {
    private Integer numberSeats;
    private Float totalPrice;

    private TicketCustomerSaveDto user;

    private TicketShowtimeSaveDto showtime;
}
