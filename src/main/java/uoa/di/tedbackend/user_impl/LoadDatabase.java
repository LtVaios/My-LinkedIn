package uoa.di.tedbackend.user_impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        repository.deleteAll();
        return args -> {
            log.info("Preloading " + repository.save(new User(1,"bilbo@gmail.com","1234","Bilbo", "Baggins", "1234",false)));
            log.info("Preloading " + repository.save(new User(2,"sam@gmail.com","4321","Sam", "Baggins", "4321",false)));
            log.info("Preloading " + repository.save(new User(3,"frodo@gmail.com","5678","Frodo", "Baggins", "5678",true)));
        };
    }
}
