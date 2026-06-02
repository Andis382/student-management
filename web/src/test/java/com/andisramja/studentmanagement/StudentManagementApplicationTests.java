package com.andisramja.studentmanagement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test integrimi që verifikon se i gjithë konteksti i Spring-ut ngarkohet pa gabime.
 * <p>
 * Niset aplikacioni i plotë mbi bazën e të dhënave H2 (përfshirë skanimin e entiteteve,
 * repository-ve, mapper-it MapStruct, shërbimeve dhe controller-ave), duke konfirmuar
 * se konfigurimi multi-modul është i saktë.
 * </p>
 *
 * @author Andis Ramja
 */
@SpringBootTest
@DisplayName("Testi i ngarkimit të kontekstit të aplikacionit")
class StudentManagementApplicationTests {

    @Test
    @DisplayName("Konteksti i aplikacionit ngarkohet me sukses")
    void contextLoads() {
        // If the Spring context fails to start, this test fails automatically
    }
}
