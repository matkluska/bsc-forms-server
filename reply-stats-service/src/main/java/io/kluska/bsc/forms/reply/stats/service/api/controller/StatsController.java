package io.kluska.bsc.forms.reply.stats.service.api.controller;

import io.kluska.bsc.forms.reply.stats.service.api.dto.FormStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.converter.FormStatsConverter;
import io.kluska.bsc.forms.reply.stats.service.domain.service.StatsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mateusz Kluska
 */
@RestController
@RequestMapping(path = "/stats")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatsController {
    @NonNull
    private final StatsService statsService;
    @NonNull
    private final FormStatsConverter formStatsConverter;

    @GetMapping(path = "/{id}")
    public FormStatsDTO getFormStats(@PathVariable String id) {
        return formStatsConverter.apply(statsService.getFormStats(id));
    }
}
