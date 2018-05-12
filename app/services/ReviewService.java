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
        return reviewRepository.findById(id);
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
     * reviewRepositoryのfindByTicketId()を呼び出す
     *
     * @param ticketId
     * @return
     */
    public List<UserReview> findByTicketId(Long ticketId) {
        return reviewRepository.findByTicketId(ticketId);
    }

    /**
     * reviewRepositoryのfindByUserId()を呼び出す
     *
     * @param userId
     * @return
     */
    public List<UserReview> findByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
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
     * @param reviewId
     */
    public void deleteUserReview(Long reviewId) {
        UserReview UserReview = reviewRepository.findById(reviewId);
        reviewRepository.deleteUserReview(UserReview);
    }

}

