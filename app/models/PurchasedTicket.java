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

}
