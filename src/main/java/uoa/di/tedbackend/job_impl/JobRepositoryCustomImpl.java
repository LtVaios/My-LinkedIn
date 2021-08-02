//package uoa.di.tedbackend.job_impl;
//
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import uoa.di.tedbackend.friends_impl.Friends;
//import uoa.di.tedbackend.user_impl.User;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import java.util.List;
//
//@Repository
//@Transactional(readOnly = true)
//public class JobRepositoryCustomImpl implements JobRepositoryCustom{
//    @PersistenceContext
//    EntityManager entityManager;
//
//    @Override
//    public List<Job> findPostByUserOrderById(User user){
//        Query query = entityManager.createQuery("SELECT j FROM Job j WHERE (j.user = ?1)");
//        query.setParameter(1, user);
//        List<Job> _jobs = query.getResultList();
//        return _jobs;
//    }
//
//    @Override
//    List<Job> findAllByOrderByIdDesc();
//}
