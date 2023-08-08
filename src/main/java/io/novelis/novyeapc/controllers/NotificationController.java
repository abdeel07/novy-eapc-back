package io.novelis.novyeapc.controllers;

import io.novelis.novyeapc.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notification/")
@CrossOrigin(origins = { "http://localhost:3000" })
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("{collaboratorId}")
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @PathVariable Long collaboratorId
    )
    {

        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>
                (notificationService.searchByCollaboratorId(collaboratorId, pageable), HttpStatus.OK);
    }
}
