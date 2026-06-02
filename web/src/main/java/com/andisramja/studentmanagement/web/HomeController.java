package com.andisramja.studentmanagement.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controller-i i faqes kryesore (root) që jep një përmbledhje të API-së.
 * <p>
 * Kthen informacion bazë dhe listën e endpoint-eve, në mënyrë që hapja e
 * adresës rrënjë (p.sh. http://localhost:8080/) të mos kthejë gabim, por
 * një përgjigje informuese.
 * </p>
 *
 * @author Andis Ramja
 */
@RestController
public class HomeController {

    /**
     * Pika hyrëse e API-së.
     *
     * @return një hartë me të dhënat e aplikacionit dhe endpoint-et e disponueshme
     */
    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, String> endpoints = new LinkedHashMap<>();
        endpoints.put("listo të gjithë", "GET /api/students");
        endpoints.put("merr sipas ID", "GET /api/students/{id}");
        endpoints.put("shto", "POST /api/students");
        endpoints.put("përditëso", "PUT /api/students/{id}");
        endpoints.put("fshi", "DELETE /api/students/{id}");
        endpoints.put("eksporto në Excel", "GET /api/students/export");

        Map<String, Object> info = new LinkedHashMap<>();
        info.put("aplikacioni", "Sistemi i Menaxhimit të Studentëve");
        info.put("autori", "Andis Ramja");
        info.put("versioni", "1.0.0");
        info.put("endpointet", endpoints);
        info.put("konsola_h2", "GET /h2-console");
        info.put("shendeti", "GET /actuator/health");
        return info;
    }
}
