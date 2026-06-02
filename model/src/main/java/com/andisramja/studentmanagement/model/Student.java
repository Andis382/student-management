package com.andisramja.studentmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entiteti që përfaqëson një student në sistemin e menaxhimit.
 * <p>
 * Kjo klasë mapohet në tabelën <strong>students</strong> të bazës së të dhënave.
 * Anotacionet e Lombok-ut gjenerojnë automatikisht metodat getter, setter,
 * konstruktorët dhe modelin Builder, duke shmangur kodin e përsëritur.
 * </p>
 *
 * @author Andis Ramja
 */
@Entity
@Table(
        name = "students",
        uniqueConstraints = @UniqueConstraint(name = "uk_student_email", columnNames = "email")
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    /** Identifikuesi unik i studentit, gjenerohet automatikisht nga baza e të dhënave. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Emri i studentit. */
    @Column(nullable = false)
    private String firstName;

    /** Mbiemri i studentit. */
    @Column(nullable = false)
    private String lastName;

    /** Adresa e email-it; duhet të jetë unike në të gjithë sistemin (shih @UniqueConstraint te @Table). */
    @Column(nullable = false)
    private String email;

    /** Data e lindjes së studentit. */
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    /** Data e regjistrimit të studentit në institucion. */
    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    /** Mesatarja e notave e studentit (Grade Point Average). */
    private Double gpa;

    /** Dega ose specializimi i studentit. */
    private String major;
}
