package uoa.di.tedbackend.application_impl;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ApplicationRepositoryCustomImpl implements ApplicationRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Application> findApplicationsByPost(int postId) {
        Query query = entityManager.createQuery("SELECT l FROM Application l WHERE l.job.id=?1");
        query.setParameter(1, postId);
        List<Application> likes = query.getResultList();
        return likes;
    }

    @Override
    public List<Application> findApplicationsByUser(int userId) {
        Query query = entityManager.createQuery("SELECT l FROM Application l WHERE l.user.id=?1");
        query.setParameter(1, userId);
        List<Application> likes = query.getResultList();
        return likes;
    }

    @Override
    public List<Application> findApplicationsToUsersJobs(int userId){
        Query query = entityManager.createQuery("SELECT l FROM Application l WHERE l.job.user.id=?1 and l.user.id<>?2");
        query.setParameter(1, userId);
        query.setParameter(2, userId);
        List<Application> likes = query.getResultList();
        return likes;
    }
}
