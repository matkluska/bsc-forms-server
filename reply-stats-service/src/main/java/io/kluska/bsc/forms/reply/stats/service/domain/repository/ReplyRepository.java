package io.kluska.bsc.forms.reply.stats.service.domain.repository;

import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mateusz Kluska
 */
@Repository
public interface ReplyRepository extends MongoRepository<Reply, String> {
}
