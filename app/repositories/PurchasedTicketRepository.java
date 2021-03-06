package repositories;

import models.PurchasedTicket;
import play.Logger;
import play.db.jpa.JPAApi;
import play.libs.Json;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.List;

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
     * ユーザーに紐づく購入済みチケットリストの取得
     *
     * @param userId
     * @return
     */
    public List<PurchasedTicket> findByUserId(Long userId) {
        return jpa.em().createQuery("SELECT p FROM PurchasedTicket p WHERE p.ticket.user.userId = :ownerId OR p.buyer.userId = :buyerId", PurchasedTicket.class)
            .setParameter("ownerId", userId)
            .setParameter("buyerId", userId)
            .getResultList();
    }

    /**
     * chatroomに紐づく購入済みチケットリストの取得
     *
     * @param roomId
     * @return
     */
    public List<PurchasedTicket> findByRoomId(Long roomId) {
        return jpa.em().createQuery("SELECT p FROM PurchasedTicket p WHERE p.chatRoom.chatRoomId = :roomId", PurchasedTicket.class)
                .setParameter("roomId", roomId)
                .getResultList();
    }

    /**
     * 購入者に紐づく購入済みチケットリストの取得
     *
     * @param userId
     * @return
     */
    public List<PurchasedTicket> findByBuyerId(Long userId) {
        return jpa.em().createQuery("SELECT p FROM PurchasedTicket p WHERE p.buyer.userId = :buyerId", PurchasedTicket.class)
                .setParameter("buyerId", userId)
                .getResultList();
    }

    /**
     * チケットIDに紐づく購入済みチケットリストの取得
     *
     * @param ticketId
     * @return
     */
    public List<PurchasedTicket> findByTicketId(Long ticketId) {
        return jpa.em().createQuery("SELECT p FROM PurchasedTicket p WHERE p.ticket.ticketId = :ticketId", PurchasedTicket.class)
                .setParameter("ticketId", ticketId)
                .getResultList();
    }

    /**
     * purchasedTicketId(主キー)で検索
     *
     * @param purchasedTicketId
     * @return
     */
    public PurchasedTicket findById(Long purchasedTicketId) {
        return jpa.em().find(PurchasedTicket.class, purchasedTicketId);
    }

    /**
     * 購入済みチケット情報の取得
     *
     * @param purchasedTicketId
     * @param userId
     * @return
     */
    public PurchasedTicket findByTicketIdAndUserId(Long purchasedTicketId, Long userId) {
        try {
            return jpa.em().createQuery("SELECT p FROM PurchasedTicket p WHERE (p.purchasedTicketId = :purchasedTicketId AND p.buyer.userId = :userId) OR (p.purchasedTicketId = :purchasedTicketId AND p.ticket.user.userId = :userId)", PurchasedTicket.class)
                .setParameter("purchasedTicketId", purchasedTicketId)
                .setParameter("userId", userId)
                .getSingleResult();
        } catch (NoResultException ne) {
            return null;
        }
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

    /**
     * 購入済みチケットの更新
     *
     * @param purchasedTicket
     */
    public void update(PurchasedTicket purchasedTicket) {
        jpa.em().merge(purchasedTicket);
        Logger.debug("購入済みチケットが更新されました： {}", Json.toJson(purchasedTicket));
    }
}
