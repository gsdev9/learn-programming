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

    public void registUser(User user) {
        userRepository.registUser(user);
    }
}
