package org.sng.shortener.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sng.shortener.json.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import static org.sng.shortener.exceptions.BusinessError.INVALID_AUTH;

@Component
public class RestAuthEntryPoint implements AuthenticationEntryPoint {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        response.setStatus(INVALID_AUTH.getHttpStatus().value());
        try (Writer w = response.getWriter()){
            mapper.writeValue(w, new ErrorResponse(INVALID_AUTH, INVALID_AUTH.format()));
        }
    }
}
