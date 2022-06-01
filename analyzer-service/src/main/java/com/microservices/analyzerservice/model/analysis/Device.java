package com.microservices.analyzerservice.model.analysis;

import com.microservices.analyzerservice.model.enums.DeviceConnectionType;
import com.microservices.analyzerservice.model.enums.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {

    @Id
    private String uuid;

    private String name;

    private String modelName;

    private DeviceType deviceType;

    private DeviceConnectionType connectionType;

    @ManyToOne
    @JoinColumn(name = "OFFICE_ID", nullable = false)
    private Office office;
}
