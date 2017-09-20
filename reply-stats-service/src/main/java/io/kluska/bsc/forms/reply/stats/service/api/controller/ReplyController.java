package io.kluska.bsc.forms.reply.stats.service.api.controller;

import io.kluska.bsc.forms.form.api.dto.FormDTO;
import io.kluska.bsc.forms.reply.stats.service.api.client.FormClient;
import io.kluska.bsc.forms.reply.stats.service.api.dto.ReplyDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.converter.ReplyDTOConverter;
import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import io.kluska.bsc.forms.reply.stats.service.domain.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final FormClient formClient;
    private final ReplyService replyService;
    private final ReplyDTOConverter replyDTOConverter;

    @GetMapping(path = "/{id}")
    public FormDTO getForm(@PathVariable String id) {
        return formClient.findFormById(id);
    }

    @PostMapping(path = "/{formId}")
    public void saveReply(@PathVariable String formId, @RequestBody Set<ReplyDTO> replyDtoSet) {
        Set<Reply> replies = replyDtoSet.stream()
                .map(reply -> replyDTOConverter.apply(reply, formId))
                .collect(Collectors.toSet());

        replyService.addReplies(replies, formId);
    }
}
