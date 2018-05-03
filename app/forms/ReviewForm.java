package forms;

import play.data.validation.Constraints;

import javax.validation.constraints.NotNull;

public class ReviewForm {
    /**
     * コメント
     */
    @Constraints.Required(message = "コメントが入力されていません。")
    public String comment;

    /**
     * 評価
     */
    @NotNull
    @Constraints.Required(message = "評価が入力されていません。")
    public Integer score;

    /**
     * 購入チケット
     */
    public Long purchasedTicketId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getPurchasedTicketId() {
        return purchasedTicketId;
    }

    public void setPurchasedTicketId(Long purchasedTicketId) {
        this.purchasedTicketId = purchasedTicketId;
    }
}
