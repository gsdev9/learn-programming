package repositories;

import models.*;
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
     * IDによる単体チケットの取得
     *
     * @param id
     * @return
     */
    public Ticket findById(Long id) {
        return jpa.em().find(Ticket.class, id);
    }

    /**
     * ユーザーに紐づくチケットの取得
     *
     * @param user
     * @return
     */
    public List<Ticket> findByUser(User user) {
        return jpa.em().createQuery("SELECT t FROM Ticket t WHERE user = :user", Ticket.class)
            .setParameter("user", user)
            .getResultList();
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

    /**
     * チケットの更新
     *
     * @param ticket
     */
    public void updateTicket(Ticket ticket) {
        jpa.em().merge(ticket);
        Logger.debug("チケットが更新されました： {}", Json.toJson(ticket));
    }
}
