package services;

import models.PurchasedTicket;
import repositories.PurchasedTicketRepository;

import javax.inject.Inject;

/**
 * PurchasedTicketRepositoryに関するサービス層
 */
public class PurchasedTicketService {

    @Inject
    private PurchasedTicketRepository purchasedTicketRepository;

    /**
     * PurchasedTicketRepositoryのfind()を呼び出す
     *
     * @param id
     * @return
     */
    public PurchasedTicket findById(Long id) {
        return purchasedTicketRepository.find(id);
    }


    /**
     * PurchasedTicketRepositoryのregist()を呼び出す
     *
     * @param purchasedTicket
     */
    public void registPurchasedTicket(PurchasedTicket purchasedTicket) {
        purchasedTicketRepository.registchatPurchasedTicket(purchasedTicket);
    }

    /**
     * PurchasedTicketRepositoryのdelete()を呼び出す
     *
     * @param id
     */
    public void deletePurchasedTicket(Long id) {
        PurchasedTicket purchasedTicket = purchasedTicketRepository.find(id);
        purchasedTicketRepository.deletechatPurchasedTicket(purchasedTicket);
    }

}
