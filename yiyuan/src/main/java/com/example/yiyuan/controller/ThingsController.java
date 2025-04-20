package com.example.yiyuan.controller;

import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.po.Things;
import com.example.yiyuan.entity.query.ThingsQuery;
import com.example.yiyuan.entity.vo.ResponseVO;
import com.example.yiyuan.service.ThingsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: Controller *
 * @author:??
 * @date:2025/03/31
 */
@RestController
@RequestMapping("/things")
public class ThingsController extends ABaseController {

    @Resource
    private ThingsService thingsService;

    @PostMapping("/add")
    public ResponseVO add(HttpServletRequest request, String patientId, String thing, String date) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String doctorId = tokenUserInfoDto.getUserId();
        Things things = new Things();
        things.setThing(thing);
        things.setDoctorId(doctorId);
        things.setPatientId(patientId);
        things.setDate(date);
        thingsService.add(things);
        return getSuccessResponseVO(null);
    }

    @PostMapping("/delete")
    public ResponseVO delete(HttpServletRequest request, Integer id) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String doctorId = tokenUserInfoDto.getUserId();
        Things things = thingsService.getThingsById(String.valueOf(id));
        if (!things.getDoctorId().equals(doctorId)) {
            return getErrorResponseVO("您无权修改");
        } else {
            thingsService.deleteThingsById(String.valueOf(id));
            return getSuccessResponseVO("删除成功");

        }
    }

    @PostMapping("/alter")
    public ResponseVO alter(HttpServletRequest request, Integer id, String thing) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String doctorId = tokenUserInfoDto.getUserId();
        Things things = thingsService.getThingsById(String.valueOf(id));

        if (!things.getDoctorId().equals(doctorId)) {
            return getErrorResponseVO("您无权修改");
        } else {
            Things things1 = new Things();
            things1.setThing(thing);
            thingsService.updateThingsById(things1, String.valueOf(id));
            return getSuccessResponseVO("修改成功");
        }
    }

    @PostMapping("/alterDay")
    public ResponseVO alterDay(HttpServletRequest request, Integer id, String date) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String doctorId = tokenUserInfoDto.getUserId();
        Things things = thingsService.getThingsById(String.valueOf(id));

        if (!things.getDoctorId().equals(doctorId)) {
            return getErrorResponseVO("您无权修改");
        } else {

            Things things1 = new Things();
            things1.setDate(date);
            thingsService.updateThingsById(things1, String.valueOf(id));
            return getSuccessResponseVO("修改成功");
        }
    }

    @GetMapping("/getAll")
    public ResponseVO getAll(HttpServletRequest request, String patientId) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        ThingsQuery thingsQuery = new ThingsQuery();
        thingsQuery.setDoctorId(tokenUserInfoDto.getUserId());
        thingsQuery.setPatientId(patientId);
        List<Things> thingsList = thingsService.findListByParam(thingsQuery);
        return getSuccessResponseVO(thingsList);
    }

    @GetMapping("/getByDate")
    public ResponseVO getByDate(HttpServletRequest request, String date) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        ThingsQuery thingsQuery = new ThingsQuery();
        thingsQuery.setPatientId(tokenUserInfoDto.getUserId());
        thingsQuery.setDate(date);
        List<Things> thingsList = thingsService.findListByParam(thingsQuery);
        return getSuccessResponseVO(thingsList);
    }


}