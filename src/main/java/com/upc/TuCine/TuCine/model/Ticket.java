package com.upc.TuCine.TuCine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upc.TuCine.TuCine.user.domain.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number_seats", nullable = false)
    private Integer numberSeats;

    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "date_emition", nullable = false)
    private LocalDate dateEmition;

    @ManyToOne
    @JoinColumn(name = "User_id", nullable = false, foreignKey = @ForeignKey(name = "FK_TICKET_PERSON"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @ManyToOne
    @JoinColumn(name = "Showtime_id", nullable = false, foreignKey = @ForeignKey(name = "FK_TICKET_SHOWTIME"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Showtime showtime;
}
