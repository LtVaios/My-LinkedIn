package uoa.di.tedbackend.post_impl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer>, PostRepositoryCustom  {
}

