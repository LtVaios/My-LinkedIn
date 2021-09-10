package uoa.di.tedbackend.application_impl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Integer>, ApplicationRepositoryCustom{
}
