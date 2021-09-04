package uoa.di.tedbackend.post_view;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PostViewRepositoryCustomImpl implements PostViewRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<PostView> findPostViewsByPost(int postId) {
        Query query = entityManager.createQuery("SELECT l FROM PostView l WHERE l.post.id=?1");
        query.setParameter(1, postId);
        List<PostView> views = query.getResultList();
        return views;
    }

    @Override
    public List<PostView> findPostViewsByUser(int userId) {
        Query query = entityManager.createQuery("SELECT l FROM PostView l WHERE l.user.id=?1");
        query.setParameter(1, userId);
        List<PostView> views = query.getResultList();
        return views;
    }

    @Override
    public List<PostView> findPostViewsToUsersPosts(int userId){
        Query query = entityManager.createQuery(("SELECT l FROM Likes l WHERE l.post.user.id=?1")); //TODO add l.user.id!= userId to not show notifications for users's own likes
        query.setParameter(1, userId);
        List<PostView> views = query.getResultList();
        return views;
    }
}
