package com.andisramja.studentmanagement.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Objekti i Transferimit të të Dhënave (DTO) për entitetin Student.
 * <p>
 * Përdoret nga shtresa web për të shkëmbyer të dhëna me klientin, duke
 * mbajtur entitetin e bazës së të dhënave të izoluar nga API-ja publike.
 * Anotacionet e Bean Validation sigurojnë vlefshmërinë e të dhënave hyrëse.
 * </p>
 *
 * @author Andis Ramja
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    /** Identifikuesi i studentit; injorohet gjatë krijimit dhe plotësohet nga sistemi. */
    private Long id;

    /** Emri i studentit; nuk mund të jetë bosh. */
    @NotBlank(message = "Emri është i detyrueshëm")
    private String firstName;

    /** Mbiemri i studentit; nuk mund të jetë bosh. */
    @NotBlank(message = "Mbiemri është i detyrueshëm")
    private String lastName;

    /** Adresa e email-it; duhet të jetë e vlefshme dhe e detyrueshme. */
    @NotBlank(message = "Email-i është i detyrueshëm")
    @Email(message = "Email-i duhet të jetë në një format të vlefshëm")
    private String email;

    /** Data e lindjes; duhet të jetë një datë në të kaluarën. */
    @Past(message = "Datëlindja duhet të jetë një datë në të kaluarën")
    private LocalDate dateOfBirth;

    /** Data e regjistrimit në institucion. */
    private LocalDate enrollmentDate;

    /** Mesatarja e notave; duhet të jetë në intervalin 0.0 - 4.0. */
    @NotNull(message = "GPA është e detyrueshme")
    @DecimalMin(value = "0.0", message = "GPA nuk mund të jetë më e vogël se 0.0")
    @DecimalMax(value = "4.0", message = "GPA nuk mund të jetë më e madhe se 4.0")
    private Double gpa;

    /** Dega ose specializimi i studentit; nuk mund të jetë bosh. */
    @NotBlank(message = "Dega është e detyrueshme")
    private String major;
}
