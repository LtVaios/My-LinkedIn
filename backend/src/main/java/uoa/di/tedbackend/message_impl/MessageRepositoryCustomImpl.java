package uoa.di.tedbackend.message_impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import uoa.di.tedbackend.comment_impl.Comment;

@Repository
@Transactional(readOnly = true)
public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Message> findMessagesByUser(int id) {
        Query query = entityManager.createQuery("SELECT m FROM Message m WHERE m.receiver_id = ?1 OR m.sender_id = ?2");
        query.setParameter(1, id);
        query.setParameter(2, id);
        List<Message> messages = query.getResultList();
        return messages;
    }

}
