package io.novelis.novyeapc.controllers;

import io.novelis.novyeapc.entities.enums.InterviewType;
import io.novelis.novyeapc.models.requests.ObjectiveRequest;
import io.novelis.novyeapc.models.responses.ObjectiveResponse;
import io.novelis.novyeapc.services.CollaboratorService;
import io.novelis.novyeapc.services.ObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/objective/")
@CrossOrigin(origins = { "http://localhost:3000" })
public class ObjectiveController {

    @Autowired
    ObjectiveService objectiveService;
    @Autowired
    CollaboratorService collaboratorService;

    @PostMapping()
    public ResponseEntity<ObjectiveResponse> add(@RequestBody @Valid ObjectiveRequest objectiveRequest){
//        CollaboratorResponse collaboratorResponse= collaboratorService.get(collaborator_id);
//        if(collaboratorResponse.getId()==null){
//            new ResourceNotFoundException("Not found collaborator="+collaborator_id);
//        }
        ObjectiveResponse objectiveResponse = objectiveService.add(objectiveRequest);

        return new ResponseEntity<>(objectiveResponse, HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>(objectiveService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ObjectiveResponse> get(@PathVariable(value = "id") Long id){
        ObjectiveResponse objectiveResponse = objectiveService.get(id);

        return new ResponseEntity<>(objectiveResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id){
        objectiveService.delete(id);

        return new ResponseEntity<>("Deleted !", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ObjectiveResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid ObjectiveRequest objectiveRequest
    ){
        ObjectiveResponse objectiveResponse = objectiveService.update(id, objectiveRequest);

        return new ResponseEntity<>(objectiveResponse, HttpStatus.OK);
    }

    @GetMapping("date")
    public  ResponseEntity<Map<String, Object>> StartDateAndEndDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){

        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>
                (objectiveService.searchByStartDateAndEndDateBetween(startDate, endDate, pageable), HttpStatus.OK);
    }

    @GetMapping("date/{year}")
    public  ResponseEntity<Map<String, Object>> getByYear(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @PathVariable(value = "year") int year){

        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>
                (objectiveService.searchByYear(year, pageable), HttpStatus.OK);
    }

    @GetMapping("collaborator")
    public  ResponseEntity<Map<String, Object>> getByCollaboratorId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") Long id){

        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>
                (objectiveService.searchByCollaboratorId(id, pageable), HttpStatus.OK);
    }

    @GetMapping("interview-type")
    public  ResponseEntity<Map<String, Object>> getByInterviewType(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") InterviewType interviewType){

        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>
                (objectiveService.searchByInterviewType(interviewType, pageable), HttpStatus.OK);
    }

    @GetMapping("collaborator-interview")
    public  ResponseEntity<Map<String, Object>> getByCollaboratorIdAndInterviewType(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") Long id,
            @RequestParam(value = "year") int year,
            @RequestParam(defaultValue = "") InterviewType interviewType){

        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>
                (objectiveService.searchByCollaboratorIdAndInterviewTypeAndYear(id, interviewType,year, pageable), HttpStatus.OK);
    }
}
