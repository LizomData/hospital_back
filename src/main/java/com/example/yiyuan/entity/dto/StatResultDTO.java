package com.example.yiyuan.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatResultDTO {
    private List<String> dateList;
    private List<Integer> appointmentList;
    private List<Integer> patientList;
}