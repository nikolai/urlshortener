package org.sng.shortener.controllers;

import org.sng.shortener.exceptions.RedirectionNotFoundException;
import org.sng.shortener.model.Redirection;
import org.sng.shortener.services.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RedirectController {

    @Autowired
    private ShortenerService shortener;

    @RequestMapping(value = "/{shortKey}", method=RequestMethod.GET)
    public RedirectView redirect(@PathVariable String shortKey, HttpServletResponse httpResponse) throws RedirectionNotFoundException {
        Redirection redirection = shortener.find(shortKey);
        if (redirection != null) {
            httpResponse.setStatus(redirection.getRedirectType());
            httpResponse.setHeader("Location", redirection.getLongUrl());
            httpResponse.setHeader("Connection", "close");
            return null;
        }
        throw new RedirectionNotFoundException();
    }
}
