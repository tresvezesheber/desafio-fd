package br.com.hebio.desafiofd.exception;

public class EmailAlreadyExistsException extends RuntimeException{

        public EmailAlreadyExistsException(String message) {
            super(message);
        }
}
