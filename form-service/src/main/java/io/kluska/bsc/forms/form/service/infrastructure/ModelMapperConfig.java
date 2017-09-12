package io.kluska.bsc.forms.form.service.infrastructure;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mateusz Kluska
 */
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper standardModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
//        modelMapper.getConfiguration().setProvider((Provider<QuestionDTO>) provisionRequest -> {
//            if (provisionRequest instanceof ShortTextDTO)
//                return new ShortTextDTO();
//            else if ((provisionRequest instanceof LongTextDTO))
//                return new LongTextDTO();
//            else throw new IllegalStateException("Question type do not known");
//
//        });
//        modelMapper.createTypeMap(QuestionDTO.class, Question.class)
//                .include(ShortTextDTO.class, ShortText.class)
//                .include(LongTextDTO.class, LongText.class);
//        modelMapper.typeMap(ShortTextDTO.class, ShortText.class).setProvider(
//                request -> new ShortText()
//        );
//        modelMapper.typeMap(LongTextDTO.class, LongText.class).setProvider(
//                request -> new LongText()
//        );
//
//        modelMapper.createTypeMap(Question.class, QuestionDTO.class)
//                .include(ShortText.class, ShortTextDTO.class)
//                .include(LongText.class, LongTextDTO.class);
//        modelMapper.typeMap(ShortText.class, ShortTextDTO.class).setProvider(
//                request -> new ShortTextDTO()
//        );
//        modelMapper.typeMap(LongText.class, LongTextDTO.class).setProvider(
//                request -> new LongTextDTO()
//        );
        return modelMapper;
    }
}
