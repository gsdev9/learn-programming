package repositories;

import models.Message;
import play.Logger;
import play.db.jpa.JPAApi;
import play.libs.Json;

import javax.inject.Inject;

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

    public void create(Message message) {
        jpa.em().persist(message);
        Logger.debug("メッセージが送信されました： {}", Json.toJson(message));
    }

}
