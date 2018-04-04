package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ChatInfo {

    /**
     * UserID
     */
    @Id
    public Long userId;

    /**
     * PeerID
     */
    public String peerId;

    /**
     * チャットルームID
     */
    public Long chatRoomId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPeerId() {
        return peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
