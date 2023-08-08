package io.novelis.novyeapc.controllers;

import io.novelis.novyeapc.models.requests.CollaboratorRequest;
import io.novelis.novyeapc.models.responses.CollaboratorResponse;
import io.novelis.novyeapc.services.CollaboratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/collaborator/")
@CrossOrigin(origins = { "http://localhost:3000" })
public class CollaboratorController {

    @Autowired
    CollaboratorService collaboratorService;

    @PostMapping
    public ResponseEntity<CollaboratorResponse> add(@RequestBody @Valid CollaboratorRequest collaboratorRequest){
        CollaboratorResponse collaboratorResponse = collaboratorService.add(collaboratorRequest);

        return new ResponseEntity<>(collaboratorResponse, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<CollaboratorResponse>> getAll(){
        return new ResponseEntity<>(collaboratorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CollaboratorResponse> get(@PathVariable Long id){
        return new ResponseEntity<>(collaboratorService.get(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        collaboratorService.delete(id);
        return new ResponseEntity<>("Deleted !", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CollaboratorResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid CollaboratorRequest collaboratorRequest
    ){
        return new ResponseEntity<>
                (collaboratorService.update(id, collaboratorRequest), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getByLastName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String name)
    {
        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>
                (collaboratorService.searchByLastName(name, pageable), HttpStatus.OK);
    }
}
