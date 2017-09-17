package io.kluska.bsc.forms.reply.stats.service.api.client;

import io.kluska.bsc.forms.form.api.dto.FormDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Mateusz Kluska
 */
@FeignClient(name = "form-service")
public interface FormClient {
    @GetMapping(path = "/forms/{id}")
    FormDTO findFormById(@PathVariable(name = "id") String id);
}
