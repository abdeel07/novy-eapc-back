package io.novelis.novyeapc.services.impl;

import io.novelis.novyeapc.entities.Fulfillment;
import io.novelis.novyeapc.entities.Interview;
import io.novelis.novyeapc.mappers.FulfillmentMapper;
import io.novelis.novyeapc.models.requests.FulfillmentRequest;
import io.novelis.novyeapc.models.responses.FulfillmentResponse;
import io.novelis.novyeapc.models.responses.InterviewResponse;
import io.novelis.novyeapc.repositories.FulfillmentRepository;
import io.novelis.novyeapc.repositories.InterviewRepository;
import io.novelis.novyeapc.services.FulfillmentService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FulfillmentServiceImpl implements FulfillmentService {
    FulfillmentMapper mapper = Mappers.getMapper(FulfillmentMapper.class);
    @Autowired
    FulfillmentRepository fulfillmentRepository;

    @Autowired
    InterviewRepository interviewRepository;
    @Override
    public List<FulfillmentResponse> getAll(Long id) {
        List<Fulfillment> fulfillments=fulfillmentRepository.findByInterviewId(id);
        List<FulfillmentResponse> interviewResponses=mapper.mapFulfillment(fulfillments);
        return interviewResponses;
    }

    @Override
    public List<FulfillmentResponse> getAll() {
        List<Fulfillment> fulfillments=fulfillmentRepository.findAll();
        List<FulfillmentResponse> fulfillmentResponses=mapper.mapFulfillment(fulfillments);
        return fulfillmentResponses;
    }

    @Override
    public List<FulfillmentResponse> add(Long id, ArrayList<FulfillmentRequest> fulfillmentRequests) {


        List<FulfillmentResponse> fulfillmentResponses = new ArrayList<>();
        Interview interview=interviewRepository.findById(id).get();

        for (FulfillmentRequest fulfillmentRequest : fulfillmentRequests) {
            Fulfillment fulfillment=mapper.fulfillmentRequestToFulfillment(fulfillmentRequest);
            fulfillment.setInterview(interview);
              Fulfillment savedFulfillment = fulfillmentRepository.save(fulfillment);
            FulfillmentResponse fulfillmentResponse=mapper.fulfillmentToFulfillmentResponse(savedFulfillment);
            fulfillmentResponses.add(fulfillmentResponse);

        }




        return fulfillmentResponses;

    }

    @Override
    public FulfillmentResponse get(Long id) {
        Fulfillment fulfillment=fulfillmentRepository.findById(id).get();
        FulfillmentResponse fulfillmentResponse=mapper.fulfillmentToFulfillmentResponse(fulfillment);
        return fulfillmentResponse;
    }

    @Override
    public FulfillmentResponse update(Long id, FulfillmentRequest fulfillmentRequest) {
        Fulfillment fulfillment=fulfillmentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("interview "+id+"not found"));

        fulfillment.setComment(fulfillmentRequest.getComment());
        fulfillment.setTitle(fulfillmentRequest.getTitle());
        Fulfillment fulfillment1=fulfillmentRepository.save(fulfillment);
        FulfillmentResponse fulfillmentResponse=mapper.fulfillmentToFulfillmentResponse(fulfillment1);
        return  fulfillmentResponse;
    }

    @Override
    public void delete(Long id) {
        Fulfillment fulfillment=fulfillmentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("interview "+id+"not found"));
        fulfillmentRepository.deleteById(id);
    }



}

