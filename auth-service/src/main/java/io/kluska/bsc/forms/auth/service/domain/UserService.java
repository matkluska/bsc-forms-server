package io.kluska.bsc.forms.auth.service.domain;

import java.util.Optional;

/**
 * @author Mateusz Kluska
 */
public interface UserService {

    Optional<User> findByUsername(String username);

    void create(User user);

}

