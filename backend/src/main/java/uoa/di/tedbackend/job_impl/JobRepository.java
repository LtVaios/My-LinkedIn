package uoa.di.tedbackend.job_impl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer>, JobRepositoryCustom {
}