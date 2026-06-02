package com.andisramja.studentmanagement.repository;

import com.andisramja.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository-i Spring Data JPA për entitetin {@link Student}.
 * <p>
 * Duke zgjeruar {@link JpaRepository}, ofron automatikisht operacionet
 * bazë CRUD (save, findAll, findById, deleteById, etj.) pa pasur nevojë
 * për implementim. Metodat shtesë gjenerohen nga emri i tyre.
 * </p>
 *
 * @author Andis Ramja
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Gjen një student sipas adresës së email-it.
     *
     * @param email email-i për kërkim
     * @return {@link Optional} me studentin nëse ekziston, përndryshe bosh
     */
    Optional<Student> findByEmail(String email);

    /**
     * Kontrollon nëse ekziston tashmë një student me email-in e dhënë.
     *
     * @param email email-i për kontroll
     * @return {@code true} nëse ekziston, përndryshe {@code false}
     */
    boolean existsByEmail(String email);
}
