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
public class Runner {
    private long id;
    private String name;
    private boolean open;
    private int r;
    private int c;
    private List<String> tags;
    private double price;
    private String priceStr;
    private String handicap;
}
