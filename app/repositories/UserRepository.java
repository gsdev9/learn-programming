package repositories;

import com.google.inject.*;
import models.User;
import play.Logger;
import play.db.jpa.JPAApi;
import play.libs.Json;

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

    public void registUser(User user) {
        jpa.em().persist(user);
        Logger.debug("ユーザーが登録されました： {}", Json.toJson(user));
    }
}
