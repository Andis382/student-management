package com.andisramja.studentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Pika e nisjes së aplikacionit "Sistemi i Menaxhimit të Studentëve".
 * <p>
 * Anotacioni {@link SpringBootApplication} aktivizon auto-konfigurimin dhe
 * skanimin e komponentëve. Meqenëse kjo klasë ndodhet në paketën rrënjë
 * {@code com.andisramja.studentmanagement}, skanohen automatikisht të gjitha
 * nën-paketat e moduleve (model, repository, service, mapper, web, excel).
 * </p>
 *
 * @author Andis Ramja
 */
@SpringBootApplication
public class StudentManagementApplication {

    /**
     * Metoda kryesore që nis aplikacionin.
     *
     * @param args argumentet e linjës së komandës
     */
    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }
}
