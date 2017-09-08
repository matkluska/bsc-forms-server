package io.kluska.bsc.forms.form.service.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mateusz Kluska
 */
@RestController
@RequestMapping(path = "/forms")
public class FormController {
    @GetMapping
    public String echo() {
        return "echo";
    }
}
