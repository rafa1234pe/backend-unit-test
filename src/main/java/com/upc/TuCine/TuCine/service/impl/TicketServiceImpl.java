package com.upc.TuCine.TuCine.service.impl;

import com.upc.TuCine.TuCine.dto.TicketDto;
import com.upc.TuCine.TuCine.dto.save.Ticket.TicketSaveDto;
import com.upc.TuCine.TuCine.shared.exception.ValidationException;
import com.upc.TuCine.TuCine.model.Showtime;
import com.upc.TuCine.TuCine.model.Ticket;
import com.upc.TuCine.TuCine.repository.ShowtimeRepository;
import com.upc.TuCine.TuCine.repository.TicketRepository;
import com.upc.TuCine.TuCine.service.TicketService;
import com.upc.TuCine.TuCine.user.domain.model.User;
import com.upc.TuCine.TuCine.user.persistence.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TicketServiceImpl() {
        this.modelMapper = new ModelMapper();
    }

    private TicketDto EntityToDto(Ticket ticket){
        return modelMapper.map(ticket, TicketDto.class);
    }

    private Ticket DtoToEntity(TicketDto ticketDto){
        return modelMapper.map(ticketDto, Ticket.class);
    }
    @Override
    public List<TicketDto> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(this::EntityToDto)
                .collect(Collectors.toList());
    }
    @Override
    public TicketDto createTicket(TicketSaveDto ticketSaveDto) {

        TicketDto ticketDto = modelMapper.map(ticketSaveDto, TicketDto.class);

        validateTicket(ticketDto);
        existsUserById(ticketDto.getUser().getId());
        existsShowtimeById(ticketDto.getShowtime().getId());

        User user = userRepository.findById(ticketDto.getUser().getId()).orElse(null);
        ticketDto.setUser(user);

        Showtime showtime = showtimeRepository.findById(ticketDto.getShowtime().getId()).orElse(null);
        ticketDto.setShowtime(showtime);

        ticketDto.setStatus("Activo");
        ticketDto.setDateEmition(LocalDate.now());

        Ticket ticket = DtoToEntity(ticketDto);
        Ticket createdTicket = ticketRepository.save(ticket);
        return EntityToDto(createdTicket);
    }

    @Override
    public TicketDto updateTicket(Integer id, TicketSaveDto ticketSaveDto) {

        Ticket ticketToUpdate = ticketRepository.findById(id).orElse(null);
        if (ticketToUpdate == null) {
            return null; // O lanzar una excepción si lo prefieres
        }

        TicketDto ticketDto = modelMapper.map(ticketSaveDto, TicketDto.class);

        validateTicket(ticketDto);

        User user = userRepository.findById(ticketDto.getUser().getId()).orElse(null);
        ticketDto.setUser(user);

        Showtime showtime = showtimeRepository.findById(ticketDto.getShowtime().getId()).orElse(null);
        ticketDto.setShowtime(showtime);


        // Actualizar los campos del ticket existente
        ticketToUpdate.setUser(ticketDto.getUser());
        ticketToUpdate.setShowtime(ticketDto.getShowtime());
        ticketToUpdate.setNumberSeats(ticketDto.getNumberSeats());
        ticketToUpdate.setTotalPrice(ticketDto.getTotalPrice());

        validateTicket(ticketDto);
        existsUserById(ticketDto.getUser().getId());
        existsShowtimeById(ticketDto.getShowtime().getId());

        Ticket updatedTicket = ticketRepository.save(ticketToUpdate);
        return EntityToDto(updatedTicket);
    }

    @Override
    public TicketDto deleteTicket(Integer id) {
        Ticket ticketToDelete = ticketRepository.findById(id).orElse(null);
        if (ticketToDelete == null) {
            return null; // O lanzar una excepción si lo prefieres
        }
        ticketRepository.delete(ticketToDelete);
        return EntityToDto(ticketToDelete);
    }

    protected static void validateTicket(TicketDto ticket) {
        if (ticket.getUser() == null) {
            throw new ValidationException("Customer id es requerido");
        }
        if (ticket.getShowtime() == null) {
            throw new ValidationException("Showtime id es requerido");
        }
        if (ticket.getNumberSeats() == null || ticket.getNumberSeats() == 0) {
            throw new ValidationException("El numero de asientos debe ser mayor a 0");
        }
        if (ticket.getTotalPrice() == null || ticket.getTotalPrice() <= 0) {
            throw new ValidationException("El precio total es requerido y debe ser mayor a 0");
        }
    }

    private void existsUserById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new ValidationException("Customer id not found");
        }
    }

    private void existsShowtimeById(Integer id) {
        if (!showtimeRepository.existsById(id)) {
            throw new ValidationException("Showtime id not found");
        }
    }
}
