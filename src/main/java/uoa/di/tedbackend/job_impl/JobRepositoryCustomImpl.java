package uoa.di.tedbackend.job_impl;

import org.apache.lucene.search.FuzzyQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uoa.di.tedbackend.friends_impl.Friends;
import uoa.di.tedbackend.user_impl.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JobRepositoryCustomImpl implements JobRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Job> findJobByUser(User myuser){
        Query query = entityManager.createQuery("SELECT j FROM Job j WHERE (j.user.id = ?1)");
        query.setParameter(1, myuser.getId());
        return (List<Job>) query.getResultList();
    }

    @Override
    public List<Job> getJobBasedOnWord(String word){
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);

        QueryBuilder qb = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Job.class)
                .get();

        FuzzyQuery foodQuery = (FuzzyQuery) qb.keyword().fuzzy().withEditDistanceUpTo(2)
                .onFields("body")
                .matching(word)
                .createQuery();

        FullTextQuery fullTextQuery = fullTextEntityManager
                .createFullTextQuery((org.apache.lucene.search.Query) foodQuery, Job.class);
        return (List<Job>) fullTextQuery.getResultList();
    }
}
