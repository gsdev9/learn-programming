package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

/**
 * チケット情報
 *
 * @author arapiku
 */
@Entity
public class Ticket {

    /** チケットID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long ticketId;

    /** チケット内容 */
    public String body;

    /** 授業開始時間 */
    public Date startTime;

    /** 授業終了時間 */
    public Date endTime;

    /** 価格 */
    public Integer price;

    /** ユーザー情報 */
    @ManyToOne(optional = false)
    public User user;

    /** レビュー情報 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
    @JsonIgnore
    public List<UserReview> userReviews;

    /** チケット情報 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
    @JsonIgnore
    public List<PurchasedTicket> purchasedTickets;

}
