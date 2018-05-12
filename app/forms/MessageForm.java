package forms;

import models.PurchasedTicket;

/**
 * メッセージのフォーム
 *
 * @author arapiku
 */
public class MessageForm {

    public String message;

    public PurchasedTicket purchasedTicket;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
