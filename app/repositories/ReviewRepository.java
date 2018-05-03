package repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.UserReview;
import play.db.jpa.JPAApi;

import java.util.List;

@Singleton
public class ReviewRepository {

    private final JPAApi jpa;

    @Inject
    public ReviewRepository(JPAApi jpa) {
        this.jpa = jpa;
    }


    /**
     * UserReviewデータのID検索
     *
     * @param reviewId
     * @return
     */
    public UserReview findByUserId(Long reviewId) {
        return jpa.em().find(UserReview.class, reviewId);
    }

    /**
     * UserReviewデータの購入ID検索
     *
     * @param purchasedTicketId
     * @return
     */
    public List<UserReview> findByPurchasedId(Long purchasedTicketId) {
        return jpa.em().createQuery("SELECT u FROM  UserReview u WHERE u.purchasedTicketId = :purchasedTicketId", UserReview.class)
                .setParameter("purchasedTicketId", purchasedTicketId)
                .getResultList();
    }

    /**
     * UserReviewデータの購入ID検索
     *
     * @param purchasedTicketId
     * @return
     */

    //TODO:COUNTの返り値エラー解消ー終わったら消す
    public Long findByPurchasedIdNum(Long purchasedTicketId) {
        return jpa.em().createQuery("SELECT COUNT(u) FROM  UserReview u WHERE u.purchasedTicketId = :purchasedTicketId", Long.TYPE)
                .setParameter("purchasedTicketId", purchasedTicketId)
                .getSingleResult();
    }

    /**
     * UserReviewの新規登録
     *
     * @param UserReview
     */
    public void registUserReview(UserReview UserReview) {
        jpa.em().persist(UserReview);
    }

    /**
     * UserReviewの論理削除
     *
     * @param UserReview
     */
    public void deleteUserReview(UserReview UserReview) {
        jpa.em().remove(UserReview);
    }

    /**
     * UserReview情報の変更後
     *
     * @param UserReview
     */
    public void updateUserReview(UserReview UserReview) {
        jpa.em().merge(UserReview);
    }

}
