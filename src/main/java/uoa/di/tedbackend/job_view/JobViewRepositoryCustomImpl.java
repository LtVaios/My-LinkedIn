package uoa.di.tedbackend.job_view;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uoa.di.tedbackend.job_view.JobView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JobViewRepositoryCustomImpl implements JobViewRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<JobView> findJobViewsByJob(int jobId) {
        Query query = entityManager.createQuery("SELECT l FROM JobView l WHERE l.job.id=?1");
        query.setParameter(1, jobId);
        List<JobView> views = query.getResultList();
        return views;
    }

    @Override
    public List<JobView> findJobViewsByUser(int userId) {
        Query query = entityManager.createQuery("SELECT l FROM JobView l WHERE l.user.id=?1");
        query.setParameter(1, userId);
        List<JobView> views = query.getResultList();
        return views;
    }

    @Override
    public List<JobView> findJobViewsToUsersJobs(int userId){
        Query query = entityManager.createQuery(("SELECT l FROM Likes l WHERE l.job.user.id=?1")); //TODO add l.user.id!= userId to not show notifications for users's own likes
        query.setParameter(1, userId);
        List<JobView> views = query.getResultList();
        return views;
    }
}
