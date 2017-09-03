package io.kluska.bsc.forms.auth.service.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mateusz Kluska
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

}

