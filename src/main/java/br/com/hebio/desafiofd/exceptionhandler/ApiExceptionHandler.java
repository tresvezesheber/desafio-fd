package br.com.hebio.desafiofd.exceptionhandler;

import br.com.hebio.desafiofd.exception.EmailAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExist(EmailAlreadyExistsException e) {
        ProblemDetail detalheDoProblema = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detalheDoProblema.setTitle("Email already exist");
        detalheDoProblema.setDetail(e.getMessage());
        detalheDoProblema.setType(URI.create("https://hebio.com.br/erros/email-already-exist"));

        return detalheDoProblema;
    }
}
