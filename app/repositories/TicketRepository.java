package repositories;

import models.Ticket;
import play.Logger;
import play.db.jpa.JPAApi;
import play.libs.Json;

import javax.inject.Inject;
import java.util.List;

/**
 * チケットのCRUD処理
 *
 * @author arapiku
 */
public class TicketRepository {

    private final JPAApi jpa;

    @Inject
    public TicketRepository(JPAApi jpa) {
        this.jpa = jpa;
    }

    /**
     * チケットの全件取得
     *
     * @return チケット全件
     */
    public List<Ticket> findAll() {
        return jpa.em().createQuery("SELECT t FROM Ticket t", Ticket.class).getResultList();
    }

    /**
     * チケットの登録
     *
     * @param ticket
     */
    public void createTicket(Ticket ticket) {
        jpa.em().persist(ticket);
        Logger.debug("チケットが作成されました： {}", Json.toJson(ticket));
    }

}
