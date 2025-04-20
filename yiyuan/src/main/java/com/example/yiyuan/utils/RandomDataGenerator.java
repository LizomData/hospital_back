package com.example.yiyuan.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDataGenerator {

    public static List<Integer> generateRandomNumbers(int count, int min, int max) {
        List<Integer> numbers = new ArrayList<>(count);
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            numbers.add(random.nextInt(max - min + 1) + min);
        }

        return numbers;
    }

    public static List<String> getNextSevenDays() {
        List<String> dates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 1; i <= 7; i++) {
            LocalDate nextDate = today.plusDays(i);
            dates.add(nextDate.format(formatter));
        }

        return dates;
    }
}