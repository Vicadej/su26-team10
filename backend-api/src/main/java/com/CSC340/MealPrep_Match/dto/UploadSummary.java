package com.CSC340.MealPrep_Match.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadSummary {

    private String type;
    private String title;
    private Long id;
}
