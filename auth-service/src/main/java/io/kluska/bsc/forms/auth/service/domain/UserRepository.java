package io.kluska.bsc.forms.auth.service.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Mateusz Kluska
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findOneByUsername(String username);

    Optional<User> findOneByEmail(String email);
}

