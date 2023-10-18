package br.com.dellist.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//Anotação para toda exceção que tiver passar por aqui.
@ControllerAdvice
public class ExceptionHandlerController {
@ExceptionHandler(HttpMessageNotReadableException.class)

    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
//validacao e pesquisar sobre o getmostespicifCause, ajuda para exibir na tela apenas o erro de 50 caracteres da taskModel.
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());
    }
}
