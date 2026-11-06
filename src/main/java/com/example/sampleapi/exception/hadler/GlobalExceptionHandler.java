package com.example.sampleapi.exception.hadler;

import com.example.sampleapi.exception.NoteArgumentNotValidException;
import com.example.sampleapi.exception.NoteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


    @RestControllerAdvice
    public class GlobalExceptionHandler {


        @ExceptionHandler(NoteNotFoundException.class)
        public ResponseEntity<String> handleNoteNotFoundException(NoteNotFoundException e) {
//        return ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }
        @ExceptionHandler(NoteArgumentNotValidException.class)
        public ResponseEntity<String> handNoteArgumentNotValidException(NoteArgumentNotValidException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
