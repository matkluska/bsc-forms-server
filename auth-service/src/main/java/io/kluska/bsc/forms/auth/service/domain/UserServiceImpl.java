package io.kluska.bsc.forms.auth.service.domain;

import io.kluska.bsc.forms.auth.service.api.exception.ClientErrorException;
import io.kluska.bsc.forms.auth.service.api.exception.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Mateusz Kluska
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findOneByUsername(username);
    }

    @Override
    public void create(User user) {

        Optional<User> existing = repository.findOneByUsername(user.getUsername());
        if (existing.isPresent()) {
            log.error("User: {} already exists", user.getUsername());
            throw new ClientErrorException(ErrorInfo.USERNAME_ALREADY_IN_USE);
        }

        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);

        repository.save(user);

        log.info("new user has been created: {}", user.getUsername());
    }
}
