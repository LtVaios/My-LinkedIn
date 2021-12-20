package uoa.di.tedbackend.post_view;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import uoa.di.tedbackend.friends_impl.Friends;
import uoa.di.tedbackend.post_impl.PostRepository;
import uoa.di.tedbackend.user_impl.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@Slf4j
public class LoadPostViewDatabase {

    @Bean("PostViewDatabase")
    @DependsOn({"PostDatabase"})
    public CommandLineRunner initLikesDatabase(PostViewRepository repository,PostRepository prepository, UserRepository urepo) {
        repository.deleteAll();
        repository.save(new PostView(urepo,prepository,1,1));
        repository.save(new PostView(urepo,prepository,4,1));
        repository.save(new PostView(urepo,prepository,1,2));
        repository.save(new PostView(urepo,prepository,4,2));
        repository.save(new PostView(urepo,prepository,2,3));
        repository.save(new PostView(urepo,prepository,11,3));
        repository.save(new PostView(urepo,prepository,15,3));
        repository.save(new PostView(urepo,prepository,5,4));
        repository.save(new PostView(urepo,prepository,15,4));
        repository.save(new PostView(urepo,prepository,6,5));
        repository.save(new PostView(urepo,prepository,15,6));
        repository.save(new PostView(urepo,prepository,2,6));
        repository.save(new PostView(urepo,prepository,8,7));
        repository.save(new PostView(urepo,prepository,10,7));
        repository.save(new PostView(urepo,prepository,2,7));
        repository.save(new PostView(urepo,prepository,10,8));
        repository.save(new PostView(urepo,prepository,4,8));
        repository.save(new PostView(urepo,prepository,12,9));
        repository.save(new PostView(urepo,prepository,14,9));
        repository.save(new PostView(urepo,prepository,5,9));
        repository.save(new PostView(urepo,prepository,3,10));
        repository.save(new PostView(urepo,prepository,12,10));
        repository.save(new PostView(urepo,prepository,12,11));
        repository.save(new PostView(urepo,prepository,11,12));
        repository.save(new PostView(urepo,prepository,9,12));
        repository.save(new PostView(urepo,prepository,15,13));
        repository.save(new PostView(urepo,prepository,11,13));
        repository.save(new PostView(urepo,prepository,14,14));
        repository.save(new PostView(urepo,prepository,11,14));
        repository.save(new PostView(urepo,prepository,13,15));
        repository.save(new PostView(urepo,prepository,5,15));
        repository.save(new PostView(urepo,prepository,9,15));
        repository.save(new PostView(urepo,prepository,13,16));
        repository.save(new PostView(urepo,prepository,9,16));
        repository.save(new PostView(urepo,prepository,13,17));
        repository.save(new PostView(urepo,prepository,5,17));
        repository.save(new PostView(urepo,prepository,9,17));
        repository.save(new PostView(urepo,prepository,1,18));
        repository.save(new PostView(urepo,prepository,3,18));
        repository.save(new PostView(urepo,prepository,13,18));
        repository.save(new PostView(urepo,prepository,10,18));
        repository.save(new PostView(urepo,prepository,1,19));
        repository.save(new PostView(urepo,prepository,3,19));
        repository.save(new PostView(urepo,prepository,13,19));
        repository.save(new PostView(urepo,prepository,10,19));
        repository.save(new PostView(urepo,prepository,7,19));
        repository.save(new PostView(urepo,prepository,3,20));
        repository.save(new PostView(urepo,prepository,13,20));
        repository.save(new PostView(urepo,prepository,15,20));
        repository.save(new PostView(urepo,prepository,6,20));
        repository.save(new PostView(urepo,prepository,2,20));
        repository.save(new PostView(urepo,prepository,2,21));
        repository.save(new PostView(urepo,prepository,15,21));
        repository.save(new PostView(urepo,prepository,3,21));
        repository.save(new PostView(urepo,prepository,1,21));
        repository.save(new PostView(urepo,prepository,6,21));
        return args -> {
            log.info("Preloading post view database");
        };
    }
}
