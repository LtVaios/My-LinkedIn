package uoa.di.tedbackend.message_impl;

import org.springframework.data.jpa.repository.JpaRepository;

interface MessageRepository extends JpaRepository<Message, Integer>, MessageRepositoryCustom  {
}

