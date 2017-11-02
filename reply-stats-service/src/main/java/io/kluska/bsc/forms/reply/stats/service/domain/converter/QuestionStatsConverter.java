package io.kluska.bsc.forms.reply.stats.service.domain.converter;

import io.kluska.bsc.forms.reply.stats.service.api.dto.QuestionStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.stats.LinearScaleStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.stats.LongTextStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.stats.MultipleChoiceStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.stats.ShortTextStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.stats.SingleChoiceStatsDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.model.QuestionStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.LinearScaleStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.LongTextStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.MultipleChoiceStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.ShortTextStats;
import io.kluska.bsc.forms.reply.stats.service.domain.model.stats.SingleChoiceStats;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

/**
 * @author Mateusz Kluska
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionStatsConverter implements Function<QuestionStats, QuestionStatsDTO> {
    @NonNull
    private final ModelMapper modelMapper;

    @Override
    public QuestionStatsDTO apply(QuestionStats qStats) {
        return Match(qStats).of(
                Case($(instanceOf(ShortTextStats.class)), () -> modelMapper.map(qStats, ShortTextStatsDTO.class)),
                Case($(instanceOf(LongTextStats.class)), () -> modelMapper.map(qStats, LongTextStatsDTO.class)),
                Case($(instanceOf(SingleChoiceStats.class)), () -> modelMapper.map(qStats, SingleChoiceStatsDTO.class)),
                Case($(instanceOf(MultipleChoiceStats.class)), () -> modelMapper.map(qStats, MultipleChoiceStatsDTO.class)),
                Case($(instanceOf(LinearScaleStats.class)), () -> modelMapper.map(qStats, LinearScaleStatsDTO.class)),
                Case($(), () -> {
                    throw new RuntimeException("Unsupported question stats type");
                }));
    }
}