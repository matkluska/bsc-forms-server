package io.kluska.bsc.forms.auth.service.api;

import io.kluska.bsc.forms.auth.service.api.dto.UserDTO;
import io.kluska.bsc.forms.auth.service.api.exception.UserNotFoundException;
import io.kluska.bsc.forms.auth.service.domain.model.User;
import io.kluska.bsc.forms.auth.service.domain.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author Mateusz Kluska
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/current")
    public UserDTO getUser(@NonNull Principal principal) {
        return userService.findByUsername(principal.getName())
                .map(UserController::toUserDTO)
                .orElseThrow(UserNotFoundException::new);
    }

    @PostMapping
    public void createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = new User();
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        userService.create(user);
    }

    private static UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
