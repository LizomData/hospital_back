package com.example.yiyuan.entity.query;

import java.util.Date;


/**
 *
 * @Description:用户信息查询对象 *
 * @author:??
 * @date:2025/03/31
 *
 */
public class UserInfoQuery extends BaseQuery {
	/**
	 * 用户id
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 邮箱
	 */
	private String email;

	private String emailFuzzy;

	/**
	 * 昵称
	 */
	private String nickName;

	private String nickNameFuzzy;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 以往病史
	 */
	private String medicalHistory;

	private String medicalHistoryFuzzy;

	/**
	 * 0 直接加入 1 同意后加好友
	 */
	private Integer joinType;

	/**
	 * 性别
	 0：女 1：男
	 */
	private Integer sex;

	/**
	 * 密码
	 */
	private String password;

	private String passwordFuzzy;

	/**
	 * 个性签名
	 */
	private String personalSignature;

	private String personalSignatureFuzzy;

	/**
	 * 状态 0：就诊中 1：已出院
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	private String lastLoginTimeStart;

	private String lastLoginTimeEnd;

	/**
	 * 地区
	 */
	private String areaName;

	private String areaNameFuzzy;

	/**
	 * 地区编号
	 */
	private String areaCode;

	private String areaCodeFuzzy;

	/**
	 * 最后离开时间
	 */
	private Long lastOffTime;

	/**
	 * 1表示主任医生，2表示副医生
	 */
	private Integer grade;

	/**
	 * 擅长领域
	 */
	private String excellent;

	private String excellentFuzzy;

	/**
	 * 经历
	 */
	private String experience;

	private String experienceFuzzy;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setNickName(String nickName){
		this.nickName = nickName;
	}

	public String getNickName(){
		return this.nickName;
	}

	public void setAge(Integer age){
		this.age = age;
	}

	public Integer getAge(){
		return this.age;
	}

	public void setMedicalHistory(String medicalHistory){
		this.medicalHistory = medicalHistory;
	}

	public String getMedicalHistory(){
		return this.medicalHistory;
	}

	public void setJoinType(Integer joinType){
		this.joinType = joinType;
	}

	public Integer getJoinType(){
		return this.joinType;
	}

	public void setSex(Integer sex){
		this.sex = sex;
	}

	public Integer getSex(){
		return this.sex;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setPersonalSignature(String personalSignature){
		this.personalSignature = personalSignature;
	}

	public String getPersonalSignature(){
		return this.personalSignature;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setLastLoginTime(Date lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime(){
		return this.lastLoginTime;
	}

	public void setAreaName(String areaName){
		this.areaName = areaName;
	}

	public String getAreaName(){
		return this.areaName;
	}

	public void setAreaCode(String areaCode){
		this.areaCode = areaCode;
	}

	public String getAreaCode(){
		return this.areaCode;
	}

	public void setLastOffTime(Long lastOffTime){
		this.lastOffTime = lastOffTime;
	}

	public Long getLastOffTime(){
		return this.lastOffTime;
	}

	public void setGrade(Integer grade){
		this.grade = grade;
	}

	public Integer getGrade(){
		return this.grade;
	}

	public void setExcellent(String excellent){
		this.excellent = excellent;
	}

	public String getExcellent(){
		return this.excellent;
	}

	public void setExperience(String experience){
		this.experience = experience;
	}

	public String getExperience(){
		return this.experience;
	}

	public void setUserIdFuzzy(String userIdFuzzy){
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getUserIdFuzzy(){
		return this.userIdFuzzy;
	}

	public void setEmailFuzzy(String emailFuzzy){
		this.emailFuzzy = emailFuzzy;
	}

	public String getEmailFuzzy(){
		return this.emailFuzzy;
	}

	public void setNickNameFuzzy(String nickNameFuzzy){
		this.nickNameFuzzy = nickNameFuzzy;
	}

	public String getNickNameFuzzy(){
		return this.nickNameFuzzy;
	}

	public void setMedicalHistoryFuzzy(String medicalHistoryFuzzy){
		this.medicalHistoryFuzzy = medicalHistoryFuzzy;
	}

	public String getMedicalHistoryFuzzy(){
		return this.medicalHistoryFuzzy;
	}

	public void setPasswordFuzzy(String passwordFuzzy){
		this.passwordFuzzy = passwordFuzzy;
	}

	public String getPasswordFuzzy(){
		return this.passwordFuzzy;
	}

	public void setPersonalSignatureFuzzy(String personalSignatureFuzzy){
		this.personalSignatureFuzzy = personalSignatureFuzzy;
	}

	public String getPersonalSignatureFuzzy(){
		return this.personalSignatureFuzzy;
	}

	public void setCreateTimeStart(String createTimeStart){
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeStart(){
		return this.createTimeStart;
	}

	public void setCreateTimeEnd(String createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}

	public String getCreateTimeEnd(){
		return this.createTimeEnd;
	}

	public void setLastLoginTimeStart(String lastLoginTimeStart){
		this.lastLoginTimeStart = lastLoginTimeStart;
	}

	public String getLastLoginTimeStart(){
		return this.lastLoginTimeStart;
	}

	public void setLastLoginTimeEnd(String lastLoginTimeEnd){
		this.lastLoginTimeEnd = lastLoginTimeEnd;
	}

	public String getLastLoginTimeEnd(){
		return this.lastLoginTimeEnd;
	}

	public void setAreaNameFuzzy(String areaNameFuzzy){
		this.areaNameFuzzy = areaNameFuzzy;
	}

	public String getAreaNameFuzzy(){
		return this.areaNameFuzzy;
	}

	public void setAreaCodeFuzzy(String areaCodeFuzzy){
		this.areaCodeFuzzy = areaCodeFuzzy;
	}

	public String getAreaCodeFuzzy(){
		return this.areaCodeFuzzy;
	}

	public void setExcellentFuzzy(String excellentFuzzy){
		this.excellentFuzzy = excellentFuzzy;
	}

	public String getExcellentFuzzy(){
		return this.excellentFuzzy;
	}

	public void setExperienceFuzzy(String experienceFuzzy){
		this.experienceFuzzy = experienceFuzzy;
	}

	public String getExperienceFuzzy(){
		return this.experienceFuzzy;
	}

}