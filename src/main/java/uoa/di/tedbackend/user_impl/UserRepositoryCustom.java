package uoa.di.tedbackend.user_impl;
import java.util.List;
import uoa.di.tedbackend.user_impl.User;

public interface UserRepositoryCustom {
    User findByUsername(String username);
    List<User> findByFullname(String name);
}
