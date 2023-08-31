package io.novelis.novyeapc.controllers;

import io.novelis.novyeapc.entities.enums.InterviewType;
import io.novelis.novyeapc.models.requests.InterviewRequest;
import io.novelis.novyeapc.models.responses.InterviewResponse;
import io.novelis.novyeapc.services.impl.InterviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/interview/")
@CrossOrigin(origins = { "http://localhost:3000" })
public class InterviewController {

    @Autowired
    InterviewServiceImpl interviewService;

    @PostMapping()
    public ResponseEntity<InterviewResponse> add(@RequestBody InterviewRequest interview) throws ParseException {

        InterviewResponse interviewResponse = interviewService.add(interview);

        return new ResponseEntity<>(interviewResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<InterviewResponse> get(@PathVariable Long id) {

        InterviewResponse interviewResponse = interviewService.get(id);

        return new ResponseEntity<>(interviewResponse, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<InterviewResponse> update(@PathVariable Long id, @RequestBody InterviewRequest interviewRequest) {

        InterviewResponse interviewResponse = interviewService.update(id, interviewRequest);

        return new ResponseEntity<>(interviewResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        interviewService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>(interviewService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("type/{type}")
    public ResponseEntity<Map<String, Object>> getByType(
            @PathVariable InterviewType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>(interviewService.searchByType(type, pageable), HttpStatus.OK);
    }

    @GetMapping("date/{year}")
    public ResponseEntity<Map<String, Object>> findByDate(
            @PathVariable int year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>(interviewService.searchByDate(year, pageable), HttpStatus.OK);
    }

    @GetMapping("collaborator/{id}")
    public ResponseEntity<Map<String, Object>> findByIdAndDate(
            @PathVariable Long id,
            @RequestParam(defaultValue = "2023") int year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>(interviewService.searchByIdAndDate(id,year, pageable), HttpStatus.OK);
    }
    @GetMapping("filters")
    public  ResponseEntity<Map<String, Object>> getByAllFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int year,
            @RequestParam(defaultValue = "Performance,Increase") Set<InterviewType> interviewTypes,
            @RequestParam(defaultValue = "") Set<Long> collaboratorsId){

        Pageable pageable = PageRequest.of(page, size);
        Set<InterviewType> interviewTypesParam = interviewTypes.isEmpty() ? null : interviewTypes;
        return new ResponseEntity<>
                (interviewService.searchByAllAttributes(name, year, interviewTypesParam,collaboratorsId, pageable), HttpStatus.OK);
    }
}