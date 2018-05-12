package controllers;

import models.Message;
import models.PurchasedTicket;
import models.Ticket;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.MessageService;
import services.PurchasedTicketService;
import services.TicketService;
import services.UserService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.mvc.Controller.session;
import static play.mvc.Results.badRequest;

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
    private MessageService messageService;

    @Inject
    private UserService userService;

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

    /**
     * 購入済みチケット画面−メッセージ登録-
     *
     * @param purchasedId
     * @param message
     * @return
     */
    @Transactional
    public Result sendMessage(Long purchasedId, String message) {
        String userId = Controller.session().get("userID");
        PurchasedTicket purchasedTicket = purchasedTicketService.findById(purchasedId);
        //チケットの空判定、ユーザーの確認
        if (purchasedTicket == null || purchasedTicket.getBuyer().getUserId().equals(userId) || purchasedTicket.getTicket().getUser().getUserId().equals(userId)) {
            return badRequest();
        }
        messageService.create(message, Long.parseLong(userId), purchasedTicket);
        return Results.ok(userService.idPerserName(Long.parseLong(userId)) + " > " + message);

    }


    /**
     * 購入済みチケット画面−メッセージ参照-
     *
     * @param purchasedId
     * @return
     */
    @Transactional
    public Result getMessage(Long purchasedId) {
        String userId = Controller.session().get("userID");
        PurchasedTicket purchasedTicket = purchasedTicketService.findById(purchasedId);
        //チケットの空判定、ユーザーの確認
        if (purchasedTicket == null || purchasedTicket.getBuyer().getUserId().equals(userId) || purchasedTicket.getTicket().getUser().getUserId().equals(userId)) {
            return badRequest();
        }
        List<Message> messageList = messageService.findByPurchasedTicketId(purchasedId);
        List<Map<String, String>> messageToJson = new ArrayList<>();
        for (Message m : messageList) {
            Map<String, String> messageMap = new HashMap<>();
            messageMap.put("message", userService.idPerserName(Long.parseLong(userId)) + " > " + m.getMessageText());
            messageToJson.add(messageMap);
        }
        return Results.ok(Json.toJson(messageToJson));
    }
}
