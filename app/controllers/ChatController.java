package controllers;

import models.ChatInfo;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.ChatInfoService;
import services.PurchasedTicketService;

import javax.inject.Inject;
import java.util.List;

public class ChatController extends Controller {

    @Inject
    private ChatInfoService chatInfoService;

    @Inject
    private PurchasedTicketService purchasedTicketService;

    public Result chatRoute() {
        return Results.ok(views.html.chat.chat.render());
    }

    @Transactional
    public Result peerIdSend(String peerId) {
        String userID = Controller.session().get("userID");
        ChatInfo chatInfo = chatInfoService.findById(Long.parseLong(userID));
        if (chatInfo == null) {
            chatInfo.setUserId(Long.parseLong(userID));
            chatInfo.setPeerId(peerId);
        }
        //TODO:chatルームID取得方法後から決める
        chatInfo.setChatRoomId(1L);
        chatInfoService.registChatInfo(chatInfo);
        Logger.info("userId:{} , peerId:{}", userID, peerId);
        return Results.ok();
    }

    public Result peerIdGet(String peerId) {
        //TODO:chatルームID取得方法後から決める
        List<ChatInfo> chatInfoList = chatInfoService.findByChatRoomId(1L);
        if (chatInfoList != null) {
            for (ChatInfo bean : chatInfoList) {
                if (!Long.toString(bean.getUserId()).equals(Controller.session().get("userID"))) {
                    ChatInfo chatInfo = bean;
                    return Results.ok();
                }
            }
        }
        return Results.ok();
    }
}
