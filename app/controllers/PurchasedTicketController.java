package controllers;

import models.*;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.*;

import javax.inject.Inject;

import static play.mvc.Controller.session;

/**
 * 購入済みチケット
 *
 * @author arapiku
 */
public class PurchasedTicketController {

    private final FormFactory formFactory;

    @Inject
    private TicketService ticketService;

    @Inject
    private PurchasedTicketService purchasedTicketService;

    @Inject
    public PurchasedTicketController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    /**
     * 購入済みチケット画面
     *
     * @param purchasedTicketId
     * @return
     */
    @Transactional
    public Result index(Long purchasedTicketId) {
        System.out.println(purchasedTicketId);
        Long userId = Long.valueOf(session("userID"));
        PurchasedTicket purchasedTicket = purchasedTicketService.findByTicketIdAndUserId(purchasedTicketId, userId);
        Ticket ticket = ticketService.findById(purchasedTicket.ticket.getTicketId());
        return Results.ok(views.html.purchasedTicket.index.render(ticket, purchasedTicket));
    }

}
