package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class ChatRoom {

    /** チャットルームID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long chatRoomId;

    /** 入室状況 */
    public boolean status;

    /** 購入済みチケット */
    @OneToOne(mappedBy = "chatRoom")
    @JsonIgnore
    public PurchasedTicket purchasedTicket;

    /** チャットコンテンツ */
    @OneToOne(mappedBy = "chatRoom")
    @JsonIgnore
    public ChatContent chatContent;


    public Long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public PurchasedTicket getPurchasedTicket() {
        return purchasedTicket;
    }

    public void setPurchasedTicket(PurchasedTicket purchasedTicket) {
        this.purchasedTicket = purchasedTicket;
    }

    public ChatContent getChatContent() {
        return chatContent;
    }

    public void setChatContent(ChatContent chatContent) {
        this.chatContent = chatContent;
    }
}
