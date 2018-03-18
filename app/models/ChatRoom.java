package models;

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
    public PurchasedTicket purchasedTicket;

    /** チャットコンテンツ */
    @OneToOne(mappedBy = "chatRoom")
    public ChatContent chatContent;

}
