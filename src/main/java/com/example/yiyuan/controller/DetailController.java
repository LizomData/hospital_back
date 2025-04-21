package com.example.yiyuan.controller;

import com.example.yiyuan.entity.config.Appconfig;
import com.example.yiyuan.entity.constants.Constants;
import com.example.yiyuan.entity.dto.BloodSugarData;
import com.example.yiyuan.entity.dto.BloodYaDto;
import com.example.yiyuan.entity.dto.ReportRequestDTO;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.po.*;
import com.example.yiyuan.entity.query.ReportQuery;
import com.example.yiyuan.entity.vo.*;
import com.example.yiyuan.service.*;
import com.example.yiyuan.service.impl.ChatServiceImpl;
import com.example.yiyuan.utils.FakeDataGenerator;
import com.example.yiyuan.utils.RandomDataGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/detail")
public class DetailController extends ABaseController {

    @Resource
    private ChatServiceImpl chatService;
    @Resource
    private Appconfig appconfig;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ScheduleinstancesService scheduleinstancesService;
    @Resource
    private DiseaseService diseaseService;
    @Resource
    private ReportService reportService;
    @Resource
    private EyeDiseaseService eyeDiseaseService;

    @GetMapping("/getEyes")
    public ResponseVO getEyes() {
        Random random = new Random();

        EyeDetails eyeDetails = new EyeDetails();
        eyeDetails.setSph(String.format("%.1f", -1.0 + random.nextDouble() * 9.0));
        eyeDetails.setCyl((String.format("%.1f", -0.25 + random.nextDouble() * 2.75)));
        eyeDetails.setAxis(String.valueOf(random.nextInt(181)));
        eyeDetails.setPd(String.format("%.1f", 50.0 + random.nextDouble() * 25.0));
        eyeDetails.setVisualAcuity(String.format("%.1f", 0.1 + random.nextDouble() * 0.9));

        return getSuccessResponseVO(eyeDetails);
    }

    @GetMapping("/getPatientInfo")
    public ResponseVO getPatientInfo(String patientId) {

        PatientInfoVO patientInfoVO = userInfoService.getPatientInfo(patientId);
        return getSuccessResponseVO(patientInfoVO);
    }

    @GetMapping("/getChange")
    public ResponseVO getChange() {
        List<String> statuses = Arrays.asList("病情稳定", "出现恶化", "开始好转", "明显改善", "维持稳定", "轻微反复", "恢复良好");
        List<String> dates = Arrays.asList("周一", "周二", "周三", "周四", "周五", "周六", "周日");
        int[] baseValues = {120, 200, 150, 80, 70, 110, 130};

        Random random = new Random();
        List<ChangeVO> healthDataList = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            int randomIndex = random.nextInt(statuses.size());
            String status = statuses.get(randomIndex);
            int baseValue = baseValues[randomIndex];
            int fluctuation = 10;
            int value = baseValue + random.nextInt(fluctuation * 2 + 1) - fluctuation;
            String date = dates.get(i);

            ChangeVO healthData = new ChangeVO();
            healthData.setValue(String.valueOf(value));
            healthData.setStatus(status);
            healthData.setDate(date);

            healthDataList.add(healthData);
        }

