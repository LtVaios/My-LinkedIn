package uoa.di.tedbackend.comment_impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import uoa.di.tedbackend.likes_impl.Likes;
import uoa.di.tedbackend.post_impl.PostRepository;
import uoa.di.tedbackend.user_impl.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@Slf4j
public class LoadCommentsDatabase {

    @Bean("CommentDatabase")
    @DependsOn({"PostDatabase"})
    public CommandLineRunner initPostDatabase(CommentRepository repository, PostRepository prepo, UserRepository urepo) {
        repository.deleteAll();
        repository.save(new Comment(prepo,urepo,"You too!",1,1));
        repository.save(new Comment(prepo,urepo,"Same!",4,1));
        repository.save(new Comment(prepo,urepo,"you too.",7,1));
        repository.save(new Comment(prepo,urepo,"Good job.",1,2));
        repository.save(new Comment(prepo,urepo,"Congrats!",4,2));
        repository.save(new Comment(prepo,urepo,"nice.",7,2));
        repository.save(new Comment(prepo,urepo,"You too!",4,4));
        repository.save(new Comment(prepo,urepo,"Have a nice day!",7,4));
        repository.save(new Comment(prepo,urepo,"And a very creative one!",4,4));
        repository.save(new Comment(prepo,urepo,"And a creative month.",7,4));
        repository.save(new Comment(prepo,urepo,"I can agree.",2,3));
        repository.save(new Comment(prepo,urepo,"Im happy for you.",6,5));
        repository.save(new Comment(prepo,urepo,"Same...",15,6));
        repository.save(new Comment(prepo,urepo,"All good!",10,8));
        repository.save(new Comment(prepo,urepo,"Welcome!",12,9));
        repository.save(new Comment(prepo,urepo,"Welcome!",14,9));
        repository.save(new Comment(prepo,urepo,"Welcome!",5,9));
        repository.save(new Comment(prepo,urepo,"hahahaha",3,10));
        repository.save(new Comment(prepo,urepo,"Good luck!",12,11));
        repository.save(new Comment(prepo,urepo,"Godd luck for the new season.",9,12));
        repository.save(new Comment(prepo,urepo,"Very nice my friend.",11,12));
        repository.save(new Comment(prepo,urepo,"True",15,13));
        repository.save(new Comment(prepo,urepo,"You too!",14,14));
        repository.save(new Comment(prepo,urepo,"Oof..",5,15));
        repository.save(new Comment(prepo,urepo,"New York too!",13,16));
        repository.save(new Comment(prepo,urepo,"But nothing like Greece.",9,16));
        repository.save(new Comment(prepo,urepo,"Oh no...",5,17));
        repository.save(new Comment(prepo,urepo,"I don't think so...",1,18));
        repository.save(new Comment(prepo,urepo,"I agree haha",10,18));
        repository.save(new Comment(prepo,urepo,"I do!",7,19));
        repository.save(new Comment(prepo,urepo,"Everybody",13,19));
        repository.save(new Comment(prepo,urepo,"Finally!",6,20));
        repository.save(new Comment(prepo,urepo,"Yesss.",2,20));
        repository.save(new Comment(prepo,urepo,"Nice!",3,21));
        repository.save(new Comment(prepo,urepo,"Noooo :(",1,22));
        return args -> {
            log.info("Preloading comments database");
        };
    }
}
