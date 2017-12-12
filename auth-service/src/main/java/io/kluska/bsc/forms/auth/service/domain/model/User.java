package io.kluska.bsc.forms.auth.service.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Mateusz Kluska
 */
@Document(collection = "users")
@Data
public class User implements UserDetails {

    @Id
    @NotNull
    @Size(min = 3, max = 15)
    private String username;

    @NotNull
    @Size(min = 6, max = 15)
    private String password;

    @NotNull
    @Email
    private String email;

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
