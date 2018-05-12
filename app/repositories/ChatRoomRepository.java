package repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.ChatRoom;
import play.db.jpa.JPAApi;

/**
 * ChatRoomエンティティに対するCURD処理
 */
@Singleton
public class ChatRoomRepository {

    private final JPAApi jpa;

    @Inject
    public ChatRoomRepository(JPAApi jpa) {
        this.jpa = jpa;
    }


    /**
     * ChatRoomデータのID検索
     *
     * @param roomId
     * @return
     */
    public ChatRoom findByRoomId(Long roomId) {
        return jpa.em().find(ChatRoom.class, roomId);
    }

    /**
     * ChatRoomの新規登録
     *
     * @param ChatRoom
     */
    public void registChatRoom(ChatRoom ChatRoom) {
        jpa.em().persist(ChatRoom);
    }

    /**
     * ChatRoomの論理削除
     *
     * @param ChatRoom
     */
    public void deleteChatRoom(ChatRoom ChatRoom) {
        ChatRoom.setStatus(false);
        jpa.em().merge(ChatRoom);
    }

    /**
     * ChatRoom情報の変更後
     *
     * @param ChatRoom
     */
    public void updateChatRoom(ChatRoom ChatRoom) {
        jpa.em().merge(ChatRoom);
    }
}
