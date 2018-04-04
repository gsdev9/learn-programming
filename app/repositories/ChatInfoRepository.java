package repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.ChatInfo;
import play.db.jpa.JPAApi;

import java.util.List;

/**
 * ChatInfoエンティティに対するCURD処理
 */
@Singleton
public class ChatInfoRepository {

    private final JPAApi jpa;

    @Inject
    public ChatInfoRepository(JPAApi jpa) {
        this.jpa = jpa;
    }


    /**
     * ChatInfoデータのID検索
     *
     * @param userId
     * @return
     */
    public ChatInfo find(Long userId) {
        return jpa.em().find(ChatInfo.class, userId);
    }

    /**
     * ChatInfoデータのチャットルームID検索
     *
     * @param chatRoomId
     * @return
     */
    public List<ChatInfo> findByChatRoomId(Long chatRoomId) {
        return jpa.em().createQuery("SELECT c FROM ChatInfo c WHERE c.chatRoomId=:chatRoomId", ChatInfo.class).setParameter("chatRoomId", chatRoomId).getResultList();
    }

    /**
     * ChatInfoの新規登録
     *
     * @param chatInfo
     */
    public void registchatInfo(ChatInfo chatInfo) {
        jpa.em().persist(chatInfo);
    }

    /**
     * ChatInfoの論理削除
     *
     * @param chatInfo
     */
    public void deletechatInfo(ChatInfo chatInfo) {
        jpa.em().remove(chatInfo);
    }

    /**
     * ChatInfo情報の変更後
     *
     * @param chatInfo
     */
    public void updatechatInfo(ChatInfo chatInfo) {
        jpa.em().merge(chatInfo);
    }
}
