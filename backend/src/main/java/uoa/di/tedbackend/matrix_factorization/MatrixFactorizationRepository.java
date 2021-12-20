package uoa.di.tedbackend.matrix_factorization;

import org.springframework.data.jpa.repository.JpaRepository;
import uoa.di.tedbackend.job_impl.Job;

public interface MatrixFactorizationRepository extends JpaRepository<Job, Integer>{
}