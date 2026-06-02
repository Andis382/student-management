package com.andisramja.studentmanagement.web;

import com.andisramja.studentmanagement.dto.StudentDTO;
import com.andisramja.studentmanagement.excel.ExcelExportService;
import com.andisramja.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller-i që ekspozon API-në për menaxhimin e studentëve.
 * <p>
 * Ofron operacionet CRUD si dhe eksportimin e listës së studentëve në Excel.
 * Të gjitha rrugët fillojnë me prefiksin <code>/api/students</code>.
 * </p>
 *
 * @author Andis Ramja
 */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final ExcelExportService excelExportService;

    /**
     * Shton një student të ri.
     *
     * @param studentDTO të dhënat e studentit (të validuara)
     * @return studenti i krijuar me kod statusi 201
     */
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO created = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Liston të gjithë studentët.
     *
     * @return lista e studentëve me kod statusi 200
     */
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /**
     * Merr një student sipas ID-së.
     *
     * @param id identifikuesi i studentit
     * @return studenti përkatës me kod statusi 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    /**
     * Përditëson një student ekzistues.
     *
     * @param id         identifikuesi i studentit
     * @param studentDTO të dhënat e reja (të validuara)
     * @return studenti i përditësuar me kod statusi 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id,
                                                    @Valid @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));
    }

    /**
     * Fshin një student sipas ID-së.
     *
     * @param id identifikuesi i studentit
     * @return përgjigje bosh me kod statusi 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Eksporton listën e studentëve si file Excel (.xlsx) për shkarkim.
     *
     * @return file-i Excel i mbështjellë në {@link ResponseEntity} me header-in
     *         Content-Disposition për shkarkim
     */
    @GetMapping("/export")
    public ResponseEntity<Resource> exportToExcel() {
        List<StudentDTO> students = studentService.getAllStudents();
        byte[] data = excelExportService.exportStudentsToExcel(students);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=studentet.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(data.length)
                .body(resource);
    }
}
