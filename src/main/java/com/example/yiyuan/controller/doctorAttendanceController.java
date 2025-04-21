package com.example.yiyuan.controller;

import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.po.DoctorAttendance;
import com.example.yiyuan.entity.query.DoctorAttendanceQuery;
import com.example.yiyuan.entity.vo.ResponseVO;
import com.example.yiyuan.mappers.DoctorAttendanceMapper;
import com.example.yiyuan.service.DoctorAttendanceService;
import com.example.yiyuan.utils.CopyTools;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class doctorAttendanceController extends ABaseController {

    @Resource
    private DoctorAttendanceService doctorAttendanceService;
    @Resource
    private DoctorAttendanceMapper<DoctorAttendance, DoctorAttendanceQuery> doctorAttendanceMapper;

    @PostMapping("/sign")
    public ResponseVO symptomCount(HttpServletRequest request, int status) {
        //status为 1视为迟到
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        String firstLetter = String.valueOf(userId.charAt(0));
        if (firstLetter.equals("U")) {
            return getErrorResponseVO("您无权限");
        }

        DoctorAttendance doctorAttendance = new DoctorAttendance();
        doctorAttendance.setDoctorId(userId);


        DoctorAttendanceQuery doctorAttendanceQuery = new DoctorAttendanceQuery();
        doctorAttendanceQuery.setDoctorId(userId);
        List<DoctorAttendance> attendanceList = doctorAttendanceService.findListByParam(doctorAttendanceQuery);

        if (attendanceList == null || attendanceList.isEmpty()) {
            if (status == 1) {
                doctorAttendance.setWrong(1);
                doctorAttendance.setRight(0);
            } else {
                doctorAttendance.setWrong(0);
                doctorAttendance.setRight(1);
            }
            doctorAttendanceService.add(doctorAttendance);

        } else {
            DoctorAttendance attendance = attendanceList.get(0);
            Integer right = attendance.getRight();
            Integer wrong = attendance.getWrong();
            String id = String.valueOf(attendance.getId());
            if (status == 1) {
                wrong++;
            } else {
                right++;
            }
            doctorAttendance.setWrong(wrong);
            doctorAttendance.setRight(right);
            doctorAttendance.setTime(new Date());
            doctorAttendanceService.updateDoctorAttendanceById(doctorAttendance, Integer.valueOf(id));
        }


        return getSuccessResponseVO(null);
    }

    @GetMapping("/getMyCheck")
    public ResponseVO getMyCheck(HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();

        DoctorAttendance doctorAttendance = new DoctorAttendance();
        doctorAttendance.setDoctorId(userId);

        DoctorAttendanceQuery doctorAttendanceQuery = new DoctorAttendanceQuery();
        doctorAttendanceQuery.setDoctorId(userId);

        List<DoctorAttendance> attendanceList = doctorAttendanceService.findListByParam(doctorAttendanceQuery);
        if (attendanceList == null || attendanceList.isEmpty()) {
            doctorAttendance.setWrong(0);
            doctorAttendance.setRight(0);
            doctorAttendanceService.add(doctorAttendance);

        }
        List<DoctorAttendance> attendanceList1 = doctorAttendanceService.findListByParam(doctorAttendanceQuery);
        DoctorAttendance doctorAttendance1 = attendanceList1.get(0);
        LocalDateTime today = LocalDateTime.now();


        Date recordDate = doctorAttendance1.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = sdf.format(recordDate);

        Boolean a = doctorAttendance1.getRight()==0&&doctorAttendance1.getWrong()==0;

        LocalDateTime recordTime = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (a){
            doctorAttendance1.setFlag(1);
        }
        if (recordTime.toLocalDate().equals(today.toLocalDate())&&!a) {
            doctorAttendance1.setFlag(0);
        }
        if (!recordTime.toLocalDate().equals(today.toLocalDate())&&!a) {

            doctorAttendance1.setFlag(1);
        }

        return getSuccessResponseVO(doctorAttendance1);
    }
}
