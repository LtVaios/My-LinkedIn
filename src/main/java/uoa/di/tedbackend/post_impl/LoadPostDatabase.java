package uoa.di.tedbackend.post_impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import uoa.di.tedbackend.user_impl.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@Slf4j
public class LoadPostDatabase {

    @Bean("PostDatabase")
    @DependsOn({"UserDatabase"})
    public CommandLineRunner initPostDatabase(PostRepository repository, UserRepository urepo) {
        //repository.deleteAll();
        repository.save(new Post(urepo,"Everybody have a nice weekend!",2,1));
        repository.save(new Post(urepo,"My .NET project is officially done!",2,2));
        repository.save(new Post(urepo,"M.I.T is the best university",3,3));
        repository.save(new Post(urepo,"Good morning everyone!",6,4));
        repository.save(new Post(urepo,"Just finished my business strategy meeting it was one the best ones.",5,5));
        repository.save(new Post(urepo,"Rough day at work today...",7,6));
        repository.save(new Post(urepo,"3D animations are the best",8,7));
        repository.save(new Post(urepo,"How are you doing today everybody?",8,8));
        repository.save(new Post(urepo,"Hello im new to this platform!",9,9));
        repository.save(new Post(urepo,"I love math!",11,10));
        repository.save(new Post(urepo,"School season begins...",11,11));
        repository.save(new Post(urepo,"Aircraft maintenance for September is done!",12,12));
        repository.save(new Post(urepo,"Marketing and consumer research can be tricky sometimes",13,13));
        repository.save(new Post(urepo,"Good luck everybody for the new season!",13,14));
        repository.save(new Post(urepo,"Taxes are bigger than you think",14,15));
        repository.save(new Post(urepo,"San Francisco is so pretty",14,16));
        repository.save(new Post(urepo,"New season new taxes, be prepared hahaha!",14,17));
        repository.save(new Post(urepo,"E-bay > Amazon",15,18));
        repository.save(new Post(urepo,"Who loves E-bay?",15,19));
        repository.save(new Post(urepo,"Job positions at E-bay will open soon, stay tuned!",15,20));
        repository.save(new Post(urepo,"Finally free so I can take care of any new android app ideas",1,21));
        repository.save(new Post(urepo,"Holidays are over :(",4,22));
        return args -> {
            log.info("Preloading posts database");
        };
    }
}
