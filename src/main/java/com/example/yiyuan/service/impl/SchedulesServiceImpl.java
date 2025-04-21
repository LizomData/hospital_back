package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.dto.ScheduleDTO;
import com.example.yiyuan.entity.dto.ScheduleInstanceDTO;
import com.example.yiyuan.entity.dto.ShiftDTO;
import com.example.yiyuan.entity.dto.UserContactSearchResultDto;
import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.po.Scheduleinstances;
import com.example.yiyuan.entity.po.Schedules;
import com.example.yiyuan.entity.po.Shifts;
import com.example.yiyuan.entity.query.ScheduleinstancesQuery;
import com.example.yiyuan.entity.query.SchedulesQuery;
import com.example.yiyuan.entity.query.ShiftsQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.mappers.ScheduleinstancesMapper;
import com.example.yiyuan.mappers.SchedulesMapper;
import com.example.yiyuan.mappers.ShiftsMapper;
import com.example.yiyuan.service.ScheduleinstancesService;
import com.example.yiyuan.service.SchedulesService;
import com.example.yiyuan.service.ShiftsService;
import com.example.yiyuan.utils.CopyTools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Service *
 * @author:??
 * @date:2025/03/27
 */
@Service("schedulesService")
public class SchedulesServiceImpl implements SchedulesService {

    @Resource
    private SchedulesMapper<Schedules, SchedulesQuery> schedulesMapper;
    @Resource
    private ScheduleinstancesMapper<Scheduleinstances, ScheduleinstancesQuery> scheduleinstancesMapper;
    @Resource
    private ShiftsMapper<Shifts, ShiftsQuery> shiftsMapper;
    @Resource
    private SchedulesService schedulesService;
    @Resource
    private ScheduleinstancesService scheduleinstancesService;
    @Resource
    private ShiftsService shiftsService;
    @Resource
    private UserContactServiceImpl userContactService;

    /**
     * 根据条件查询列表
     */
    public List<Schedules> findListByParam(SchedulesQuery query) {
        return this.schedulesMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(SchedulesQuery query) {
        return this.schedulesMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<Schedules> findListByPage(SchedulesQuery query) {
        Integer count = this.findCountByParam(query);
        Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<Schedules> list = this.findListByParam(query);
        PaginationResultVO<Schedules> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(Schedules bean) {
        return this.schedulesMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<Schedules> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.schedulesMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<Schedules> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.schedulesMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据Id查询
     */
    public Schedules getSchedulesById(Integer id) {
        return this.schedulesMapper.selectById(id);
    }

    /**
     * 根据Id更新
     */
    public Integer updateSchedulesById(Schedules bean, Integer id) {
        return this.schedulesMapper.updateById(bean, id);
    }

    /**
     * 根据Id删除
     */
    public Integer deleteSchedulesById(Integer id) {
        return this.schedulesMapper.deleteById(id);
    }

    @Override
    public List<ScheduleDTO> getAllSchedules(String userId) throws BusinessException {
        List<Schedules> scheduless = schedulesMapper.getAllSchedules();
        List<ScheduleDTO> schedules = CopyTools.copyList(scheduless, ScheduleDTO.class);
        System.out.println("scheduless:" + scheduless);
        System.out.println("schedules:" + schedules);

        for (ScheduleDTO schedule : schedules) {
            System.out.println("sc:" + schedule.getId());
            // 根据schedule_id获取实例
            ScheduleinstancesQuery scheduleinstancesQuery = new ScheduleinstancesQuery();
            scheduleinstancesQuery.setScheduleId(schedule.getId());
            scheduleinstancesQuery.setDoctorId(userId);
            List<Scheduleinstances> instances = scheduleinstancesService.findListByParam(scheduleinstancesQuery);
            List<ScheduleInstanceDTO> instancess = CopyTools.copyList(instances, ScheduleInstanceDTO.class);

            schedule.setInstances(instancess);

            // 对于每个排班实例，获取相关的换班记录
            for (ScheduleInstanceDTO instance : instancess) {
                ShiftsQuery shiftsQuery = new ShiftsQuery();
                shiftsQuery.setScheduleInstanceId(instance.getId());

                // 查询换班信息
                List<Shifts> shift = shiftsService.findListByParam(shiftsQuery);

                // 复制数据
                List<ShiftDTO> shifts = CopyTools.copyList(shift, ShiftDTO.class);

                for (ShiftDTO shiftDTO : shifts) {
                    UserContactSearchResultDto resultDto = userContactService.searchContact(userId,shiftDTO.getNewDoctorId());
                    shiftDTO.setNewDoctorId(resultDto.getNickName());
                }

                // **避免 schedule.getShifts() 为空**
                if (schedule.getShifts() == null) {
                    schedule.setShifts(new ArrayList<>());
                }

                if (!shifts.isEmpty()) {
                    schedule.getShifts().addAll(shifts);
                }
            }

        }
        return schedules;
    }
}


