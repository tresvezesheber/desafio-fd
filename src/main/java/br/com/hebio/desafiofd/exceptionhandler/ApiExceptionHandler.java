package br.com.hebio.desafiofd.exceptionhandler;

import br.com.hebio.desafiofd.exception.EmailAlreadyExistsException;
import br.com.hebio.desafiofd.exception.PasswordConfirmationException;
import br.com.hebio.desafiofd.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource mensagemFonte;

    private static final String BASEURL = "https://hebio.com.br";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("One or more fields are invalid");
        problemDetail.setType(URI.create(BASEURL + "/erros/invalid-fields"));

        Map<String, String> fields = ex.getBindingResult().getAllErrors()
                .stream()
                .collect(Collectors.toMap(objectError -> ((FieldError) objectError).getField(),
                        objectError -> mensagemFonte.getMessage(objectError, LocaleContextHolder.getLocale())));

        problemDetail.setProperty("fields", fields);

        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExist(EmailAlreadyExistsException e) {
        ProblemDetail detalheDoProblema = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detalheDoProblema.setTitle("Email already exist");
        detalheDoProblema.setDetail(e.getMessage());
        detalheDoProblema.setType(URI.create(BASEURL + "/erros/email-already-exist"));

        return detalheDoProblema;
    }

    @ExceptionHandler(PasswordConfirmationException.class)
    public ProblemDetail handlePPasswordConfirmation(PasswordConfirmationException e) {
        ProblemDetail detalheDoProblema = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detalheDoProblema.setTitle("The Passwords must be equals");
        detalheDoProblema.setDetail(e.getMessage());
        detalheDoProblema.setType(URI.create(BASEURL + "/erros/passwords-must-be-equals"));

        return detalheDoProblema;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFound(UserNotFoundException e) {
        ProblemDetail detalheDoProblema = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detalheDoProblema.setTitle("User not found");
        detalheDoProblema.setDetail(e.getMessage());
        detalheDoProblema.setType(URI.create(BASEURL + "/erros/user-not-found"));

        return detalheDoProblema;
    }
}
