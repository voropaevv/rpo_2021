package ru.iu3.rpo.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iu3.rpo.backend.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByToken(String token); // аутентификация пользователя
    Optional<User> findByLogin(String login); // поиск пользователя при входе
}