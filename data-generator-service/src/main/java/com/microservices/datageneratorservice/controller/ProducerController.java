package com.microservices.datageneratorservice.controller;

import com.microservices.datageneratorservice.model.Device;
import com.microservices.datageneratorservice.model.DeviceWrapper;
import com.microservices.datageneratorservice.services.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProducerController {
    private boolean isOn;

    @Value(value = "${spring.kafka.template.default-topic}")
    private String topic;
    private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    @Autowired
    private ProducerService producerService;

    @GetMapping("/generateData/kyiv/")
    public ResponseEntity<DeviceWrapper> sendData() {
        List<Device> deviceList = producerService.produceData("kyiv");
        DeviceWrapper deviceWrapper = new DeviceWrapper(deviceList);

        return new ResponseEntity<DeviceWrapper>(deviceWrapper, HttpStatus.OK);
    }
}
