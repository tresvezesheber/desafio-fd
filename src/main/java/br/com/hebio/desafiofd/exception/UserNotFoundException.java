package br.com.hebio.desafiofd.exception;

public class UserNotFoundException extends RuntimeException{

        public UserNotFoundException(String message) {
            super(message);
        }
}
