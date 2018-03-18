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

    /** チケット情報 */
    /** オーナー情報はこちら */
    @ManyToOne(optional = false)
    public Ticket ticket;

    /** 購入者情報 */
    @ManyToOne(optional = false)
    public User buyer;

}
