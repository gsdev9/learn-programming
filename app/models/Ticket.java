package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    /** チケットタイトル */
    public String title;

    /** チケット内容 */
    public String body;

    /** 授業開始時間 */
    public LocalDateTime startTime;

    /** 授業終了時間 */
    public LocalDateTime endTime;

    /** 価格 */
    public Integer price;

    /** ユーザー情報 */
    @ManyToOne(optional = false)
    public User user;

    /** レビュー情報 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
    @JsonIgnore
    private List<UserReview> userReviews;

    /** チケット情報 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
    @JsonIgnore
    private List<PurchasedTicket> purchasedTickets;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserReview> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<UserReview> userReviews) {
        this.userReviews = userReviews;
    }

    public List<PurchasedTicket> getPurchasedTickets() {
        return purchasedTickets;
    }

    public void setPurchasedTickets(List<PurchasedTicket> purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }
}
