package uoa.di.tedbackend.job_impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import uoa.di.tedbackend.user_impl.UserRepository;

@Configuration
@Slf4j
public class LoadJobDatabase {

    @Bean("JobDatabase")
    @DependsOn({"UserDatabase"})
    public CommandLineRunner initJobDatabase(JobRepository repository, UserRepository urepo) {
        //repository.deleteAll();
        repository.save(new Job(urepo,"Currently looking for any android or ios apps that need developing. You know where you can find me.",1,1));
        repository.save(new Job(urepo,".NET full-stack developer looking for full-time job within USA",2,2));
        repository.save(new Job(urepo,"New job openings at E-bay. All job positions open",15,3));
        repository.save(new Job(urepo,"Senior software developer looking for job. Hit me up!",3,4));
        repository.save(new Job(urepo,"My business plan for 2021 is ready, let me know if you want some info.",5,5));
        repository.save(new Job(urepo,"Taxes are going up, sent me a message for further info",6,6));
        repository.save(new Job(urepo,"Looking for a full-stack developer to help me with my new finance app.",6,7));
        repository.save(new Job(urepo,"Anyone in need for some 3D software?",8,8));
        repository.save(new Job(urepo,"I also take care of free-lancing graphic design jobs",10,9));
        repository.save(new Job(urepo,"We need a graphic designer assistant in amazon",10,10));
        repository.save(new Job(urepo,"I need a math teacher assistant to care of my class while I am at a business trip.",11,11));
        repository.save(new Job(urepo,"Any mechanics here? Open job at NYC",12,12));
        repository.save(new Job(urepo,"Marketing will change dramatically during 2022. In case you need any analysis...",13,13));
        repository.save(new Job(urepo,"I offer great market plans at zero price!",13,14));
        repository.save(new Job(urepo,"Is there any business here that wants taxes and economy analysis for the new season?",14,15));
        return args -> {
            log.info("Preloading jobs database");
        };
    }
}
