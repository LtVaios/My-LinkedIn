package uoa.di.tedbackend.joblike_impl;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uoa.di.tedbackend.likes_impl.Likes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JobLikeRepositoryCustomImpl implements JobLikeRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<JobLike> findJobLikesByPost(int postId) {
        Query query = entityManager.createQuery("SELECT l FROM JobLike l WHERE l.job.id=?1");
        query.setParameter(1, postId);
        List<JobLike> likes = query.getResultList();
        return likes;
    }

    @Override
    public List<JobLike> findJobLikesByUser(int userId) {
        Query query = entityManager.createQuery("SELECT l FROM JobLike l WHERE l.user.id=?1");
        query.setParameter(1, userId);
        List<JobLike> likes = query.getResultList();
        return likes;
    }

    @Override
    public List<JobLike> findLikesToUsersJobs(int userId){
        Query query = entityManager.createQuery(("SELECT l FROM JobLike l WHERE l.job.user.id=?1 and l.user.id<>?2"));
        query.setParameter(1, userId);
        query.setParameter(2, userId);
        List<JobLike> likes = query.getResultList();
        return likes;
    }
}
