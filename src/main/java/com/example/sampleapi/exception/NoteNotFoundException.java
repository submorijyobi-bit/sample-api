package com.example.sampleapi.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(long id) {
        super("指定されたIDのノードが見つかりません: " + id);
    }
}
