package com.microservices.datageneratorservice.controller;

import com.microservices.datageneratorservice.services.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GeneratorController {

    private final ProducerService producerService;

    @GetMapping("/generateData")
    public String generateData(@RequestParam(value = "amount", required = false) Integer amount,
                                               @RequestParam(value = "genRateMs", required = false) Long genRateMs) throws Exception {
        amount = (amount == null) ? 10 : amount;
        genRateMs = (genRateMs == null) ? 1000 : genRateMs;
        // generates & sends to api-gateway endpoint
        producerService.genAndSendDataForDifferentOffices(amount, genRateMs);

        return "Generated records: " + amount;
    }
}
