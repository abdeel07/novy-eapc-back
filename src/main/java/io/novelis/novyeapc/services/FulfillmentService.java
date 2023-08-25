package io.novelis.novyeapc.services;

import io.novelis.novyeapc.entities.Fulfillment;
import io.novelis.novyeapc.models.requests.FulfillmentRequest;
import io.novelis.novyeapc.models.responses.FulfillmentResponse;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface FulfillmentService {

    List<FulfillmentResponse> getAll(Long id);
    List<FulfillmentResponse> getAll();



    List<FulfillmentResponse> add(Long id, ArrayList<FulfillmentRequest> fulfillmentRequests);

    FulfillmentResponse get(Long id);

    FulfillmentResponse update(Long id, FulfillmentRequest fulfillmentRequest);

    void delete(Long id);

}
