package com.microservices.apigateway.controller;

import com.microservices.apigateway.model.DeviceWrapper;
import com.microservices.apigateway.services.DeviceDataProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeviceDataController {

    private final DeviceDataProducerService producerService;

    @PostMapping ("/office/{officeName}/devices/data")
    public void dispatchOfficeDeviceData(@PathVariable("officeName") String officeName, @RequestBody DeviceWrapper deviceWrapper) {
        this.producerService.sendDataPerOffice(officeName, deviceWrapper);
    }
}
