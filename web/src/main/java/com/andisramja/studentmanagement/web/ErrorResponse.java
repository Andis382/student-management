package com.andisramja.studentmanagement.web;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Struktura standarde e përgjigjes së gabimit që kthehet nga API-ja.
 * <p>
 * Fushat me vlerë {@code null} (p.sh. {@code validationErrors} kur nuk ka
 * gabime validimi) nuk përfshihen në përgjigjen JSON.
 * </p>
 *
 * @param timestamp        momenti kur ndodhi gabimi
 * @param status           kodi numerik i statusit HTTP
 * @param error            përshkrimi i shkurtër i gabimit
 * @param message          mesazhi shpjegues i gabimit
 * @param validationErrors harta e gabimeve sipas fushave (opsionale)
 * @author Andis Ramja
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> validationErrors
) {
}
