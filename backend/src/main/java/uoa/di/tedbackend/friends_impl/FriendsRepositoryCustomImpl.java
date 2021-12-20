package uoa.di.tedbackend.friends_impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import uoa.di.tedbackend.comment_impl.Comment;
import uoa.di.tedbackend.post_impl.Post;

@Repository
@Transactional(readOnly = true)
public class FriendsRepositoryCustomImpl implements FriendsRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Friends> findFriendsOfUser(int id) {
        Query query = entityManager.createQuery("SELECT f FROM Friends f WHERE (f.user_one = ?1 OR f.user_two=?2) AND f.state='completed' ");
        query.setParameter(1, id);
        query.setParameter(2, id);
        List<Friends> friends_ = query.getResultList();
        return friends_;
    }

    @Override
    public List<Friends> findRequestsOfUser(int id) {
        Query query = entityManager.createQuery("SELECT f FROM Friends f WHERE (f.user_two=?1) AND f.state='pending'");
        query.setParameter(1, id);
        List<Friends> friends_ = query.getResultList();
        return friends_;
    }

    @Override
    public List<Friends> findFriendsAndRequestsOfUser(int id) {
        Query query = entityManager.createQuery("SELECT f FROM Friends f WHERE (f.user_one = ?1 OR f.user_two=?2)");
        query.setParameter(1, id);
        query.setParameter(2, id);
        List<Friends> friends_ = query.getResultList();
        return friends_;
    }

}
