package uoa.di.tedbackend.matrix_factorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import uoa.di.tedbackend.comment_impl.Comment;
import uoa.di.tedbackend.comment_impl.CommentRepository;
import uoa.di.tedbackend.post_impl.PostRepository;
import uoa.di.tedbackend.user_impl.UserRepository;

@Configuration
@Slf4j
public class LoadMatrixFactorization {

    @Bean("MatrixFactorizationInit")
    @DependsOn({"LikesDatabase", "CommentDatabase", "joblikeDatabase"})
    public CommandLineRunner initMatrixFactorization(matrix_factorization mf) {
        mf.mf_posts();
        mf.mf_jobs();
        return args -> {
            log.info("Initialized matrix factorization");
        };
    }
}
