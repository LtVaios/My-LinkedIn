package uoa.di.tedbackend.comment_impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import uoa.di.tedbackend.comment_impl.Comment;
import uoa.di.tedbackend.joblike_impl.JobLike;

@Repository
@Transactional(readOnly = true)
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Comment> findCommentsByPost(int postId) {
        Query query = entityManager.createQuery("SELECT c FROM Comment c WHERE c.post.id=?1");
        query.setParameter(1, postId);
        List<Comment> comments = query.getResultList();
        return comments;
    }

    @Override
    public List<Comment> findCommentsByUser(int userId) {
        Query query = entityManager.createQuery("SELECT c FROM Comment c WHERE c.user.id=?1");
        query.setParameter(1, userId);
        List<Comment> comments = query.getResultList();
        return comments;
    }

    @Override
    public List<Comment> findCommentsToUsersPosts(int userId){
        Query query = entityManager.createQuery(("SELECT c FROM Comment c WHERE c.post.user.id=?1")); //TODO add l.user.id!= userId to not show notifications for users's own likes
        query.setParameter(1, userId);
        List<Comment> comments = query.getResultList();
        return comments;
    }
}
