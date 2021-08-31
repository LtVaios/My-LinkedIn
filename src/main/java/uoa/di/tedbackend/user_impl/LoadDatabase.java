package uoa.di.tedbackend.user_impl;

import lombok.extern.slf4j.Slf4j;

import java.lang.Math;
import org.ejml.simple.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        //repository.deleteAll();
//        this.MF();
        return args -> {
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            log.info("Preloading " + repository.save(new User(1,"bilbo@gmail.com",bCryptPasswordEncoder.encode("1234"),"Bilbo", "Baggins", "1234",false)));
            log.info("Preloading " + repository.save(new User(4,"bob@gmail.com",bCryptPasswordEncoder.encode("1234"),"Bob", "Baggins", "1234",false)));
            log.info("Preloading " + repository.save(new User(2,"sam@gmail.com",bCryptPasswordEncoder.encode("4321"),"Sam", "Baggins", "4321",false)));
            log.info("Preloading " + repository.save(new User(3,"frodo@gmail.com",bCryptPasswordEncoder.encode("5678"),"Frodo", "Baggins", "5678",true)));
        };
    }


}
