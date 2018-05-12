package services;

import models.ChatRoom;
import models.PurchasedTicket;
import models.Ticket;
import models.User;
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
     * PurchasedTicketRepositoryのfindByRoomIdを呼び出す
     *
     * @param roomId
     * @return
     */
    public List<PurchasedTicket> findByRoomId(Long roomId) {
        return purchasedTicketRepository.findByRoomId(roomId);
    }

    /**
     * PurchasedTicketRepositoryのfindByUserIdを呼び出す
     *
     * @param userId
     * @return
     */
    public List<PurchasedTicket> findByBuyerId(Long userId) {
        return purchasedTicketRepository.findByBuyerId(userId);
    }


    /**
     * PurchasedTicketRepositoryのfindByUserIdを呼び出す
     *
     * @param ticketId
     * @return
     */
    public List<PurchasedTicket> findByTicketId(Long ticketId) {
        return purchasedTicketRepository.findByTicketId(ticketId);
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
     * PurchasedTicketRepositoryのfindByIdを呼び出す
     *
     * @param purchasedTicketId
     * @return
     */
    public PurchasedTicket findById(Long purchasedTicketId) {
        return purchasedTicketRepository.findById(purchasedTicketId);
    }


    /**
     * PurchasedTicketRepositoryのcreateを呼び出す
     *
     * @param ticketId
     * @param userId
     */
    public PurchasedTicket create(Long ticketId, Long userId, ChatRoom chatRoom) {
        User buyer = userService.findById(userId);
        Ticket ticket = ticketService.findById(ticketId);

        PurchasedTicket purchasedTicket = new PurchasedTicket();
        purchasedTicket.buyer = buyer;
        purchasedTicket.ticket = ticket;
        purchasedTicket.status = false;
        purchasedTicket.setChatRoom(chatRoom);

        purchasedTicketRepository.create(purchasedTicket);

        return purchasedTicket;
    }

    /**
     * purchasedTicketRepositoryのupdateを呼び出す
     *
     * @param purchasedTicket
     */
    public void update(PurchasedTicket purchasedTicket) {
        purchasedTicketRepository.update(purchasedTicket);
    }

}
