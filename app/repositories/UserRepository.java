package repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.User;
import play.Logger;
import play.db.jpa.JPAApi;
import play.libs.Json;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Userエンティティに対するCURD処理
 *
 * @author arapiku
 */
@Singleton
public class UserRepository {

    private final JPAApi jpa;

    @Inject
    public UserRepository(JPAApi jpa) {
        this.jpa = jpa;
    }

    /**
     * Userテーブルの全件検索
     *
     * @return
     */
    public List<User> findAll() {
        return jpa.em().createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    /**
     * ログインのためのユーザー名とパスワード照合
     *
     * @param userName
     * @param password
     * @return
     */
    public User findByUserNameAndPassword(String userName, String password) {
        try {
            return jpa.em().createQuery("SELECT u FROM User u WHERE u.userName = :username AND u.password = :password", User.class)
                .setParameter("username", userName)
                .setParameter("password", password)
                .getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }

    /**
     * UserデータのID検索
     *
     * @param id
     * @return
     */
    public User findById(Long id) {
        return jpa.em().find(User.class, id);
    }

    /**
     * ユーザーの新規登録
     *
     * @param user
     */
    public void registUser(User user) {
        jpa.em().persist(user);
        Logger.debug("ユーザーが登録されました： {}", Json.toJson(user));
    }

    /**
     * ユーザーの論理削除
     *
     * @param user
     */
    public void deleteUser(User user) {
        jpa.em().remove(user);
        Logger.debug("ユーザーが削除されました： {}", Json.toJson(user));
    }

    /**
     * ユーザー情報の変更後
     *
     * @param user
     */
    public void updateUser(User user) {
        jpa.em().merge(user);
        Logger.debug("ユーザー情報が変更されました： {}", Json.toJson(user));
    }
}
