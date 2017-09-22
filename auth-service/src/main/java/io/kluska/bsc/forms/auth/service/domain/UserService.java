package io.kluska.bsc.forms.auth.service.domain;

import io.kluska.bsc.forms.auth.service.api.exception.EmailAlreadyUsedException;
import io.kluska.bsc.forms.auth.service.api.exception.UsernameAlreadyUsedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Mateusz Kluska
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final UserRepository repository;

    public Optional<User> findByUsername(String username) {
        return repository.findOneByUsername(username);
    }

    public void create(User user) {

        Optional<User> existing = repository.findOneByUsername(user.getUsername());
        if (existing.isPresent())
            throw new UsernameAlreadyUsedException("User: " + user.getUsername() + " already exists");
        existing = repository.findOneByEmail(user.getEmail());
        if (existing.isPresent())
            throw new EmailAlreadyUsedException("Email: " + user.getEmail() + " already used");

        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);

        repository.save(user);

        log.info("new user has been created: {}", user.getUsername());
    }
}
