package io.kluska.bsc.forms.reply.stats.service.domain.service;

import io.kluska.bsc.forms.form.api.dto.FormDTO;
import io.kluska.bsc.forms.form.api.dto.QuestionDTO;
import io.kluska.bsc.forms.reply.stats.service.api.client.FormClient;
import io.kluska.bsc.forms.reply.stats.service.api.exception.InconsistentFormIdsException;
import io.kluska.bsc.forms.reply.stats.service.api.exception.LackOfRequiredReplyException;
import io.kluska.bsc.forms.reply.stats.service.api.exception.NotDefinedQuestionException;
import io.kluska.bsc.forms.reply.stats.service.domain.model.Reply;
import io.kluska.bsc.forms.reply.stats.service.domain.repository.ReplyRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReplyService {
    @NonNull
    private final ReplyRepository repository;
    @NonNull
    private final FormStatsCalculationService statsCalculationService;
    @NonNull
    private final FormClient formClient;

    public void addReplies(Set<Reply> replies, String formId) {
        FormDTO formDTO = formClient.findFormById(formId);
        validateReplies(replies, formDTO);
        repository.save(replies);

        statsCalculationService.calcAndSaveFormStats(formId, formDTO);
    }

    private static void validateReplies(Set<Reply> replies, FormDTO formDTO) {
        checkFormIdsConsistency(replies, formDTO);
        checkIfRequiredRepliesArePresent(replies, formDTO);
        checkBelongingToFormAndValidateRepliesType(replies, formDTO);
    }

    private static void checkFormIdsConsistency(Set<Reply> replies, FormDTO formDTO) {
        boolean hasDifferentFormId = replies.stream()
                .anyMatch(r -> !r.getFormId().equalsIgnoreCase(formDTO.getId()));
        if (hasDifferentFormId)
            throw new InconsistentFormIdsException();
    }

    private static void checkIfRequiredRepliesArePresent(Set<Reply> replies, FormDTO formDTO) {
        List<String> requiredQuestionIds = formDTO.getQuestions().stream()
                .filter(QuestionDTO::isRequired)
                .map(QuestionDTO::getId)
                .collect(Collectors.toList());

        List<String> repliesQuestionsIds = replies.stream()
                .map(Reply::getQuestionId)
                .collect(Collectors.toList());

        requiredQuestionIds.forEach(questionId -> {
            if (!repliesQuestionsIds.contains(questionId))
                throw new LackOfRequiredReplyException("Lack of required reply for question with id: " + questionId);
        });
    }

    private static void checkBelongingToFormAndValidateRepliesType(Set<Reply> replies, FormDTO formDTO) {
        Map<String, QuestionDTO> formQuestionTypeMap = formDTO.getQuestions().stream()
                .collect(Collectors.toMap(QuestionDTO::getId, Function.identity()));

        replies.forEach(reply -> {
            if (!formQuestionTypeMap.containsKey(reply.getQuestionId()))
                throw new NotDefinedQuestionException("Question " + reply.getQuestionId() + " is not defined in form schema.");
            reply.validate(formQuestionTypeMap.get(reply.getQuestionId()));
        });
    }
}
