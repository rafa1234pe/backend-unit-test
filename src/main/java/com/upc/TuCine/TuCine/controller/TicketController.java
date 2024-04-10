package com.upc.TuCine.TuCine.controller;

import com.upc.TuCine.TuCine.dto.ShowtimeDto;
import com.upc.TuCine.TuCine.dto.TicketDto;
import com.upc.TuCine.TuCine.dto.save.Ticket.TicketSaveDto;
import com.upc.TuCine.TuCine.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Ticket", description = "API de Tickets")
@RequestMapping("/api/TuCine/v1")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    //URL: http://localhost:8080/api/TuCine/v1/tickets
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/tickets")
    @Operation(summary = "Obtener toda la lista de tickets")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se obtuvo la lista de tickets",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TicketDto.class,type = "array")
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron los tickets",
                    content = @Content
            )
    })
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/tickets
    //Method: POST
    @Transactional
    @PostMapping("/tickets")
    @Operation(summary = "Crear un nuevo ticket")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Se creó el ticket",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = TicketDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No se pudo crear el ticket",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketSaveDto ticketSaveDto){
        TicketDto createdTicketDto= ticketService.createTicket(ticketSaveDto);
        return new ResponseEntity<>(createdTicketDto, HttpStatus.CREATED);
    }

    //URL: http://localhost:8080/api/TuCine/v1/tickets/1
    //Method: PUT
    @Transactional
    @PutMapping("/tickets/{id}")
    @Operation(summary = "Actualizar ticket")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable Integer id, @RequestBody TicketSaveDto ticketSaveDto){
        TicketDto updatedTicketDto = ticketService.updateTicket(id, ticketSaveDto);
        if (updatedTicketDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedTicketDto,HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/tickets/1
    //Method: DELETE
    @Transactional
    @DeleteMapping("/tickets/{id}")
    @Operation(summary = "Borrar ticket mediante su id")
    public ResponseEntity<TicketDto> deleteTicket(@PathVariable Integer id){
        TicketDto deletedTicketDto = ticketService.deleteTicket(id);
        if (deletedTicketDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedTicketDto,HttpStatus.OK);
    }


}
