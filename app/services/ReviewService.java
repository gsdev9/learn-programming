package services;

import models.UserReview;
import repositories.ReviewRepository;

import javax.inject.Inject;
import java.util.List;

public class ReviewService {

    @Inject
    private ReviewRepository reviewRepository;

    /**
     * reviewRepositoryのfindById()を呼び出す
     *
     * @param id
     * @return
     */
    public UserReview findById(Long id) {
        return reviewRepository.findByUserId(id);
    }

    /**
     * reviewRepositoryのfindByReviewId()を呼び出す
     *
     * @param purchasedTicketId
     * @return
     */
    public List<UserReview> findByPurchasedId(Long purchasedTicketId) {
        return reviewRepository.findByPurchasedId(purchasedTicketId);
    }


    /**
     * reviewRepositoryのfindByReviewId()を呼び出す
     *
     * @param purchasedTicketId
     * @return
     */
    //TODO:COUNTの返り値エラー解消ー終わったら消す
    public Long findByPurchasedIdNum(Long purchasedTicketId) {
        return reviewRepository.findByPurchasedIdNum(purchasedTicketId);
    }

    /**
     * reviewRepositoryのregistUser()を呼び出す
     *
     * @param UserReview
     */
    public void registUserReview(UserReview UserReview) {
        reviewRepository.registUserReview(UserReview);
    }

    /**
     * reviewRepositoryのdeleteUser()を呼び出す
     *
     * @param userID
     */
    public void deleteUserReview(Long userID) {
        UserReview UserReview = reviewRepository.findByUserId(userID);
        reviewRepository.deleteUserReview(UserReview);
    }

}

