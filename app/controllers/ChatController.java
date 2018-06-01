package controllers;

import models.*;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import services.*;

import javax.inject.Inject;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ChatController extends Controller {

    @Inject
    private ChatRoomService chatRoomService;

    @Inject
    private ChatInfoService chatInfoService;

    @Inject
    private UserService userService;

    /**
     * チャット画面
     *
     * @param roomId
     * @return
     */
    @Transactional(readOnly = true)
    public Result chatRoute(Long roomId) {
        ChatRoom chatRoom = chatRoomService.findByRoomId(roomId);
        if (chatRoom == null) {
            Logger.warn("id={}'s chatRoom is not founded.", roomId);
            return Results.notFound(views.html.notfound.index.render());
        }
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
                Logger.error("URLデコードに失敗しました。");
                return Results.badRequest();
            }
        } else {
            Controller.flash("error", "Missing file");
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
        if (file.exists()) {
            return Results.ok().sendFile(file, false);
        }
        return Results.badRequest();
    }
}
