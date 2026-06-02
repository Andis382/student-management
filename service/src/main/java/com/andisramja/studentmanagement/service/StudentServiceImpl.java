package com.andisramja.studentmanagement.service;

import com.andisramja.studentmanagement.dto.StudentDTO;
import com.andisramja.studentmanagement.mapper.StudentMapper;
import com.andisramja.studentmanagement.model.Student;
import com.andisramja.studentmanagement.repository.StudentRepository;
import com.andisramja.studentmanagement.service.exception.EmailAlreadyExistsException;
import com.andisramja.studentmanagement.service.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementimi i {@link StudentService} që përmban logjikën e biznesit
 * për menaxhimin e studentëve.
 * <p>
 * Përdor {@link StudentRepository} për qasjen në të dhëna dhe
 * {@link StudentMapper} për konvertimin mes entitetit dhe DTO-së.
 * Anotacioni {@link Transactional} siguron integritetin transaksional.
 * </p>
 *
 * @author Andis Ramja
 */
@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        // Reject duplicate email addresses before persisting
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Ekziston tashmë një student me email-in: " + studentDTO.getEmail());
        }
        // Ensure a brand-new record is created regardless of any id sent by the client
        studentDTO.setId(null);
        Student entity = studentMapper.toEntity(studentDTO);
        Student saved = studentRepository.save(entity);
        return studentMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        return studentMapper.toDtoList(studentRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(
                        "Studenti me ID " + id + " nuk u gjet"));
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(
                        "Studenti me ID " + id + " nuk u gjet"));

        // If the email is being changed, make sure the new one is not already taken
        if (!existing.getEmail().equals(studentDTO.getEmail())
                && studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Ekziston tashmë një student me email-in: " + studentDTO.getEmail());
        }

        existing.setFirstName(studentDTO.getFirstName());
        existing.setLastName(studentDTO.getLastName());
        existing.setEmail(studentDTO.getEmail());
        existing.setDateOfBirth(studentDTO.getDateOfBirth());
        existing.setEnrollmentDate(studentDTO.getEnrollmentDate());
        existing.setGpa(studentDTO.getGpa());
        existing.setMajor(studentDTO.getMajor());

        Student updated = studentRepository.save(existing);
        return studentMapper.toDto(updated);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Studenti me ID " + id + " nuk u gjet");
        }
        studentRepository.deleteById(id);
    }
}
