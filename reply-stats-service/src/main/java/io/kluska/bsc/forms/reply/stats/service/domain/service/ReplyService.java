package io.kluska.bsc.forms.reply.stats.service.domain.service;

import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.ReplyRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Mateusz Kluska
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReplyService {
    @NonNull
    private final ReplyRepository repository;

    public void addReplies(Set<Reply> replies) {
        repository.save(replies);
    }
}
