package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Message {

    /** メッセージID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long messageId;

    /** メッセージ */
    public String messageText;

    /** ユーザーID */
    public Long userId;

    /** 購入済みチケット情報 */
    /** ユーザー情報はこちら */
    @ManyToOne(optional = false)
    @JsonIgnore
    public PurchasedTicket purchasedTicket;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PurchasedTicket getPurchasedTicket() {
        return purchasedTicket;
    }

    public void setPurchasedTicket(PurchasedTicket purchasedTicket) {
        this.purchasedTicket = purchasedTicket;
    }
}
