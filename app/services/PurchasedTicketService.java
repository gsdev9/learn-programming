package services;

import models.*;
import repositories.PurchasedTicketRepository;

import javax.inject.Inject;
import java.util.List;

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
     * PurchasedTicketRepositoryのfindByUserIdを呼び出す
     *
     * @param userId
     * @return
     */
    public List<PurchasedTicket> findByUserId(Long userId) {
        return purchasedTicketRepository.findByUserId(userId);
    }

    /**
     * PurchasedTicketRepositoryのfindByTicketIdAndUserIdを呼び出す
     *
     * @param purchasedTicketId
     * @param userId
     * @return
     */
    public PurchasedTicket findByTicketIdAndUserId(Long purchasedTicketId, Long userId) {
        return purchasedTicketRepository.findByTicketIdAndUserId(purchasedTicketId, userId);
    }

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
