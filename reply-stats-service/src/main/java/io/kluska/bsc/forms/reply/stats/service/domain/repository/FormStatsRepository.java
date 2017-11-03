package io.kluska.bsc.forms.reply.stats.service.domain.repository;

import io.kluska.bsc.forms.reply.stats.service.domain.model.FormStats;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author Mateusz Kluska
 */
public interface FormStatsRepository extends MongoRepository<FormStats, String> {
    Optional<FormStats> findOneByFormId(String formId);
}
