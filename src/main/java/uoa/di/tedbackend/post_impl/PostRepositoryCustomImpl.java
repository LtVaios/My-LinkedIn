package uoa.di.tedbackend.post_impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import uoa.di.tedbackend.comment_impl.Comment;

@Repository
@Transactional(readOnly = true)
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Post> findByUser(String username) {
        Post post = null;
        Query query = entityManager.createQuery("SELECT p FROM Job p WHERE p.user_id = ?1");
        query.setParameter(1, username);
        List<Post> posts = query.getResultList();
        return posts;
    }

    @Override
    public List<Comment> get_comms(int pid) {
        Comment comment = null;
        Query query = entityManager.createQuery("SELECT c FROM Comment c WHERE c.post_id = ?1");
        query.setParameter(1, pid);
        List<Comment> comments = query.getResultList();
        return comments;
    }
}
