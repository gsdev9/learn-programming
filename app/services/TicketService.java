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
     * @return チケット全件
     */
    public List<Ticket> findAll() { return ticketRepository.findAll(); }

    /**
     * TicketRepositoryのfindByIdを呼び出す
     *
     * @param id
     * @return チケット{id}
     */
    public Ticket findById(Long id) { return ticketRepository.findById(id); }

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
