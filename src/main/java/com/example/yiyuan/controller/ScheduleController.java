package com.example.yiyuan.controller;

import com.example.yiyuan.entity.dto.ScheduleDTO;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.po.Scheduleinstances;
import com.example.yiyuan.entity.query.ScheduleinstancesQuery;
import com.example.yiyuan.entity.query.ShiftsQuery;
import com.example.yiyuan.entity.vo.AbleApplyVO;
import com.example.yiyuan.entity.vo.ResponseVO;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.service.ScheduleinstancesService;
import com.example.yiyuan.service.SchedulesService;
import com.example.yiyuan.service.ShiftsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/dayPlan")
public class ScheduleController extends ABaseController {

    @Autowired
    private SchedulesService scheduleService;
    @Resource
    private ScheduleinstancesService scheduleinstancesService;
    @Resource
    private ShiftsService shiftsService;

    @CrossOrigin
    @GetMapping("/schedules")
    public ResponseVO getSchedules(HttpServletRequest request) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        return getSuccessResponseVO(scheduleService.getAllSchedules(userId));
    }

    @GetMapping("/getMyTime")
    public ResponseVO getMyTime(HttpServletRequest request, String day) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();

        if (day.equals("sunday") || day.equals("saturday")) {
            return getSuccessResponseVO("今日休息，尽情happy");
        }
        ScheduleinstancesQuery scheduleinstancesQuery = new ScheduleinstancesQuery();
        scheduleinstancesQuery.setDoctorId(userId);
        scheduleinstancesQuery.setRepeatDay(day);
        scheduleinstancesQuery.setEndTime("22:00:00");
        List<Scheduleinstances> scheduleinstances = scheduleinstancesService.findListByParam(scheduleinstancesQuery);
        if (scheduleinstances == null || scheduleinstances.isEmpty()) {
            return getSuccessResponseVO("08:30 - 17:30");
        } else {
            return getSuccessResponseVO("08:30 - 22:00:00");
        }
    }

    @GetMapping("/getAnbleApply")
    public ResponseVO getAnbleApply(String doctorId) throws BusinessException {
        List<AbleApplyVO> ableApplyVOS = scheduleinstancesService.getAnbleApply(doctorId);
        return getSuccessResponseVO(ableApplyVOS);
    }

//    @CrossOrigin
//    @GetMapping("/getMyShifts")
//    public ResponseVO getMyShifts(HttpServletRequest request)  {
//        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
//        String userId = tokenUserInfoDto.getUserId();
//        ShiftsQuery shiftsQuery = new ShiftsQuery();
//        shiftsQuery.setNewDoctorId(userId);
//        return getSuccessResponseVO(shiftsService.findListByParam(shiftsQuery));
//    }


}
