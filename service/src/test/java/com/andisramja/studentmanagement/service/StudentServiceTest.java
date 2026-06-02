package com.andisramja.studentmanagement.service;

import com.andisramja.studentmanagement.dto.StudentDTO;
import com.andisramja.studentmanagement.mapper.StudentMapper;
import com.andisramja.studentmanagement.model.Student;
import com.andisramja.studentmanagement.repository.StudentRepository;
import com.andisramja.studentmanagement.service.exception.EmailAlreadyExistsException;
import com.andisramja.studentmanagement.service.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Teste unitare për {@link StudentServiceImpl} duke përdorur JUnit 5 dhe Mockito.
 * <p>
 * Repository-i dhe mapper-i zëvendësohen me objekte mock në mënyrë që të
 * testohet vetëm logjika e shtresës service, e izoluar nga baza e të dhënave.
 * Çdo metodë e service-it mbulohet nga të paktën tre raste testimi.
 * </p>
 *
 * @author Andis Ramja
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testet e shtresës StudentService")
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private StudentDTO studentDTO;

    @BeforeEach
    void setUp() {
        student = Student.builder()
                .id(1L)
                .firstName("Andis")
                .lastName("Ramja")
                .email("andis.ramja@example.com")
                .dateOfBirth(LocalDate.of(2000, 5, 15))
                .enrollmentDate(LocalDate.of(2019, 10, 1))
                .gpa(3.8)
                .major("Inxhinieri Informatike")
                .build();

        studentDTO = StudentDTO.builder()
                .id(1L)
                .firstName("Andis")
                .lastName("Ramja")
                .email("andis.ramja@example.com")
                .dateOfBirth(LocalDate.of(2000, 5, 15))
                .enrollmentDate(LocalDate.of(2019, 10, 1))
                .gpa(3.8)
                .major("Inxhinieri Informatike")
                .build();
    }

    // ===================== createStudent =====================

    @Test
    @DisplayName("createStudent: krijon dhe kthen studentin kur email-i nuk ekziston")
    void createStudent_whenEmailIsNew_returnsCreatedStudent() {
        when(studentRepository.existsByEmail(studentDTO.getEmail())).thenReturn(false);
        when(studentMapper.toEntity(any(StudentDTO.class))).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.toDto(student)).thenReturn(studentDTO);

        StudentDTO result = studentService.createStudent(studentDTO);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("andis.ramja@example.com");
        assertThat(result.getFirstName()).isEqualTo("Andis");
    }

    @Test
    @DisplayName("createStudent: hedh përjashtim kur email-i ekziston tashmë")
    void createStudent_whenEmailExists_throwsException() {
        when(studentRepository.existsByEmail(studentDTO.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> studentService.createStudent(studentDTO))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("andis.ramja@example.com");

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("createStudent: thërret repository-n dhe mapper-in saktësisht një herë")
    void createStudent_invokesRepositoryAndMapper() {
        when(studentRepository.existsByEmail(studentDTO.getEmail())).thenReturn(false);
        when(studentMapper.toEntity(any(StudentDTO.class))).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.toDto(student)).thenReturn(studentDTO);

        studentService.createStudent(studentDTO);

        verify(studentRepository, times(1)).existsByEmail("andis.ramja@example.com");
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(studentMapper, times(1)).toDto(student);
    }

    // ===================== getAllStudents =====================

    @Test
    @DisplayName("getAllStudents: kthen listën e studentëve")
    void getAllStudents_returnsStudentList() {
        when(studentRepository.findAll()).thenReturn(List.of(student));
        when(studentMapper.toDtoList(anyList())).thenReturn(List.of(studentDTO));

        List<StudentDTO> result = studentService.getAllStudents();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("andis.ramja@example.com");
    }

    @Test
    @DisplayName("getAllStudents: kthen listë bosh kur nuk ka studentë")
    void getAllStudents_whenNoStudents_returnsEmptyList() {
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());
        when(studentMapper.toDtoList(anyList())).thenReturn(Collections.emptyList());

        List<StudentDTO> result = studentService.getAllStudents();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("getAllStudents: thërret repository.findAll() një herë")
    void getAllStudents_invokesRepository() {
        when(studentRepository.findAll()).thenReturn(List.of(student));
        when(studentMapper.toDtoList(anyList())).thenReturn(List.of(studentDTO));

        studentService.getAllStudents();

        verify(studentRepository, times(1)).findAll();
        verify(studentMapper, times(1)).toDtoList(anyList());
    }

    // ===================== getStudentById =====================

    @Test
    @DisplayName("getStudentById: kthen studentin kur ekziston")
    void getStudentById_whenExists_returnsStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentMapper.toDto(student)).thenReturn(studentDTO);

        StudentDTO result = studentService.getStudentById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getStudentById: hedh përjashtim kur studenti nuk gjendet")
    void getStudentById_whenNotFound_throwsException() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getStudentById(99L))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("99");

        verify(studentMapper, never()).toDto(any(Student.class));
    }

    @Test
    @DisplayName("getStudentById: thërret repository.findById() me ID-në e dhënë")
    void getStudentById_invokesRepositoryWithId() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentMapper.toDto(student)).thenReturn(studentDTO);

        studentService.getStudentById(1L);

        verify(studentRepository, times(1)).findById(1L);
        verify(studentMapper, times(1)).toDto(student);
    }

    // ===================== updateStudent =====================

    @Test
    @DisplayName("updateStudent: përditëson dhe kthen studentin kur ekziston")
    void updateStudent_whenExists_returnsUpdatedStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.toDto(student)).thenReturn(studentDTO);

        StudentDTO result = studentService.updateStudent(1L, studentDTO);

        assertThat(result).isNotNull();
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    @DisplayName("updateStudent: hedh përjashtim kur studenti nuk gjendet")
    void updateStudent_whenNotFound_throwsException() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.updateStudent(99L, studentDTO))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("99");

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("updateStudent: aplikon vlerat e reja mbi entitetin ekzistues")
    void updateStudent_updatesEntityFields() {
        StudentDTO changes = StudentDTO.builder()
                .firstName("Ardit")
                .lastName("Hoxha")
                .email("andis.ramja@example.com") // email i pandryshuar
                .gpa(3.5)
                .major("Shkenca Kompjuterike")
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.toDto(any(Student.class))).thenReturn(studentDTO);

        studentService.updateStudent(1L, changes);

        assertThat(student.getFirstName()).isEqualTo("Ardit");
        assertThat(student.getLastName()).isEqualTo("Hoxha");
        assertThat(student.getMajor()).isEqualTo("Shkenca Kompjuterike");
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    @DisplayName("updateStudent: hedh përjashtim kur email-i i ri i përket një studenti tjetër")
    void updateStudent_whenNewEmailTaken_throwsException() {
        StudentDTO changes = StudentDTO.builder()
                .firstName("Andis")
                .lastName("Ramja")
                .email("tjeter@example.com") // email i ndryshuar
                .gpa(3.8)
                .major("Inxhinieri Informatike")
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.existsByEmail("tjeter@example.com")).thenReturn(true);

        assertThatThrownBy(() -> studentService.updateStudent(1L, changes))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("tjeter@example.com");

        verify(studentRepository, never()).save(any(Student.class));
    }

    // ===================== deleteStudent =====================

    @Test
    @DisplayName("deleteStudent: fshin studentin kur ekziston")
    void deleteStudent_whenExists_deletesStudent() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteStudent: hedh përjashtim kur studenti nuk gjendet")
    void deleteStudent_whenNotFound_throwsException() {
        when(studentRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> studentService.deleteStudent(99L))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("99");

        verify(studentRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("deleteStudent: thërret deleteById() saktësisht një herë")
    void deleteStudent_invokesDeleteOnce() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }
}
