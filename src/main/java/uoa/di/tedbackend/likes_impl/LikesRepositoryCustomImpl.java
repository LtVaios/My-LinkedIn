package uoa.di.tedbackend.likes_impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import uoa.di.tedbackend.comment_impl.Comment;

@Repository
@Transactional(readOnly = true)
public class LikesRepositoryCustomImpl implements LikesRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Likes> findLikesByPost(int postId) {
        Query query = entityManager.createQuery("SELECT l FROM Likes l WHERE l.post.id=?1");
        query.setParameter(1, postId);
        List<Likes> likes = query.getResultList();
        return likes;
    }

    @Override
    public List<Likes> findLikesByUser(int userId) {
        Query query = entityManager.createQuery("SELECT l FROM Likes l WHERE l.user.id=?1");
        query.setParameter(1, userId);
        List<Likes> likes = query.getResultList();
        return likes;
    }

    @Override
    public List<Likes> findLikesToUsersPosts(int userId){
        Query query = entityManager.createQuery(("SELECT l FROM Likes l WHERE l.post.user.id=?1 and l.user.id<>?2"));
        query.setParameter(1, userId);
        query.setParameter(2, userId);
        List<Likes> likes = query.getResultList();
        return likes;
    }
}
