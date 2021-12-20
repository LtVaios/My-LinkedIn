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
    public List<Post> findPostsByUser(int user_id) {
        Query query = entityManager.createQuery("SELECT p FROM Post p WHERE p.user.id=?1");
        query.setParameter(1, user_id);
        List<Post> posts = query.getResultList();
        return posts;
    }
}
