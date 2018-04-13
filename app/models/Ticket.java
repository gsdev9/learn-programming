package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.*;
import java.util.List;

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
    private Long ticketId;

    /** チケットタイトル */
    private String title;

    /** チケット内容 */
    private String body;

    /** 授業日 */
    private LocalDate date;

    /** 授業開始時間 */
    private LocalTime startAt;

    /** 授業終了時間 */
    private LocalTime endAt;

    /** 価格 */
    private Integer price;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalTime endAt) {
        this.endAt = endAt;
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
