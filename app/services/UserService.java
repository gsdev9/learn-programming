package services;

import dtos.UserDetailDTO;
import models.User;
import repositories.UserRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Userに関するサービス層
 *
 * @author arapiku
 */
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserDetailDTO userDetailDTO;

    /**
     * UserRepositoryのfindAll()を呼び出す
     *
     * @return
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * UserRepositoryのfindByUserNameAndPassword()を呼び出す
     * その際にuserNameとpasswordを引数として渡す
     *
     * @param userName
     * @param password
     * @return
     */
    public User findByUserNameAndPassword(String userName, String password) { return userRepository.findByUserNameAndPassword(userName, password); }

    /**
     * UserRepositoryのfindById()を呼び出す
     *
     * @param id
     * @return
     */
    public User findById(Long id) { return userRepository.findById(id); }

    /**
     * UserRepositoryのregistUser()を呼び出す
     *
     * @param user
     */
    public void registUser(User user) {
        userRepository.registUser(user);
    }

    /**
     * UserRepositoryのdeleteUser()を呼び出す
     *
     * @param user
     */
    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    /**
     * ユーザー情報編集の情報をDBへ反映する
     *
     * @param newuser
     */
    public void updateUser(User newuser) {
        User olduser = userRepository.findById(newuser.getUserId());
        newuser = userDetailDTO.oldToNew(olduser, newuser);
        userRepository.updateUser(newuser);
    }


    /**
     * UserIdをUserNameに変換する
     *
     * @param userid
     * @return
     */
    public String idPerserName(Long userid) {
        List<User> userList = findAll();
        String userName = null;
        if (userList.isEmpty()) {
            return null;
        } else {
            for (User user : userList) {
                if (user.getUserId().equals(userid)) {
                    userName = user.getUserName();
                }
            }
        }
        return userName;
    }
}
