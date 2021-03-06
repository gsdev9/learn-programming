package models;

import javax.persistence.*;

@Entity
public class UserReview {

    /** レビューID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long reviewId;

    /** コメント */
    public String comment;

    /** 評価 */
    public Integer evaluation;

    /** オーナー情報はこちら */
    @ManyToOne(optional = false)
    public Ticket ticket;

    /**
     * チケット購入ID
     */
    public Long purchasedTicketId;

    /** 購入者情報 */
    @ManyToOne(optional = false)
    public User buyer;

    /**
     * チケット情報
     */
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Long getPurchasedTicketId() {
        return purchasedTicketId;
    }

    public void setPurchasedTicketId(Long purchasedTicketId) {
        this.purchasedTicketId = purchasedTicketId;
    }

}
