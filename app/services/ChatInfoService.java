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

//    @Inject
//    private UserDetailDTO userDetailDTO;


    /**
     * ChatInfoRepositoryのfindById()を呼び出す
     *
     * @param id
     * @return
     */
    public ChatInfo findById(Long id) {
        return chatInfoRepository.find(id);
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
        ChatInfo chatInfo = chatInfoRepository.find(userID);
        chatInfoRepository.deletechatInfo(chatInfo);
    }

}
