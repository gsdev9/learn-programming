package repositories;

import models.PurchasedTicket;
import play.Logger;
import play.db.jpa.JPAApi;
import play.libs.Json;

import javax.inject.Inject;

/**
 * 購入済みチケットのCRUD処理
 *
 * @author arapiku
 */
public class PurchasedTicketRepository {

    private final JPAApi jpa;

    @Inject
    public PurchasedTicketRepository(JPAApi jpa) {
        this.jpa = jpa;
    }

    /**
     * 購入済みチケットの登録
     *
     * @param purchasedTicket
     */
    public void create(PurchasedTicket purchasedTicket) {
        jpa.em().persist(purchasedTicket);
        Logger.debug("チケットが購入されました： {}", Json.toJson(purchasedTicket));
    }
}
