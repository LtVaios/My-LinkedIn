package uoa.di.tedbackend.comment_impl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>, CommentRepositoryCustom{
}

