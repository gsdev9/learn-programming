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
        return reviewRepository.find(id);
    }

    /**
     * reviewRepositoryのfindByReviewId()を呼び出す
     *
     * @param reviewId
     * @return
     */
    public List<UserReview> findByChatRoomId(Long reviewId) {
        return reviewRepository.findByReviewId(reviewId);
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
        UserReview UserReview = reviewRepository.find(userID);
        reviewRepository.deleteUserReview(UserReview);
    }

}

