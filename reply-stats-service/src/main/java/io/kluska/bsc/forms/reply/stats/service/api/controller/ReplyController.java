package io.kluska.bsc.forms.reply.stats.service.api.controller;

import io.kluska.bsc.forms.form.api.dto.FormDTO;
import io.kluska.bsc.forms.reply.stats.service.api.client.FormClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mateusz Kluska
 */
@RestController
@RequestMapping(path = "/replies")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReplyController {
    private final FormClient formClient;

    @GetMapping(path = "/{id}")
    public FormDTO getForm(@PathVariable String id) {
        return formClient.findFormById(id);
    }

    @PostMapping(path = "/{formId}")
    public String saveReply(@PathVariable String formId) {
        return formId;
    }
}
