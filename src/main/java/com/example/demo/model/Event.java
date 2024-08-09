package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    private long id;
    private String name;
    private List<Competitor> competitors;
    private long kickoff;
    private long lastUpdated;
    private League league;
    private String betline;
    private boolean open;
    private String status;
    private String matchPhase;
    private List<Market> markets;
}
