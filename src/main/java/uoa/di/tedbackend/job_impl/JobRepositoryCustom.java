package uoa.di.tedbackend.job_impl;

import java.util.List;
import uoa.di.tedbackend.user_impl.User;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepositoryCustom {
    List<Job> findJobByUserOrderById(User user);

    List<Job> findAllByOrderByIdDesc();
}
