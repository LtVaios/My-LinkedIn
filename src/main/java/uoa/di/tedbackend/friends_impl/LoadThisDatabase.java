package uoa.di.tedbackend.friends_impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadThisDatabase {

    @Bean
    CommandLineRunner initThisDatabase(FriendsRepository repository) {
        repository.deleteAll();
        return args -> {
            log.info("Preloading " + repository.save(new Friends(1,1,3,"completed")));
            log.info("Preloading " + repository.save(new Friends(1,1,2,"completed")));
        };
    }
}
