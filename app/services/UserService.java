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
    public User findByUserNameAndPassword(String userName, String password) {
        return userRepository.findByUserNameAndPassword(userName, password);
    }

    /**
     * userRepositoryのregistUser()を呼び出す
     *
     * @param user
     */
    public void registUser(User user) {
        userRepository.registUser(user);
    }

    /**
     * ユーザー情報編集の情報をDBへ反映する
     *
     * @param newuser
     */
    public void updateUserDetail(User newuser) {
        User olduser = userRepository.findByUserId(newuser.getUserId());
        newuser = userDetailDTO.oldToNew(olduser, newuser);
        userRepository.updateUser(newuser);
    }

    /**
     * UserRepositoryのfindByUserName()を呼び出す
     *
     * @param userName
     */
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    /**
     * UserRepositoryのfindByUserId()を呼び出す
     *
     * @param userId
     */
    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }



}