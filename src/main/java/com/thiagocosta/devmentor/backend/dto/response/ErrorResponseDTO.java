package com.thiagocosta.devmentor.backend.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponseDTO {
    private final LocalDateTime timestamp=LocalDateTime.now(); private final int status; private final String error;
    private final String message; private final String path; private final Map<String,String> fields;
    public ErrorResponseDTO(int status,String error,String message,String path,Map<String,String> fields){this.status=status;
        this.error=error;this.message=message;this.path=path;this.fields=fields;}
    public LocalDateTime getTimestamp(){return timestamp;} public int getStatus(){return status;} public String getError(){return error;}
    public String getMessage(){return message;} public String getPath(){return path;} public Map<String,String> getFields(){return fields;}
}
