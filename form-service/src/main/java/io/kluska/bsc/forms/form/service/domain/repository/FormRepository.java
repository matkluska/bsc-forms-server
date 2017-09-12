package io.kluska.bsc.forms.form.service.domain.repository;

import io.kluska.bsc.forms.form.service.domain.model.Form;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Mateusz Kluska
 */
@Repository
public interface FormRepository extends MongoRepository<Form, String> {

    Optional<Form> findOneById(String id);

    List<Form> findByUsername(String username);
}
