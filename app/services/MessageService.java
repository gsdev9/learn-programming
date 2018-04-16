package services;

import models.*;
import repositories.MessageRepository;

import javax.inject.Inject;

/**
 * メッセージ
 *
 * @author arapiku
 */
public class MessageService {

    @Inject
    private MessageRepository messageRepository;

    /**
     * MessageRepositoryのcreateを呼び出す
     *
     * @param message
     * @param userId
     */
    public void create(String message, Long userId, PurchasedTicket purchasedTicket) {
        Message newMessage = new Message();
        newMessage.message = message;
        newMessage.userId = userId;
        newMessage.purchasedTicket = purchasedTicket;

        messageRepository.create(newMessage);
    }
}
