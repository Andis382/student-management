package com.andisramja.studentmanagement.web;

import com.andisramja.studentmanagement.dto.StudentDTO;
import com.andisramja.studentmanagement.excel.ExcelExportService;
import com.andisramja.studentmanagement.service.StudentService;
import com.andisramja.studentmanagement.service.exception.StudentNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Teste integrimi për {@link StudentController} duke përdorur {@code @WebMvcTest} dhe MockMvc.
 * <p>
 * Shtresa e shërbimit dhe ajo e eksportimit zëvendësohen me bean-a mock, në mënyrë që
 * të testohet vetëm shtresa web (rrugët, kodet e statusit, validimi dhe serializimi JSON).
 * </p>
 *
 * @author Andis Ramja
 */
@WebMvcTest(StudentController.class)
@DisplayName("Testet e integrimit për StudentController")
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private ExcelExportService excelExportService;

    private StudentDTO sampleDto() {
        return StudentDTO.builder()
                .id(1L)
                .firstName("Andis")
                .lastName("Ramja")
                .email("andis.ramja@example.com")
                .gpa(3.8)
                .major("Inxhinieri Informatike")
                .build();
    }

    @Test
    @DisplayName("GET /api/students kthen listën me status 200")
    void getAllStudents_returnsOk() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of(sampleDto()));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("andis.ramja@example.com"));
    }

    @Test
    @DisplayName("GET /api/students/{id} kthen studentin me status 200")
    void getStudentById_returnsOk() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(sampleDto());

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Andis"));
    }

    @Test
    @DisplayName("GET /api/students/{id} kthen 404 kur studenti nuk gjendet")
    void getStudentById_returnsNotFound() throws Exception {
        when(studentService.getStudentById(99L))
                .thenThrow(new StudentNotFoundException("Studenti me ID 99 nuk u gjet"));

        mockMvc.perform(get("/api/students/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("POST /api/students krijon studentin me status 201")
    void createStudent_returnsCreated() throws Exception {
        when(studentService.createStudent(any(StudentDTO.class))).thenReturn(sampleDto());

        String json = """
                {
                  "firstName": "Andis",
                  "lastName": "Ramja",
                  "email": "andis.ramja@example.com",
                  "dateOfBirth": "2000-05-15",
                  "enrollmentDate": "2019-10-01",
                  "gpa": 3.8,
                  "major": "Inxhinieri Informatike"
                }
                """;

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /api/students kthen 400 kur të dhënat janë të pavlefshme")
    void createStudent_returnsBadRequest() throws Exception {
        String invalidJson = """
                {
                  "firstName": "",
                  "lastName": "Ramja",
                  "email": "jo-email"
                }
                """;

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("DELETE /api/students/{id} kthen status 204")
    void deleteStudent_returnsNoContent() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());

        verify(studentService).deleteStudent(1L);
    }

    @Test
    @DisplayName("GET /api/students/export kthen file Excel me header Content-Disposition")
    void exportToExcel_returnsFile() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of(sampleDto()));
        when(excelExportService.exportStudentsToExcel(any())).thenReturn(new byte[]{1, 2, 3});

        mockMvc.perform(get("/api/students/export"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=studentet.xlsx"))
                .andExpect(content().contentType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }
}
