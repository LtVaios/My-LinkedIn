package uoa.di.tedbackend.job_view;

import java.util.List;

import uoa.di.tedbackend.likes_impl.Likes;
import uoa.di.tedbackend.job_impl.Job;
import uoa.di.tedbackend.comment_impl.Comment;
import uoa.di.tedbackend.job_view.JobView;

public interface JobViewRepositoryCustom {
    List<uoa.di.tedbackend.job_view.JobView> findJobViewsByJob(int jobId);
    List<uoa.di.tedbackend.job_view.JobView> findJobViewsByUser(int userId);
    List<JobView> findJobViewsToUsersJobs(int userId);
}
