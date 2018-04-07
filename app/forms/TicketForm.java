package forms;

import play.data.validation.Constraints;

import javax.validation.constraints.NotNull;

public class TicketForm {

    /** チケットタイトル */
    @NotNull
    @Constraints.Required(message = "タイトルが入力されていません。")
    public String title;

    /** チケット内容 */
    @NotNull
    @Constraints.Required(message = "内容が入力されていません。")
    public String body;

    /** 授業日 */
    @NotNull
    @Constraints.Required(message = "授業日が入力されていません。")
    @Constraints.Pattern(value = "\\d{4}-\\d{2}-\\d{2}", message = "日付を入力してください")
    public String date;

    /** 授業開始時間 */
    @NotNull
    @Constraints.Required(message = "授業開始時間が入力されていません。")
    @Constraints.Pattern(value = "\\d{2}:\\d{2}", message = "時間を入力してください")
    public String startAt;

    /** 授業終了時間 */
    @NotNull
    @Constraints.Required(message = "授業終了時間が入力されていません。")
    @Constraints.Pattern(value = "\\d{2}:\\d{2}", message = "時間を入力してください")
    public String endAt;

    /** 価格 */
    @NotNull
    @Constraints.Required(message = "金額が入力されていません。")
    public Integer price;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}