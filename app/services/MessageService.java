package services;

import models.Message;
import models.PurchasedTicket;
import repositories.MessageRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * メッセージ
 *
 * @author arapiku
 */
public class MessageService {

    @Inject
    private MessageRepository messageRepository;

    /**
     * MessageRepositoryのfindByPurchasedTicketIdを呼び出す
     *
     * @param purchasedTicketId
     * @return
     */
    public List<Message> findByPurchasedTicketId(Long purchasedTicketId) {
        return messageRepository.findByPurchasedTicketId(purchasedTicketId);
    }

    /**
     * MessageRepositoryのcreateを呼び出す
     *
     * @param message
     * @param userId
     */
    public void create(String message, Long userId, PurchasedTicket purchasedTicket) {
        Message newMessage = new Message();
        newMessage.messageText = message;
        newMessage.userId = userId;
        newMessage.purchasedTicket = purchasedTicket;

        messageRepository.create(newMessage);
    }

}
