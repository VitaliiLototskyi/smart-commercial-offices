package com.microservices.datageneratorservice.services;

import com.microservices.datageneratorservice.model.DataCenter;
import com.microservices.datageneratorservice.model.Device;
import com.microservices.datageneratorservice.utils.DataGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProducerService {
    public List<Device> produceData(String dataCenter) {

        return new ArrayList<>(DataGenerator.generateDevices(DataCenter.getDataCenterName(dataCenter)));
    }
}
