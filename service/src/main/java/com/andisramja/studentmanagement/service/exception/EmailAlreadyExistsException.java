package com.andisramja.studentmanagement.service.exception;

/**
 * Përjashtim që hidhet kur tentohet të regjistrohet ose përditësohet një student
 * me një adresë email që ekziston tashmë në sistem.
 * <p>
 * Kapet nga trajtuesi global i gabimeve dhe kthehet si përgjigje HTTP 409 (Conflict).
 * </p>
 *
 * @author Andis Ramja
 */
public class EmailAlreadyExistsException extends RuntimeException {

    /**
     * Krijon përjashtimin me një mesazh përshkrues.
     *
     * @param message mesazhi i gabimit në gjuhën shqipe
     */
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
