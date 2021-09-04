package uoa.di.tedbackend.job_view;

import org.springframework.data.jpa.repository.JpaRepository;
import uoa.di.tedbackend.likes_impl.Likes;
import uoa.di.tedbackend.likes_impl.LikesRepositoryCustom;
import uoa.di.tedbackend.job_view.JobView;
import uoa.di.tedbackend.job_view.JobViewRepositoryCustom;

public interface JobViewRepository extends JpaRepository<JobView, Integer>, JobViewRepositoryCustom {
}

