package controllers;

import play.data.FormFactory;
import services.TicketService;

import javax.inject.Inject;

public class PurchasedTicketController {

    private final FormFactory formFactory;

    @Inject
    private TicketService ticketService;

    @Inject
    public PurchasedTicketController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

}
