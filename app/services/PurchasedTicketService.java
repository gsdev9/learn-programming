package services;

import models.*;
import repositories.PurchasedTicketRepository;

import javax.inject.Inject;

/**
 * 購入済みチケット
 *
 * @author arapiku
 */
public class PurchasedTicketService {

    @Inject
    private UserService userService;

    @Inject
    private TicketService ticketService;

    @Inject
    private PurchasedTicketRepository purchasedTicketRepository;

    /**
     * PurchasedTicketRepositoryのcreateを呼び出す
     *
     * @param ticketId
     * @param userId
     */
    public PurchasedTicket create(Long ticketId, Long userId) {
        User buyer = userService.findById(userId);
        Ticket ticket = ticketService.findById(ticketId);

        PurchasedTicket purchasedTicket = new PurchasedTicket();
        purchasedTicket.buyer = buyer;
        purchasedTicket.ticket = ticket;
        purchasedTicket.status = false;

        purchasedTicketRepository.create(purchasedTicket);

        return purchasedTicket;
    }
}
