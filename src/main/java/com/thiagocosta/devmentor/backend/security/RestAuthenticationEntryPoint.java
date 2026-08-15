package com.thiagocosta.devmentor.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagocosta.devmentor.backend.dto.response.ErrorResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.*;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper;
    public RestAuthenticationEntryPoint(ObjectMapper mapper){this.mapper=mapper;}
    @Override public void commence(HttpServletRequest req,HttpServletResponse res,AuthenticationException e)throws IOException{
        res.setStatus(401);res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(res.getOutputStream(),new ErrorResponseDTO(401,"UNAUTHORIZED","Autenticação necessária",req.getRequestURI(),null));
    }
}
