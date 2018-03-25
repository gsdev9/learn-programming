package dtos;

import models.User;
import play.data.validation.Constraints;

/**
 * ユーザー情報をサービスに渡す
 *
 * @author arapiku
 */
public class UserDTO {

    @Constraints.Required
    public String userName;

    @Constraints.Required
    public String nickName;

    @Constraints.Required
    public String email;

    @Constraints.Required
    public String thumbnailPath;

    public User duplicateUserInfo(String userName, String nickName, String email, String password, String thumbnailPath) {
        User user = new User();
        user.userName = userName;
        user.nickName = nickName;
        user.email = email;
        user.password = password;
        user.thumbnailPath = thumbnailPath;
        return user;
    }
}
