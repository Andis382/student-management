package com.andisramja.studentmanagement.service.exception;

/**
 * Përjashtim që hidhet kur një student i kërkuar nuk gjendet në sistem.
 * <p>
 * Kapet nga trajtuesi global i gabimeve dhe kthehet si përgjigje HTTP 404.
 * </p>
 *
 * @author Andis Ramja
 */
public class StudentNotFoundException extends RuntimeException {

    /**
     * Krijon përjashtimin me një mesazh përshkrues.
     *
     * @param message mesazhi i gabimit në gjuhën shqipe
     */
    public StudentNotFoundException(String message) {
        super(message);
    }
}
