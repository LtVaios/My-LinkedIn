package uoa.di.tedbackend.image;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uoa.di.tedbackend.friends_impl.Friends;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ImageRepositoryCustomImpl  implements ImageRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Image> findPostImages(int post_id){
        Query query = entityManager.createQuery("SELECT img FROM Image img WHERE img.post.id=?1");
        query.setParameter(1, post_id);
        List<Image> images = query.getResultList();
        return images;
    }
}
