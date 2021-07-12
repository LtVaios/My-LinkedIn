package uoa.di.tedbackend.user_impl;

import uoa.di.tedbackend.user_impl.User;

public interface UserRepositoryCustom {
    User findByUsername(String username);
}
