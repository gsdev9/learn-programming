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
     * TickerRepositoryのfinByUserを呼び出す
     *
     * @param user
     * @return
     */
    public List<Ticket> findByUser(User user) { return ticketRepository.findByUser(user); }

    /**
     * TicketRepositoryのfindByTitleOrBodyを呼び出す
     * @param input
     * @return
     */
    public List<Ticket> findByTitleOrBody(String input) { return ticketRepository.findByTitleOrBody(input); }

    /**
     * TicketRepositoryのcreateTicketを呼び出す
     *
     * @param ticket
     */
    public void createTicket(Ticket ticket, Long userId) {
        User user = userService.findById(userId);
        ticket.user = user;
        System.out.println(ticket);
        ticketRepository.createTicket(ticket);
    }

    /**
     * TicketRepositoryのupdateTicketを呼び出す
     *
     * @param ticket
     */
    public void updateTicket(Ticket ticket) {
        ticketRepository.updateTicket(ticket);
    }

    /**
     * TicketRepositoryのdeleteTicketを呼び出す
     *
     * @param ticket
     */
    public void deleteTicket(Ticket ticket) {
        ticketRepository.deleteTicket(ticket);
    }

}
