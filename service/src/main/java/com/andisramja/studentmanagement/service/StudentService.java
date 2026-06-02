package com.andisramja.studentmanagement.service;

import com.andisramja.studentmanagement.dto.StudentDTO;

import java.util.List;

/**
 * Interface-i i shërbimit që përcakton operacionet e biznesit për studentët.
 * <p>
 * Ndarja e interface-it nga implementimi lejon zëvendësimin e lehtë të
 * logjikës dhe lehtëson testimin me objekte mock.
 * </p>
 *
 * @author Andis Ramja
 */
public interface StudentService {

    /**
     * Krijon një student të ri në sistem.
     *
     * @param studentDTO të dhënat e studentit që do të krijohet
     * @return studenti i krijuar bashkë me ID-në e gjeneruar
     */
    StudentDTO createStudent(StudentDTO studentDTO);

    /**
     * Kthen listën e të gjithë studentëve të regjistruar.
     *
     * @return lista e studentëve (mund të jetë bosh)
     */
    List<StudentDTO> getAllStudents();

    /**
     * Merr një student sipas identifikuesit të tij.
     *
     * @param id identifikuesi i studentit
     * @return studenti përkatës
     */
    StudentDTO getStudentById(Long id);

    /**
     * Përditëson të dhënat e një studenti ekzistues.
     *
     * @param id         identifikuesi i studentit që do të përditësohet
     * @param studentDTO të dhënat e reja
     * @return studenti i përditësuar
     */
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    /**
     * Fshin një student sipas identifikuesit të tij.
     *
     * @param id identifikuesi i studentit që do të fshihet
     */
    void deleteStudent(Long id);
}
