package uoa.di.tedbackend.likes_impl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Integer>,LikesRepositoryCustom{
}

