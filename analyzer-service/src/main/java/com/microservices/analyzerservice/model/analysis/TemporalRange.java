package com.microservices.analyzerservice.model.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.temporal.TemporalAccessor;

@Data
@AllArgsConstructor
public class TemporalRange<T extends TemporalAccessor> {

    private final T start;
    private final T end;
}