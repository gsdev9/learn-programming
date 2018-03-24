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
    public String UUID;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String email;

    @Constraints.Required
    public String thumbnailPath;

    public User duplicateUserInfo(String UUID, String name, String email, String thumbnailPath) {
        User user = new User();
        user.UUID = UUID;
        user.name = name;
        user.email = email;
        user.thumbnailPath = thumbnailPath;
        return user;
    }
}
