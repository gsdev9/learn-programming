package forms;

/**
 * 管理画面のログインフォーム
 *
 * @author arapiku
 */
public class AdminForm {

    /** ユーザー名 */
    public String userName;

    /** パスワード */
    public String password;

    // TODO UUID認証項目に入れる

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
