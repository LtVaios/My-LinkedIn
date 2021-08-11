package uoa.di.tedbackend.joblike_impl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobLikeRepository extends JpaRepository<JobLike, Integer>, JobLikeRepositoryCustom{
}
