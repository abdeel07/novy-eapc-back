package io.novelis.novyeapc.controllers;

import io.novelis.novyeapc.models.requests.FulfillmentRequest;
import io.novelis.novyeapc.models.responses.FulfillmentResponse;
import io.novelis.novyeapc.models.responses.InterviewResponse;
import io.novelis.novyeapc.services.impl.FulfillmentServiceImpl;
import io.novelis.novyeapc.services.impl.InterviewServiceImpl;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FulfillmentController {
    @Autowired
    FulfillmentServiceImpl fulfillmentService;
    @PostMapping("/interview/{interview_id}/fulfillment")
    public ResponseEntity<FulfillmentResponse> addFulfillment(@PathVariable(value = "interview_id") Long interview_id, @RequestBody FulfillmentRequest fulfillment){

//            fulfillment.setInterviewResponse(interviewResponse);
         FulfillmentResponse fulfillmentResponse=fulfillmentService.add(fulfillment);
        return new ResponseEntity<>(fulfillmentResponse, HttpStatus.CREATED);
    }
    @GetMapping("/interview/{interview_id}/fulfillment")
    public ResponseEntity<List<FulfillmentResponse> > getFulfillment(@PathVariable(value = "interview_id") Long interview_id){
//        if(!interviewService.existsById(interview_id)){
//            throw new ResourceNotFoundException("not found interview  with id "+interview_id);
//        }
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


}
