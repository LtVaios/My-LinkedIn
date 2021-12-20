package uoa.di.tedbackend.message_impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import uoa.di.tedbackend.user_impl.UserRepository;

import java.util.Date;

@Configuration
@Slf4j
public class LoadMessageDatabase {

    @Bean("MessagesDatabase")
    @DependsOn({"UserDatabase"})
    public CommandLineRunner initMessageDatabase(MessageRepository repository) {
        long date=System.currentTimeMillis();
        repository.save(new Message(date,"Hello",1,2));
        repository.save(new Message(date,"Hi, how are you?",2,1));
        repository.save(new Message(date,"All good my friend have a nice day!",1,2));
        repository.save(new Message(date,"Hello!",4,1));
        repository.save(new Message(date,"Hi, what's up",1,4));
        repository.save(new Message(date,"I have an android app that needs developing for my company. Do you wanna get the job?",4,1));
        repository.save(new Message(date,"Of course, send me the info via e-mail.",1,4));
        repository.save(new Message(date,"Hello jamie, I love your work keep on.",1,15));
        repository.save(new Message(date,"Thank you, I appreciate it.",15,1));
        repository.save(new Message(date,"Hi are you still available for that android app development?",7,1));
        repository.save(new Message(date,"Oh no I'm really sorry I already got hired for a job.",1,7));
        repository.save(new Message(date,"That's fine! Good luck my friend.",7,1));
        return args -> {
            log.info("Preloading messages database");
        };
    }
}
