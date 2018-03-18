package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * ユーザー情報
 *
 * @author arapiku
 */
@Entity
public class User {

    /** ユーザーID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long userId;

    /** ユーザー名 */
    public String name;

    /** メールアドレス */
    public String email;

    /** パスワード */
    public String password;

    /** サムネイルパス */
    public String thumbnailPass;

    /** クレジット番号 */
    public String cardNo;

    /** クレジット有効期限月 */
    public String cardMaxAgeMonth;

    /** クレジット有効期限年 */
    public String cardMaxAgeYear;

    /** クレジット名義 */
    public String cardName;

    /** クレジットセキュリテイNo */
    public Integer cardCVC;

    /** チケット情報 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    public List<Ticket> tickets;

    /** レビュー情報 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
    @JsonIgnore
    public List<UserReview> userReviews;

}
