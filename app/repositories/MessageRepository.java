package repositories;

import models.Message;
import play.Logger;
import play.db.jpa.JPAApi;
import play.libs.Json;

import javax.inject.Inject;
import java.util.List;

/**
 * メッセージのCRUD処理
 *
 * @author arapiku
 */
public class MessageRepository {

    private final JPAApi jpa;

    @Inject
    public MessageRepository(JPAApi jpa) {
        this.jpa = jpa;
    }


    public List<Message> findByPurchasedTicketId(Long purchasedTicketId) {
        return jpa.em().createQuery("SELECT m FROM Message m WHERE m.purchasedTicket.purchasedTicketId = :purchasedTicketId")
                .setParameter("purchasedTicketId", purchasedTicketId)
                .getResultList();
    }

    public void create(Message message) {
        jpa.em().persist(message);
        Logger.debug("メッセージが送信されました： {}", Json.toJson(message));
    }

}
