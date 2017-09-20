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
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

/**
 * @author Mateusz Kluska
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ReplyDTOConverter implements BiFunction<ReplyDTO, String, Reply> {
    @NonNull
    private final ModelMapper modelMapper;

    @Override
    public Reply apply(@NonNull ReplyDTO replyDTO, @NonNull String formId) {
        Reply reply = Match(replyDTO).of(
                Case($(instanceOf(ShortTextReplyDTO.class)), () -> modelMapper.map(replyDTO, ShortTextReply.class)),
                Case($(instanceOf(LongTextReplyDTO.class)), () -> modelMapper.map(replyDTO, LongTextReply.class)),
                Case($(instanceOf(SingleChoiceReplyDTO.class)), () -> modelMapper.map(replyDTO, SingleChoiceReply.class)),
                Case($(instanceOf(MultipleChoiceReplyDTO.class)), () -> modelMapper.map(replyDTO, MultipleChoiceReply.class)),
                Case($(instanceOf(LinearScaleReplyDTO.class)), () -> modelMapper.map(replyDTO, LinearScaleReply.class)),
                Case($(), () -> {
                    throw new RuntimeException("Unsupported reply type");
                }));
        reply.setFormId(formId);
        return reply;
    }
}
