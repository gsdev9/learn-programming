package controllers;

import models.ChatInfo;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.ChatInfoService;
import services.PurchasedTicketService;
import services.UserService;

import javax.inject.Inject;
import java.util.List;

public class ChatController extends Controller {

    @Inject
    private ChatInfoService chatInfoService;

    @Inject
    private UserService userService;

    @Inject
    private PurchasedTicketService purchasedTicketService;


    /**
     * チャット画面
     *
     * @param roomId
     * @return
     */
    public Result chatRoute(Long roomId) {
        //TODO::ユーザーID整合性確認
        return Results.ok(views.html.chat.chat.render(roomId));
    }

    /**
     * peerIdを受け取るrest
     *
     * @param peerId
     * @param roomId
     * @return
     */
    @Transactional
    public Result peerIdSend(String peerId, Long roomId) {
        String userId = Controller.session().get("userID");
        String userName = userService.idPerserName(Long.parseLong(userId));
        ChatInfo chatInfo = chatInfoService.findByUserId(Long.parseLong(userId));
        if (chatInfo == null) {
            chatInfo = new ChatInfo();
            chatInfo.setUserId(Long.parseLong(userId));
            chatInfo.setPeerId(peerId);
            chatInfo.setChatRoomId(roomId);
            chatInfoService.registChatInfo(chatInfo);
        }
        chatInfo.setPeerId(peerId);
        chatInfo.setChatRoomId(roomId);
        chatInfoService.updatechatInfo(chatInfo);
        Logger.info("userId:{} , peerId:{}", userId, peerId);
        return Results.ok();
    }

    /**
     * peerIdを送るrest
     *
     * @param roomId
     * @return
     */
    @Transactional
    public Result peerIdGet(Long roomId) {
        List<ChatInfo> chatInfoList = chatInfoService.findByChatRoomId(roomId);
        if (chatInfoList != null) {
            for (ChatInfo bean : chatInfoList) {
                if (!Long.toString(bean.getUserId()).equals(Controller.session().get("userID"))) {
                    ChatInfo chatInfo = bean;
                    return Results.ok(Json.toJson(chatInfo));
                }
            }
        }
        return Results.ok();
    }
}
