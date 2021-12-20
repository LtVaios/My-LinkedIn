package uoa.di.tedbackend.job_impl;

import java.util.List;
import uoa.di.tedbackend.user_impl.User;

public interface JobRepositoryCustom {
    List<Job> findJobByUser(User user);
    List<Job> getJobBasedOnWord(String word);
}
