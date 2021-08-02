package uoa.di.tedbackend.user_impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findByUsername(String username) {
        User user = null;
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = ?1");
        query.setParameter(1, username);
        List<User> users = query.getResultList();
        if (users != null && users.size() > 0)
            user = users.get(0);
        return user;
    }

    @Override
    public List<User> findByFullname(String name) {
        String[] splited=null;
        List<User> users=new ArrayList<>();
        if (name.contains(" ")){
            splited=name.split(" ");
        }
        if(splited!=null) {
            for (String str : splited) {
                Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.firstName = ?1 OR u.lastName = ?2");
                query.setParameter(1, str);
                query.setParameter(2, str);
                users.addAll(query.getResultList());
            }
        }
        else{
            Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.firstName = ?1 OR u.lastName = ?2");
            query.setParameter(1, name);
            query.setParameter(2, name);
            users=query.getResultList();
        }
        return users;
    }
}
