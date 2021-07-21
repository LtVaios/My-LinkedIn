package uoa.di.tedbackend.user_impl;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom  {
}

