package services;

import models.ChatInfo;
import repositories.ChatInfoRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * ChatInfoに関するサービス層
 */
public class ChatInfoService {

    @Inject
    private ChatInfoRepository chatInfoRepository;

    /**
     * ChatInfoRepositoryのfindById()を呼び出す
     *
     * @param id
     * @return
     */
    public ChatInfo findByUserId(Long id) {
        return chatInfoRepository.findByUserId(id);
    }

    /**
     * ChatInfoRepositoryのfindByUserIdAndRoomId()を呼び出す
     *
     * @param userId
     * @param roomId
     * @return
     */
    public ChatInfo findByUserIdAndRoomId(Long userId, Long roomId) {
        if (chatInfoRepository.findByUserIdAndRoomId(userId, roomId).isEmpty()) {
            return null;
        }

        return chatInfoRepository.findByUserIdAndRoomId(userId, roomId).get(0);
    }


    /**
     * ChatInfoRepositoryのfindByChatRoomId()を呼び出す
     *
     * @param chatRoomId
     * @return
     */
    public List<ChatInfo> findByChatRoomId(Long chatRoomId) {
        return chatInfoRepository.findByChatRoomId(chatRoomId);
    }

    /**
     * ChatInfoRepositoryのregistUser()を呼び出す
     *
     * @param chatInfo
     */
    public void registChatInfo(ChatInfo chatInfo) {
        chatInfoRepository.registchatInfo(chatInfo);
    }

    /**
     * ChatInfoRepositoryのdeleteUser()を呼び出す
     *
     * @param userID
     */
    public void deleteChatInfo(Long userID) {
        ChatInfo chatInfo = chatInfoRepository.findByUserId(userID);
        chatInfoRepository.deletechatInfo(chatInfo);
    }

    /**
     * ChatInfoRepositoryのdeleteUser()を呼び出す
     *
     * @param chatInfo
     */
    public void updatechatInfo(ChatInfo chatInfo) {
        chatInfoRepository.updatechatInfo(chatInfo);
    }

}
