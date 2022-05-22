package com.microservices.dataaggregationservice.model;

public class Topic {

    private String name;
    private Boolean enabled;

    public Topic(String name, Boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public Topic(String name) {
        this(name, true);
    }


    public Boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }
}
