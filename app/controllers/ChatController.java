package controllers;

import models.ChatInfo;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import services.ChatInfoService;
import services.PurchasedTicketService;
import services.UserService;

import javax.inject.Inject;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ChatController extends Controller {

    /**
     * エラー時画面出力
     */
    private static final String FILENOTFOUND = "フラッシュはユーザー向けなので「ファイルを認識できませんでした。もう一度お試しください";

    /**
     * エラー時画面出力
     */
    private static final String FAILDEODE = "URLデコードに失敗しました。";

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

    /**
     * ファイルアップロード.
     *
     * @return
     */
    public Result fileUpload() {
        Http.MultipartFormData<File> body = Controller.request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("picture");
        if (picture != null) {
            String fileName = picture.getFilename();
            File tmpFile = picture.getFile();
            //TODO:チャットルームごとに保存領域分ける
            File file = new File(System.getProperty("user.dir") + "/public/uploadfiles/" + fileName);
            tmpFile.renameTo(file);
            try {
                String encodeFileName = URLEncoder.encode(fileName, "UTF-8");
                return Results.ok(encodeFileName);
            } catch (UnsupportedEncodingException e) {
                Controller.flash("error", ChatController.FAILDEODE);
                return Results.badRequest();
            }
        } else {
            Controller.flash("error", ChatController.FILENOTFOUND);
            return Results.badRequest();
        }
    }

    /**
     * ファイルダウンロード.
     *
     * @param fileName
     * @return
     */
    public Result fileDownload(String fileName) {

        File file = new File(System.getProperty("user.dir") + "/public/uploadfiles/" + fileName);
        if (!file.exists()) {
            return Results.badRequest();
        }
        return Results.ok().sendFile(file, false);

    }
}
