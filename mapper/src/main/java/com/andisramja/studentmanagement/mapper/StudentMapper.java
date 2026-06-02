package com.andisramja.studentmanagement.mapper;

import com.andisramja.studentmanagement.dto.StudentDTO;
import com.andisramja.studentmanagement.model.Student;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper-i MapStruct për konvertimin mes entitetit {@link Student} dhe {@link StudentDTO}.
 * <p>
 * Meqenëse emrat e fushave përputhen plotësisht mes entitetit dhe DTO-së,
 * MapStruct gjeneron automatikisht të gjithë kodin e mapimit gjatë kompilimit;
 * nuk kërkohet asnjë mapim manual. Parametri {@code componentModel = "spring"}
 * bën që implementimi i gjeneruar të regjistrohet si bean i Spring-ut.
 * </p>
 *
 * @author Andis Ramja
 */
@Mapper(componentModel = "spring")
public interface StudentMapper {

    /**
     * Konverton një entitet {@link Student} në {@link StudentDTO}.
     *
     * @param student entiteti që do të konvertohet
     * @return objekti DTO përkatës
     */
    StudentDTO toDto(Student student);

    /**
     * Konverton një {@link StudentDTO} në entitet {@link Student}.
     *
     * @param studentDTO objekti DTO që do të konvertohet
     * @return entiteti përkatës
     */
    Student toEntity(StudentDTO studentDTO);

    /**
     * Konverton një listë entitetesh {@link Student} në listë {@link StudentDTO}.
     *
     * @param students lista e entiteteve
     * @return lista e objekteve DTO
     */
    List<StudentDTO> toDtoList(List<Student> students);
}
