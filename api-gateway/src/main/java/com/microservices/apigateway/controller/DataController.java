package com.microservices.apigateway.controller;

import com.microservices.apigateway.model.*;
import com.microservices.apigateway.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class DataController {
    @Autowired
    private MessageService messageService;
    public RestTemplate restTemplate;
    //@Value(value = "${connection.string}")
    private final String connectionURL = "http://localhost:8000/generateData/kyiv/";

    @GetMapping ("/retrieveData/kyiv/")
    public String receiveDataFromKyiv() throws IOException {
        restTemplate =  new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DeviceWrapper> request = new HttpEntity<>(null, headers);
        ResponseEntity<DeviceWrapper> responseEntity = restTemplate.exchange(
                connectionURL,HttpMethod.GET, request, new ParameterizedTypeReference<DeviceWrapper>() {});
        if (responseEntity.getBody() != null) {

            messageService.sendDataPerOffice(responseEntity.getBody().getDeviceList());
            return "Devices received and send to corresponding kafka topic";
        }
        // TO DO Sending to Kafka topic
        return "Nothing received";
    }

//    @GetMapping("/getdata/")
//    public ResponseEntity<String>
}
