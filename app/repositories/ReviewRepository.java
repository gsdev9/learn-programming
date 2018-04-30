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
     * @param userId
     * @return
     */
    public UserReview find(Long userId) {
        return jpa.em().find(UserReview.class, userId);
    }

    /**
     * UserReviewデータのチャットルームID検索
     *
     * @param reviewId
     * @return
     */
    public List<UserReview> findByReviewId(Long reviewId) {
        return jpa.em().createQuery("SELECT u FROM UserReview u WHERE u.reviewId=:reviewId", UserReview.class).setParameter("reviewId", reviewId).getResultList();
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
