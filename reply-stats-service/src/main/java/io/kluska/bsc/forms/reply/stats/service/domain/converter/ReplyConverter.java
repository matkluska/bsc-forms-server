package io.kluska.bsc.forms.reply.stats.service.domain.converter;

import io.kluska.bsc.forms.reply.stats.service.api.dto.ReplyDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.replies.LinearScaleReplyDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.replies.LongTextReplyDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.replies.MultipleChoiceReplyDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.replies.ShortTextReplyDTO;
import io.kluska.bsc.forms.reply.stats.service.api.dto.replies.SingleChoiceReplyDTO;
import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.replies.LinearScaleReply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.replies.LongTextReply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.replies.MultipleChoiceReply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.replies.ShortTextReply;
import io.kluska.bsc.forms.reply.stats.service.domain.model.replies.SingleChoiceReply;
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
public class ReplyConverter implements Function<Reply, ReplyDTO> {
    @NonNull
    private final ModelMapper modelMapper;

    @Override
    public ReplyDTO apply(@NonNull Reply reply) {
        return Match(reply).of(
                Case($(instanceOf(ShortTextReply.class)), () -> modelMapper.map(reply, ShortTextReplyDTO.class)),
                Case($(instanceOf(LongTextReply.class)), () -> modelMapper.map(reply, LongTextReplyDTO.class)),
                Case($(instanceOf(SingleChoiceReply.class)), () -> modelMapper.map(reply, SingleChoiceReplyDTO.class)),
                Case($(instanceOf(MultipleChoiceReply.class)), () -> modelMapper.map(reply, MultipleChoiceReplyDTO.class)),
                Case($(instanceOf(LinearScaleReply.class)), () -> modelMapper.map(reply, LinearScaleReplyDTO.class)),
                Case($(), () -> {
                    throw new RuntimeException("Unsupported reply type");
                }));
    }
}
