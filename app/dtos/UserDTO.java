package dtos;

import models.User;
import play.data.Form;
import play.data.validation.Constraints;

import java.util.Objects;

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

    /**
     * admin画面のユーザー更新DTO
     *
     * @param user
     * @param form
     * @return
     */
    public User adminUserDto(User user, Form<User> form) {
        User f = form.get();
        user.userName = (Objects.nonNull(f.userName)) ? f.userName : user.userName;
        user.nickName = (Objects.nonNull(f.nickName)) ? f.nickName : user.nickName;
        user.email = (Objects.nonNull(f.email)) ? f.email : user.email;
        return user;
    }
}
