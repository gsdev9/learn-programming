package services;

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
     * userRepositoryのregistUser()を呼び出す
     *
     * @param user
     */
    public void registUser(User user) {
        userRepository.registUser(user);
    }
}
