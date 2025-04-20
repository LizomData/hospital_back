package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.config.Appconfig;
import com.example.yiyuan.entity.constants.Constants;
import com.example.yiyuan.entity.dto.SysSettingDto;
import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.enums.ResponseCodeEnum;
import com.example.yiyuan.entity.po.UserContactApply;
import com.example.yiyuan.entity.po.UserOutlineApply;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.query.UserOutlineApplyQuery;
import com.example.yiyuan.entity.vo.DoctorcommentVO;
import com.example.yiyuan.entity.vo.OutlineApplyVO;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.entity.vo.SymptomCountVO;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.mappers.UserOutlineApplyMapper;
import com.example.yiyuan.redis.RedisComponent;
import com.example.yiyuan.service.UserOutlineApplyService;
import com.example.yiyuan.utils.StringTools;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Description: Service *
 * @author:??
 * @date:2025/03/26
 */
@Service("userOutlineApplyService")
public class UserOutlineApplyServiceImpl implements UserOutlineApplyService {

    Logger logger = LoggerFactory.getLogger(UserContactApplyServiceImpl.class);
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private Appconfig appconfig;
    @Resource
    private UserOutlineApplyMapper<UserOutlineApply, UserOutlineApplyQuery> userOutlineApplyMapper;

    /**
     * 根据条件查询列表
     */
    public List<UserOutlineApply> findListByParam(UserOutlineApplyQuery query) {
        return this.userOutlineApplyMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(UserOutlineApplyQuery query) {
        return this.userOutlineApplyMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<UserOutlineApply> findListByPage(UserOutlineApplyQuery query) {
        Integer count = this.findCountByParam(query);
        Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<UserOutlineApply> list = this.findListByParam(query);
        PaginationResultVO<UserOutlineApply> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(UserOutlineApply bean) {
        return this.userOutlineApplyMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<UserOutlineApply> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userOutlineApplyMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<UserOutlineApply> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userOutlineApplyMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据ApplyId查询
     */
    public UserOutlineApply getUserOutlineApplyByApplyId(Integer applyId) {
        return this.userOutlineApplyMapper.selectByApplyId(applyId);
    }

    /**
     * 根据ApplyId更新
     */
    public Integer updateUserOutlineApplyByApplyId(UserOutlineApply bean, Integer applyId) {
        return this.userOutlineApplyMapper.updateByApplyId(bean, applyId);
    }

    /**
     * 根据ApplyId删除
     */
    public Integer deleteUserOutlineApplyByApplyId(Integer applyId) {
        return this.userOutlineApplyMapper.deleteByApplyId(applyId);
    }

    /**
     * 根据ApplyUserIdAndReceiveUserId查询
     */
    public UserOutlineApply getUserOutlineApplyByApplyUserIdAndReceiveUserId(String applyUserId, String receiveUserId) {
        return this.userOutlineApplyMapper.selectByApplyUserIdAndReceiveUserId(applyUserId, receiveUserId);
    }

    /**
     * 根据ApplyUserIdAndReceiveUserId更新
     */
    public Integer updateUserOutlineApplyByApplyUserIdAndReceiveUserId(UserOutlineApply bean, String applyUserId, String receiveUserId) {
        return this.userOutlineApplyMapper.updateByApplyUserIdAndReceiveUserId(bean, applyUserId, receiveUserId);
    }

    /**
     * 根据ApplyUserIdAndReceiveUserId删除
     */
    public Integer deleteUserOutlineApplyByApplyUserIdAndReceiveUserId(String applyUserId, String receiveUserId) {
        return this.userOutlineApplyMapper.deleteByApplyUserIdAndReceiveUserId(applyUserId, receiveUserId);
    }

    @Override
    public void saveApplyFile(String userId, int i, Integer applyId, MultipartFile file) throws BusinessException {
        UserOutlineApply userOutlineApply = userOutlineApplyMapper.selectByApplyId(applyId);
        logger.info("userOutlineApply+" + userOutlineApply);
        if (userOutlineApply == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        if (userOutlineApply == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (!userOutlineApply.getApplyUserId().equals(userId)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        SysSettingDto sysSettingDto = redisComponent.getSysSetting();
        String fileSuffix = StringTools.getFileSuffix(file.getOriginalFilename());
        if (!StringTools.isEmpty(fileSuffix)
                && ArrayUtils.contains(Constants.IMAGE_SUFFIX_LIST, fileSuffix.toLowerCase())
                && file.getSize() > sysSettingDto.getMaxImageSize() * Constants.FILE_SIZE_MB) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        } else if (!StringTools.isEmpty(fileSuffix)
                && ArrayUtils.contains(Constants.VIDEO_SUFFIX_LIST, fileSuffix.toLowerCase())
                && file.getSize() > sysSettingDto.getMaxVideoSize() * Constants.FILE_SIZE_MB) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        } else if (!StringTools.isEmpty(fileSuffix)
                && !ArrayUtils.contains(Constants.IMAGE_SUFFIX_LIST, fileSuffix.toLowerCase())
                && !ArrayUtils.contains(Constants.VIDEO_SUFFIX_LIST, fileSuffix.toLowerCase())
                && file.getSize() > sysSettingDto.getMaxFileSize() * Constants.FILE_SIZE_MB
        ) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        String fileName = file.getOriginalFilename();
        logger.info("上传fileName:" + fileName);
//		String fileExtName = StringTools.getFileSuffix(fileName);
        String fileExtName = ".jpg";
        logger.info("上传fileExtName:" + fileExtName);
        //12.jpg
        String fileRealName = applyId + "_" + (i + 1) + fileExtName;
        logger.info("上传fileRealName:" + fileRealName);
        File folder = new File(appconfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_OUTLINEAPPLY_NAME);

        if (!folder.exists()) {
            folder.mkdirs();
        }
        File uploadFile = new File(folder.getPath() + "/" + fileRealName);
        logger.info("上传uploadFile:" + uploadFile);

        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            logger.error("上传文件失败", e);
            throw new BusinessException("文件上传失败");
        }

    }

    @Override
    public List<OutlineApplyVO> loadOnlineApply(UserOutlineApplyQuery userContactApplyQuery) {
        return userOutlineApplyMapper.loadOnlineApply(userContactApplyQuery);

    }

    @Override
    public List<SymptomCountVO> countSymptomsByUserId(String userId) {
        return userOutlineApplyMapper.countSymptomsByUserId(userId);
    }

    @Override
    public List<DoctorcommentVO> selectComment(String userId) {
        return userOutlineApplyMapper.selectComment(userId);
    }


}