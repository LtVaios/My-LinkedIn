package uoa.di.tedbackend.application_impl;

import java.util.List;

public interface ApplicationRepositoryCustom {
    List<Application> findApplicationsByPost(int postId);
    List<Application> findApplicationsByUser(int userId);
    List<Application> findApplicationsToUsersJobs(int userId);
}
