package models;

import javax.persistence.*;

@Entity
public class Message {

    /** メッセージID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long messageId;

    /** メッセージ */
    public String message;

    /** ユーザーID */
    public Long userId;

    /** 購入済みチケット情報 */
    /** ユーザー情報はこちら */
    @ManyToOne(optional = false)
    public PurchasedTicket purchasedTicket;

}
