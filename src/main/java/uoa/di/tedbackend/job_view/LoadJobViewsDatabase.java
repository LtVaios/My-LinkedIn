package uoa.di.tedbackend.job_view;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import uoa.di.tedbackend.job_impl.JobRepository;
import uoa.di.tedbackend.user_impl.UserRepository;

@Configuration
@Slf4j
public class LoadJobViewsDatabase {

    @Bean("jobViewDatabase")
    @DependsOn({"JobDatabase"})
    public CommandLineRunner initJobLikesDatabase(JobViewRepository repository, JobRepository jrepository, UserRepository urepo) {
        repository.deleteAll();
        repository.save(new JobView(urepo,jrepository,4,1));
        repository.save(new JobView(urepo,jrepository,15,1));
        repository.save(new JobView(urepo,jrepository,1,2));
        repository.save(new JobView(urepo,jrepository,4,2));
        repository.save(new JobView(urepo,jrepository,7,2));
        repository.save(new JobView(urepo,jrepository,6,3));
        repository.save(new JobView(urepo,jrepository,4,3));
        repository.save(new JobView(urepo,jrepository,7,3));
        repository.save(new JobView(urepo,jrepository,13,3));
        repository.save(new JobView(urepo,jrepository,10,3));
        repository.save(new JobView(urepo,jrepository,8,3));
        repository.save(new JobView(urepo,jrepository,2,4));
        repository.save(new JobView(urepo,jrepository,6,5));
        repository.save(new JobView(urepo,jrepository,9,5));
        repository.save(new JobView(urepo,jrepository,15,6));
        repository.save(new JobView(urepo,jrepository,5,6));
        repository.save(new JobView(urepo,jrepository,4,7));
        repository.save(new JobView(urepo,jrepository,7,7));
        repository.save(new JobView(urepo,jrepository,5,7));
        repository.save(new JobView(urepo,jrepository,10,8));
        repository.save(new JobView(urepo,jrepository,2,9));
        repository.save(new JobView(urepo,jrepository,15,9));
        repository.save(new JobView(urepo,jrepository,8,10));
        repository.save(new JobView(urepo,jrepository,3,11));
        repository.save(new JobView(urepo,jrepository,9,12));
        repository.save(new JobView(urepo,jrepository,15,13));
        repository.save(new JobView(urepo,jrepository,11,14));
        repository.save(new JobView(urepo,jrepository,15,14));
        repository.save(new JobView(urepo,jrepository,14,14));
        repository.save(new JobView(urepo,jrepository,13,15));
        repository.save(new JobView(urepo,jrepository,9,15));
        repository.save(new JobView(urepo,jrepository,14,15));
        return args -> {
            log.info("Preloading job views database");
        };
    }
}
