package com.example.yiyuan.utils;

import com.example.yiyuan.entity.po.Scheduleinstances;
import com.example.yiyuan.entity.query.ScheduleinstancesQuery;
import com.example.yiyuan.mappers.ScheduleinstancesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
@Component
public class ScheduleUtils {

    @Autowired
    private ScheduleinstancesMapper<Scheduleinstances, ScheduleinstancesQuery> scheduleInstancesMapper;

    // 定义可选的排班星期
    private static final List<String> REPEAT_DAYS = Arrays.asList("monday", "tuesday", "wednesday", "thursday", "friday");

    // 定义时间段
    private static final List<String[]> DAY_TIME_SLOTS = Arrays.asList(
            new String[]{"08:30:00", "12:15:00"},
            new String[]{"13:45:00", "17:30:00"}
    );

    private static final String[] NIGHT_TIME_SLOT = {"19:00:00", "22:00:00"};  // 只有夜班的时间段

    // Java 8 兼容的 DAY_MAP 初始化
    private static final Map<String, Integer> DAY_MAP = new HashMap<>();
    static {
        DAY_MAP.put("monday", 1);
        DAY_MAP.put("tuesday", 2);
        DAY_MAP.put("wednesday", 3);
        DAY_MAP.put("thursday", 4);
        DAY_MAP.put("friday", 5);
    }

    // 跟踪所有已使用的组合 (repeatDay + startTime + endTime)
    private Set<String> usedTriples = new HashSet<>();

    public void generateRandomSchedules(String doctorId) {
        List<Scheduleinstances> instances = new ArrayList<>();
        Random random = new Random();
        LocalDate today = LocalDate.now();
        usedTriples.clear(); // 清空历史记录

        System.out.println("=== 生成新的随机排班数据 ===");

        for (Integer scheduleId : Arrays.asList(1, 2, 3, 4, 5, 6)) {
            Set<String> usedDaysForSchedule = new HashSet<>(); // 跟踪当前 schedule_id 已使用的星期
            int attempts = 0;

            while (usedDaysForSchedule.size() < 2 && attempts < 100) { // 防止无限循环
                // 随机选择星期几
                String repeatDay = REPEAT_DAYS.get(random.nextInt(REPEAT_DAYS.size()));

                // 选择时间段
                String[] timeSlot;
                if (scheduleId == 6) {
                    timeSlot = NIGHT_TIME_SLOT;
                } else {
                    timeSlot = DAY_TIME_SLOTS.get(random.nextInt(DAY_TIME_SLOTS.size()));
                }

                // 构建唯一标识 (确保全排列唯一)
                String tripleKey = repeatDay + "-" + timeSlot[0] + "-" + timeSlot[1];

                // 检查是否重复
                if (usedTriples.contains(tripleKey)) {
                    attempts++;
                    continue; // 如果组合已存在，跳过
                }

                // 检查当前 schedule_id 是否已使用该星期
                if (usedDaysForSchedule.contains(repeatDay)) {
                    attempts++;
                    continue;
                }

                // 生成日期
                LocalDate randomDate = getRandomDateByWeekday(today, repeatDay);

                // 创建实例
                Scheduleinstances instance = new Scheduleinstances();
                instance.setScheduleId(scheduleId);
                instance.setDate(java.sql.Date.valueOf(randomDate));
                instance.setRepeatDay(repeatDay);
                instance.setStartTime(timeSlot[0]);
                instance.setEndTime(timeSlot[1]);
                instance.setDoctorId(doctorId);
                instance.setCreatedAt(new Date());

                instances.add(instance);
                usedTriples.add(tripleKey);
                usedDaysForSchedule.add(repeatDay);

                System.out.printf("scheduleId: %d, repeatDay: %s, date: %s, time: %s-%s%n",
                        scheduleId, repeatDay, randomDate, timeSlot[0], timeSlot[1]);

                attempts = 0; // 重置尝试计数器
            }

            if (usedDaysForSchedule.size() < 2) {
                throw new RuntimeException("无法为 schedule_id=" + scheduleId + " 生成足够的唯一排班");
            }
        }

        // 批量插入
        scheduleInstancesMapper.insertOrUpdateBatch(instances);
    }

    /**
     * 生成指定星期几的日期（Java 8 兼容）
     */
    private LocalDate getRandomDateByWeekday(LocalDate today, String repeatDay) {
        int targetDay = DAY_MAP.getOrDefault(repeatDay.toLowerCase(), 1); // 处理大小写
        int currentDay = today.getDayOfWeek().getValue();

        // 计算下一个目标星期几
        int daysToAdd = (targetDay - currentDay + 7) % 7;
        daysToAdd = daysToAdd == 0 ? 7 : daysToAdd; // 如果当天是目标日，选下周

        // 随机选择0-3周后的同一天
        int weeksToAdd = new Random().nextInt(4);
        return today.plusDays(daysToAdd + weeksToAdd * 7);
    }
}