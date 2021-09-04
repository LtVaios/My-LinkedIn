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
        repository.save(new Friends(1,1,2,"completed"));
        repository.save(new Friends(2,1,4,"completed"));
        repository.save(new Friends(3,2,3,"completed"));
        repository.save(new Friends(28,2,7,"completed"));
        repository.save(new Friends(4,2,4,"completed"));
        repository.save(new Friends(5,1,15,"completed"));
        repository.save(new Friends(6,3,15,"completed"));
        repository.save(new Friends(7,2,15,"completed"));
        repository.save(new Friends(8,5,6,"completed"));
        repository.save(new Friends(29,4,6,"completed"));
        repository.save(new Friends(9,6,15,"completed"));
        repository.save(new Friends(10,6,7,"completed"));
        repository.save(new Friends(11,7,15,"completed"));
        repository.save(new Friends(12,1,7,"completed"));
        repository.save(new Friends(13,8,2,"completed"));
        repository.save(new Friends(14,8,4,"completed"));
        repository.save(new Friends(15,9,15,"completed"));
        repository.save(new Friends(16,9,5,"completed"));
        repository.save(new Friends(17,8,10,"completed"));
        repository.save(new Friends(18,10,15,"completed"));
        repository.save(new Friends(19,10,2,"completed"));
        repository.save(new Friends(20,11,3,"completed"));
        repository.save(new Friends(21,11,12,"completed"));
        repository.save(new Friends(22,12,9,"completed"));
        repository.save(new Friends(23,13,15,"completed"));
        repository.save(new Friends(24,13,11,"completed"));
        repository.save(new Friends(25,14,13,"completed"));
        repository.save(new Friends(26,14,5,"completed"));
        repository.save(new Friends(27,14,9,"completed"));
        return args -> {
            log.info("Preloading friends database");
        };
    }
}
