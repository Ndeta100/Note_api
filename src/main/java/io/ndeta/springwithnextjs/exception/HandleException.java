package io.ndeta.springwithnextjs.exception;

import io.ndeta.springwithnextjs.domain.HttpResponse;
import io.ndeta.springwithnextjs.util.DateUtil;
import jakarta.annotation.Nullable;
import jakarta.persistence.NoResultException;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class HandleException extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode
                                                                              status, WebRequest request) {
       log.info(ex.getMessage());
        List<FieldError>fieldErrors=ex.getBindingResult().getFieldErrors();
        String fieldsMessage=fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" "));
        return new ResponseEntity<>(HttpResponse.builder()
                .reason("Invalid fields: " + fieldsMessage)
                .status((HttpStatus) status)
                .statusCode(status.value())
                .timeStamp(LocalDateTime.now().format(DateUtil.dateTimeFormatter()))
                .build(),status );
    }

    @Nullable
    protected  ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body,
                                                              HttpHeaders headers, HttpStatus status, WebRequest request){
        log.info(exception.getMessage());
        return new ResponseEntity<>(HttpResponse.builder()
                .reason(exception.getMessage())
                .status((HttpStatus) status)
                .statusCode(status.value())
                .timeStamp(LocalDateTime.now().format(DateUtil.dateTimeFormatter()))
                .build(),status );
    }
   @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<HttpResponse<?>> illegalStateException(IllegalStateException  exception){
        return  createHttpResponse(HttpStatus.BAD_REQUEST,exception.getMessage(), exception);
    }

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<HttpResponse<?>> noteNotFoundException(NoteNotFoundException exception){
        return  createHttpResponse( HttpStatus.BAD_REQUEST,exception.getMessage(), exception);
    }
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse<?>> noResultsException(NoResultException exception){
        return  createHttpResponse( HttpStatus.BAD_REQUEST,exception.getMessage(), exception);
    }
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<HttpResponse<?>> servletException(ServletException exception){
        return  createHttpResponse( HttpStatus.BAD_REQUEST,exception.getMessage(), exception);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse<?>> exception(Exception exception){
        return  createHttpResponse( HttpStatus.BAD_REQUEST,exception.getMessage(), exception);
    }
    private ResponseEntity<HttpResponse<?>> createHttpResponse(HttpStatus httpStatus,
            String reason, Exception exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(HttpResponse.builder()
                .reason(exception.getMessage())
                .status((HttpStatus) HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timeStamp(LocalDateTime.now().format(DateUtil.dateTimeFormatter()))
                .build(), HttpStatus.NOT_FOUND);

    }

}
