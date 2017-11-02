package io.kluska.bsc.forms.reply.stats.service.api.controller;

import io.kluska.bsc.forms.reply.stats.service.api.dto.ReplyDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.converter.ReplyDTOConverter;
import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import io.kluska.bsc.forms.reply.stats.service.domain.service.ReplyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@RestController
@RequestMapping(path = "/replies")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReplyController {
    @NonNull
    private final ReplyService replyService;
    @NonNull
    private final ReplyDTOConverter replyDTOConverter;

    @PostMapping(path = "/{formId}")
    public void saveReply(@PathVariable String formId, @RequestBody Set<ReplyDTO> replyDtoSet) {
        Set<Reply> replies = replyDtoSet.stream()
                .map(reply -> replyDTOConverter.apply(reply, formId))
                .collect(Collectors.toSet());

        replyService.addReplies(replies, formId);
    }
}
