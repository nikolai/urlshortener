package org.sng.shortener.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static org.sng.shortener.controllers.PathMappings.HELP;

@Controller
public class HelpController {

    // inject via application.properties
    @Value("${help.message:test}")
    private String message = "Hello World";

    @RequestMapping(HELP)
    public String welcome(Map<String, Object> model) {
        model.put("message", this.message);
        return "help";
    }

}
