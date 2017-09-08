package io.kluska.bsc.forms.auth.service.api;

import io.kluska.bsc.forms.auth.service.api.dto.UserDTO;
import io.kluska.bsc.forms.auth.service.api.exception.ClientErrorException;
import io.kluska.bsc.forms.auth.service.api.exception.ErrorInfo;
import io.kluska.bsc.forms.auth.service.domain.User;
import io.kluska.bsc.forms.auth.service.domain.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public UserDTO getUser(@NonNull Principal principal) {
        return userService.findByUsername(principal.getName())
                .map(UserController::toUserDTO)
                .orElseThrow(() -> new ClientErrorException(ErrorInfo.USER_NOT_FOUND));
//        return UserDTO.builder()
//                .username(user.getUsername())
//                .email(user.getEmail())
//                .build();
    }

    @RequestMapping(method = RequestMethod.POST)
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
