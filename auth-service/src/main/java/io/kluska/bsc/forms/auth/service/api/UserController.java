package io.kluska.bsc.forms.auth.service.api;

import io.kluska.bsc.forms.auth.service.api.dto.UserDTO;
import io.kluska.bsc.forms.auth.service.domain.User;
import io.kluska.bsc.forms.auth.service.domain.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public UserDTO getUser(@AuthenticationPrincipal User user) {
        if (user == null)
            return null;
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = new User();
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        userService.create(user);
    }
}
