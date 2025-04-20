package com.example.yiyuan.utils;

import com.example.yiyuan.entity.dto.BloodSugarData;
import com.example.yiyuan.entity.dto.BloodYaDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakeDataGenerator {
    public static List<BloodSugarData> generateFakeData() {
        List<BloodSugarData> dataList = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2025, 3, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 13);

        Random random = new Random();

        for (LocalDate date = startDate;
             !date.isAfter(endDate);
             date = date.plusDays(1)) {
            // 生成90-139的随机数（含90和139）
            int value = random.nextInt(50) + 90;
            dataList.add(new BloodSugarData(
                    date.toString(),
                    value
            ));
        }
        return dataList;
    }

    public static List<BloodYaDto> generateData() {
        List<BloodYaDto> dataList = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2025, 3, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 13);
        Random random = new Random();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            int scaledValue = random.nextInt(22) + 39;
            double value = scaledValue / 10.0;
            if (value < 3.9 || value > 6.1) {
                value = 6.1;
            }
            dataList.add(new BloodYaDto(date.toString(), value));
        }
        return dataList;
    }
}