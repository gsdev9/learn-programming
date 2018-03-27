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

    /** アクセストークン */
    public String UUID;

    @Column(unique = true)
    /** ユーザー名 */
    public String userName;

    /** ニックネーム */
    public String nickName;

    /** メールアドレス */
    public String email;

    /** パスワード */
    public String password;

    /** サムネイルパス */
    public String thumbnailPath;

    /** ユーザー削除スーテタス */
    public Boolean deletedStatus;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardMaxAgeMonth() {
        return cardMaxAgeMonth;
    }

    public void setCardMaxAgeMonth(String cardMaxAgeMonth) {
        this.cardMaxAgeMonth = cardMaxAgeMonth;
    }

    public String getCardMaxAgeYear() {
        return cardMaxAgeYear;
    }

    public void setCardMaxAgeYear(String cardMaxAgeYear) {
        this.cardMaxAgeYear = cardMaxAgeYear;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Integer getCardCVC() {
        return cardCVC;
    }

    public void setCardCVC(Integer cardCVC) {
        this.cardCVC = cardCVC;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<UserReview> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<UserReview> userReviews) {
        this.userReviews = userReviews;
    }
}
