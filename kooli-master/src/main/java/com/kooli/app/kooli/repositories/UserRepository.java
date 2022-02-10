package com.kooli.app.kooli.repositories;

import java.util.List;
import java.util.Optional;

import com.kooli.app.kooli.models.RecordStatus;
import com.kooli.app.kooli.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findByCategoryAndStatus(long id, RecordStatus recordStatus);

    Optional<User> findByUsername(long username);

    Optional<User> findByUsernameOrEmailOrMobile(String username, String username2, String username3);

    Optional<User> findByMobile(String mobile);

    Optional<User> findByEmail(String email);

    List<User> findByTypeAndStatus(String type, RecordStatus active);

    List<User> findByType(String type);

}