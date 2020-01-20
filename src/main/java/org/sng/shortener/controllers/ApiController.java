package org.sng.shortener.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sng.shortener.exceptions.*;
import org.sng.shortener.json.*;
import org.sng.shortener.services.AccountService;
import org.sng.shortener.services.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static org.sng.shortener.controllers.ParamNames.REDIRECT_TYPE;
import static org.sng.shortener.controllers.ParamNames.URL;
import static org.sng.shortener.controllers.PathMappings.*;
import static org.sng.shortener.exceptions.BusinessError.SYSTEM_ERROR;

@RestController
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private AccountService accounts;

    @Autowired
    private ShortenerService shortener;

    @RequestMapping(value = ACCOUNT, method = RequestMethod.POST)
    public ResponseBase openAccount(@RequestParam(value="accountId") String accountId) throws AccountAlreadyExists {
        String password = accounts.register(accountId);
        return new AccountOkResponse(password);
    }

    @RequestMapping(value = REGISTER, method = RequestMethod.POST)
    public RegisterUrlResponse registerUrl(
            @RequestParam(value = URL) String url,
            @RequestParam(value = REDIRECT_TYPE, defaultValue = "302") int redirectType) throws InvalidRedirectType, InvalidLongUrlException {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();
        return new RegisterUrlResponse(shortener.shorten(url, redirectType, accountId));
    }

    @RequestMapping(value = STATISTIC +"/{accountId}", method = RequestMethod.GET)
    public GetStatisticResponse getStats(@PathVariable String accountId) throws AccessDenied {
        String realAccountId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!realAccountId.equals(accountId)) {
            throw new AccessDenied();
        }
        return new GetStatisticResponse(shortener.getStats(realAccountId));
    }

    @ExceptionHandler(BusinessFastException.class)
    public ErrorResponse handleBusinessException(BusinessFastException e, HttpServletResponse response) {
        BusinessError be = e.getBusinessError();
        response.setStatus(be.getHttpStatus().value());
        return new ErrorResponse(be, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleSystemException(Exception e) {
        String desc = SYSTEM_ERROR.format(e.toString().hashCode()); // hide details from user
        logger.error(desc, e);
        return new ErrorResponse(SYSTEM_ERROR, desc);
    }

}