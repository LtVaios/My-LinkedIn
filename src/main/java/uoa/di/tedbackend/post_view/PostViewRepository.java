package uoa.di.tedbackend.post_view;

import org.springframework.data.jpa.repository.JpaRepository;
import uoa.di.tedbackend.likes_impl.Likes;
import uoa.di.tedbackend.likes_impl.LikesRepositoryCustom;

public interface PostViewRepository extends JpaRepository<PostView, Integer>, PostViewRepositoryCustom {
}

