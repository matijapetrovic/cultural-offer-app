package cultureapp.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh::mm::ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private String debugMessage;
    private List<Object> subErrors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable ex) {
        this(status);
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this(status);
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public void addSubError(Object error) {
        if (subErrors == null)
            subErrors = new ArrayList<Object>();
        subErrors.add(error);
    }
}