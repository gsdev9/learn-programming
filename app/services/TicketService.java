package services;

import models.*;
import repositories.TicketRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * チケットに関するサービス
 *
 * @author arapiku
 */
public class TicketService {

    @Inject
    private TicketRepository ticketRepository;

    @Inject
    private UserService userService;

    /**
     * TicketRepositoryのfindAllを呼び出す
     *
     * @return
     */
    public List<Ticket> findAll() { return ticketRepository.findAll(); }

    /**
     * TicketRepositoryのcreateTicketを呼び出す
     *
     * @param ticket
     */
    public void createTicket(Ticket ticket, Long userId) {
        User user = userService.findById(userId);
        ticket.user = user;
        ticketRepository.createTicket(ticket);
    }

}
