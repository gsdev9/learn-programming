package services;

import models.ChatRoom;
import repositories.ChatRoomRepository;

import javax.inject.Inject;

/**
 * ChatRoomに関するサービス層
 */
public class ChatRoomService {

    @Inject
    private ChatRoomRepository chatRoomRepository;

    /**
     * ChatRoomRepositoryのfindByRoomId()を呼び出す
     *
     * @param roomId
     * @return
     */
    public ChatRoom findByRoomId(Long roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }


    /**
     * ChatRoomRepositoryのregistChatRoomを呼び出す
     *
     * @param chatRoom
     */
    public void registChatRoom(ChatRoom chatRoom) {
        chatRoomRepository.registChatRoom(chatRoom);
    }

    /**
     * ChatRoomRepositoryのdeleteChatRoomを呼び出す
     *
     * @param userID
     */
    public void deleteChatRoom(Long userID) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(userID);
        chatRoomRepository.deleteChatRoom(chatRoom);
    }

    /**
     * ChatRoomRepositoryのupdateChatRoomを呼び出す
     *
     * @param chatRoom
     */
    public void update(ChatRoom chatRoom) {
        chatRoomRepository.updateChatRoom(chatRoom);
    }


}