        return getSuccessResponseVO(healthDataList);
    }

    @GetMapping("/getCategory")
    public ResponseVO getCategory(String patientId) {


        List<CategoryVO> disease = diseaseService.getCategory(patientId);
        return getSuccessResponseVO(disease);
    }

    @GetMapping("/getLink")
    public ResponseVO getLink(String patientId) {


        NameVO sankeyData = new NameVO();
        List<NameVO.Node> nodes = new ArrayList<>();
        nodes.add(new NameVO.Node("糖尿病"));
        nodes.add(new NameVO.Node("青光眼"));
        nodes.add(new NameVO.Node("白内障"));
        nodes.add(new NameVO.Node("黄斑水肿"));
        nodes.add(new NameVO.Node("视力下降"));
        sankeyData.setNodes(nodes);
        List<NameVO.Link> links = new ArrayList<>();
        links.add(new NameVO.Link("糖尿病", "青光眼", 35));
        links.add(new NameVO.Link("糖尿病", "白内障", 20));
        links.add(new NameVO.Link("青光眼", "视力下降", 15));
        sankeyData.setLinks(links);

        return getSuccessResponseVO(sankeyData);
    }

    @GetMapping("/getXuetang")
    public ResponseVO<List<BloodSugarData>> getXuetang() {
        return getSuccessResponseVO(FakeDataGenerator.generateFakeData());
    }

    @GetMapping("/getXueYa")
    public ResponseVO<List<BloodYaDto>> getXueYa() {
        return getSuccessResponseVO(FakeDataGenerator.generateData());
    }

    @GetMapping("/getShiLi")
    public ResponseVO getShiLi() {
        List<Integer> randomNumbers = RandomDataGenerator.generateRandomNumbers(7, 120, 300);
        return getSuccessResponseVO(randomNumbers);
    }


    @GetMapping("/getDate")
    public ResponseVO getDate() {
        List<String> randomNumbers = RandomDataGenerator.getNextSevenDays();
        return getSuccessResponseVO(randomNumbers);
    }

    @GetMapping("/getDoctorAll")
    public ResponseVO getMyCheck(HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        if (userId == null || userId.isEmpty()) {
            return getErrorResponseVO("请登录!!");
        }

        List<DoctorAllVO> doctorAllVOS = scheduleinstancesService.getDoctorAll();

        return getSuccessResponseVO(doctorAllVOS);
    }

    @PostMapping("/upload")
    public ResponseVO upload(
            @NotEmpty String recordNo,
            @NotNull MultipartFile file
    ) throws IOException {
        // 校验文件非空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        // 构造安全存储路径
        String baseFolder = appconfig.getProjectFolder() + Constants.FILE_FOLDER_FILE;
        File targetFileFolder = new File(baseFolder, Constants.FILE_FOLDER_DETAILS_NAME);

        // 确保目录存在
        if (!targetFileFolder.exists() && !targetFileFolder.mkdirs()) {
            throw new IOException("无法创建目录: " + targetFileFolder);
        }

        // 构造目标文件对象
        File targetFile = new File(targetFileFolder, recordNo + ".jpg");

        // 写入文件（自动覆盖已存在的文件）
        file.transferTo(targetFile);

        return getSuccessResponseVO(targetFile.getAbsolutePath());
    }

    @PostMapping("/uploadReport")
    public ResponseVO uploadReport(@NotEmpty String recordNo, @NotNull MultipartFile file) throws IOException {


        String baseFoder = appconfig.getProjectFolder() + Constants.FILE_FOLDER_FILE;
        File targetFileFolder = new File(baseFoder + Constants.FILE_FOLDER_REPORT_NAME);
        if (!targetFileFolder.exists()) {
            targetFileFolder.mkdirs();
        }
        String filePath = targetFileFolder.getPath() + "/" + recordNo + ".pdf";
        File avatarFileFolder = new File(filePath);

        file.transferTo(avatarFileFolder);


        return getSuccessResponseVO(null);

    }


    @PostMapping("/AIChat")
    public ResponseVO AIChat(String content) {

        String request = content + "这是一名患者可能得病的数据以及概率，根据以上信息简述，以存在的可能问题以及建议分成两点，存在的可能问题和建议是给患者的，使用typora这样的富文本，每点不超过150字";
        System.out.println(request);
        String answer = ChatServiceImpl.chat(request);
        return getSuccessResponseVO(answer);

    }

    @PostMapping("saveReport")
    public ResponseVO saveReport(HttpServletRequest request,@RequestBody ReportRequestDTO reportDTO) {

        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        Report report = new Report();
        report.setDoctorId(tokenUserInfoDto.getUserId());
        report.setPatientId(reportDTO.getPatientId());
        report.setCheckDate(reportDTO.getCheckDate());
        report.setRecordNo(reportDTO.getRecordNo());
        report.setDate(reportDTO.getDate());
        report.setPatientName(reportDTO.getPatientName());
        reportService.add(report);

        for (ReportRequestDTO.EyeDiseaseDTO dto : reportDTO.getEyeDiseases()) {
            EyeDisease disease = new EyeDisease();
            disease.setRecordNo(report.getRecordNo());
            disease.setCategory(dto.getCategory());
            disease.setProbability(String.valueOf(dto.getProbability()));
            disease.setStatus(dto.getStatus());
            disease.setDescription(dto.getDescription());
            disease.setSuggestion(dto.getSuggestion());
            eyeDiseaseService.add(disease);
        }
        return getSuccessResponseVO("保存成功");

    }

    @GetMapping("getReportsByDoctor")
    public ResponseVO getReportsByDoctor(HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);
        String doctorId = tokenUserInfo.getUserId();
        List<ReportRequestDTO> list = reportService.getReportsByDoctorId(doctorId);
        return getSuccessResponseVO(list);
    }

    @GetMapping("getReportsByNo")
    public ResponseVO getReportsByNo(HttpServletRequest request,String recordNo) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);
        String doctorId = tokenUserInfo.getUserId();

        ReportRequestDTO list = reportService.getReportsByNo(doctorId,recordNo);
        return getSuccessResponseVO(list);
    }

    @GetMapping("getMyReports")
    public ResponseVO getMyReports(HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(request);
        String userId = tokenUserInfo.getUserId();

        ReportQuery reportQuery = new ReportQuery();
        reportQuery.setPatientId(userId);
        List<Report> result = reportService.findListByParam(reportQuery);
        return getSuccessResponseVO(result);
    }

    @PostMapping("submitComment")
    public ResponseVO submitComment(String recordNo,String title,String comment) {


        reportService.submitComment(recordNo,title,comment);
        return getSuccessResponseVO("成功");
    }
    @PostMapping("getComment")
    public ResponseVO getComment(HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        ReportQuery reportQuery = new ReportQuery();
        reportQuery.setDoctorId(userId);
        List<Report> result = reportService.findListByParam(reportQuery);
        return getSuccessResponseVO(result);
    }

//    @GetMapping
//    public ResponseVO getReports(
//            @RequestParam String doctorId,
//            @RequestParam String patientId) {
//        try {
//            List<Report> reports = reportService.getReportsByDoctorAndPatient(doctorId, patientId);
//            return getSuccessResponseVO(reports);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("查询报告失败: " + e.getMessage());
//        }
//    }
}
