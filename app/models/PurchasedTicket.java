package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class PurchasedTicket {

    /** 購入済チケットID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long purchasedTicketId;

    /** 授業実施ステータス */
    public boolean status;

    /** 購入者情報 */
    @ManyToOne(optional = false)
    public User buyer;

    /** チケット情報 */
    /** オーナー情報、授業時間はこちら */
    @ManyToOne(optional = false)
    public Ticket ticket;

    /** メッセージ情報 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchasedTicket")
    @JsonIgnore
    public List<Message> message;

    /** チャットルーム */
    @OneToOne(cascade = CascadeType.ALL)
    public ChatRoom chatRoom;

    public Long getPurchasedTicketId() {
        return purchasedTicketId;
    }

    public void setPurchasedTicketId(Long purchasedTicketId) {
        this.purchasedTicketId = purchasedTicketId;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
