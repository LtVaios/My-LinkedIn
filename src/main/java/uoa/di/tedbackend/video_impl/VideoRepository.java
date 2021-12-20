package uoa.di.tedbackend.video_impl;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
