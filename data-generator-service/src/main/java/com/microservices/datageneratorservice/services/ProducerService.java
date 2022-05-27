package com.microservices.datageneratorservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.datageneratorservice.model.DeviceWrapper;
import com.microservices.datageneratorservice.model.Office;
import com.microservices.datageneratorservice.model.SmartPlug;
import com.microservices.datageneratorservice.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {

    // beans
    private final SmartPlugImitationService smartPlugImitationService;
    private final OfficeRepository officeRepository;
    private final ObjectMapper objectMapper;
    // values
    @Value("${api.gateway.host}")
    private String apiGatewayHost;
    @Value("${api.gateway.port}")
    private String apiGatewayPort;
    // fields
    private final SecureRandom random = new SecureRandom();
    private final OkHttpClient client = new OkHttpClient();

    public void genAndSendDataForDifferentOffices(int amount, long generationRateMs) throws InterruptedException, IOException {
        List<Office> offices = officeRepository.findAll();
        int generatedCounter = 0;
        while (generatedCounter < amount) {
            // pick next random office
            Office nextOffice = offices.get(random.nextInt(offices.size()));
            // generate & send
            SmartPlug smartPlug = smartPlugImitationService.generateDeviceWithPopulatedValues();
            this.sendDeviceToApiGateway(nextOffice, smartPlug.getDevice().getUuid(), this.objectMapper.writeValueAsString(smartPlug));
            // interval between msg generation
            generatedCounter++;
            Thread.sleep(generationRateMs);
        }
    }

    private void sendDeviceToApiGateway(Office office, String deviceUUID, String deviceBody) throws IOException {
        DeviceWrapper deviceWrapper = new DeviceWrapper();
        deviceWrapper.setDeviceUUID(deviceUUID);
        deviceWrapper.setDeviceBody(deviceBody);

        String deviceWrapperJSON = this.objectMapper.writeValueAsString(deviceWrapper);


        RequestBody body = RequestBody.create(deviceWrapperJSON, MediaType.parse("application/json; charset=utf-8"));
        final String targetURL = String.format("%s:%s/office/%s/devices/data", this.apiGatewayHost, this.apiGatewayPort, office.getName());
        Request request = new Request.Builder()
                .url(targetURL)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        boolean success = response.code() == HttpStatus.OK.value();
        if (success) {
            log.info("Record sent successfully to office {}. Body: {}", office.getName(), deviceBody);
        } else {
            log.warn("Failed to send record to office {}. Body: {}. Response status: {}", office.getName(), deviceBody, response.code());
        }
        // end
        response.body().close();
    }

}