package io.novelis.novyeapc.controllers;

import io.novelis.novyeapc.entities.Interview;
import io.novelis.novyeapc.models.requests.FulfillmentRequest;
import io.novelis.novyeapc.models.requests.QuizRequest;
import io.novelis.novyeapc.models.responses.FulfillmentResponse;
import io.novelis.novyeapc.models.responses.QuizResponse;
import io.novelis.novyeapc.services.InterviewService;
import io.novelis.novyeapc.services.impl.FulfillmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/fulfillments/")
@CrossOrigin(origins = { "http://localhost:3000" })
public class FulfillmentController {
    @Autowired
    FulfillmentServiceImpl fulfillmentService;


    @GetMapping("/interview/{interview_id}/fulfillment")
    public ResponseEntity<List<FulfillmentResponse> > getFulfillment(@PathVariable(value = "interview_id") Long interview_id){

        List<FulfillmentResponse> fulfillmentResponses=fulfillmentService.getAll(interview_id);


        return new ResponseEntity<>(fulfillmentResponses,HttpStatus.OK);
    }
    @GetMapping("/fulfillments")
    public ResponseEntity<List<FulfillmentResponse> > getFulfilments(){
        List<FulfillmentResponse> fulfillmentResponses=fulfillmentService.getAll();
        return new ResponseEntity<>(fulfillmentResponses,HttpStatus.OK);
    }
    @GetMapping("/fulfillment/{fulfillment_id}")
    public ResponseEntity<FulfillmentResponse> getFulfillmentById(@PathVariable(value = "fulfillment_id") Long fulfillment_id){
        FulfillmentResponse fulfillmentResponse=fulfillmentService.get(fulfillment_id);
        return  new ResponseEntity<>(fulfillmentResponse, HttpStatus.OK);

    }
    @PutMapping("/fulfillment/{fulfillment_id}")
    public ResponseEntity<FulfillmentResponse> updateFulfillment(@PathVariable(value = "fulfillment_id") Long fulfillment_id,@RequestBody FulfillmentRequest fulfillmentRequest){
        FulfillmentResponse fulfillmentResponse= fulfillmentService.update(fulfillment_id,fulfillmentRequest);
        return new ResponseEntity<>(fulfillmentResponse, HttpStatus.OK);

    }
    @DeleteMapping("fulfillment/{fulfillment_id}")
    public ResponseEntity<HttpStatus> deleteFulfillment(@PathVariable(value = "fulfillment_id") Long fulfillment_id){

        fulfillmentService.delete(fulfillment_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PostMapping("{interview_id}")
    public ResponseEntity<List<FulfillmentResponse>> addFulfillments(@PathVariable Long interview_id,@RequestBody ArrayList<FulfillmentRequest> fulfillmentRequests){
        List<FulfillmentResponse> fulfillmentResponses = fulfillmentService.add(interview_id,fulfillmentRequests);

        return new ResponseEntity<>(fulfillmentResponses, HttpStatus.OK);
    }



}
