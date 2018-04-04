package repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.PurchasedTicket;
import play.db.jpa.JPAApi;

/**
 * ChatInfoエンティティに対するCURD処理
 */
@Singleton
public class PurchasedTicketRepository {

    private final JPAApi jpa;

    @Inject
    public PurchasedTicketRepository(JPAApi jpa) {
        this.jpa = jpa;
    }


    /**
     * purchasedTicketのID検索
     *
     * @param purchasedTicketId
     * @return
     */
    public PurchasedTicket find(Long purchasedTicketId) {
        return jpa.em().find(PurchasedTicket.class, purchasedTicketId);
    }


    /**
     * purchasedTicketの新規登録
     *
     * @param purchasedTicket
     */
    public void registchatPurchasedTicket(PurchasedTicket purchasedTicket) {
        jpa.em().persist(purchasedTicket);
    }

    /**
     * purchasedTicketの論理削除
     *
     * @param purchasedTicket
     */
    public void deletechatPurchasedTicket(PurchasedTicket purchasedTicket) {
        jpa.em().remove(purchasedTicket);
    }

    /**
     * purchasedTicket情報の変更後
     *
     * @param purchasedTicket
     */
    public void updatechatPurchasedTicket(PurchasedTicket purchasedTicket) {
        jpa.em().merge(purchasedTicket);
    }
}
