package uoa.di.tedbackend.joblike_impl;

import uoa.di.tedbackend.likes_impl.Likes;

import java.util.List;

public interface JobLikeRepositoryCustom {
    List<JobLike> findJobLikesByPost(int postId);
    List<JobLike> findJobLikesByUser(int userId);
}
