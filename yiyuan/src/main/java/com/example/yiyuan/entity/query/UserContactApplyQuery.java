package com.example.yiyuan.entity.query;



/**
 *
 * @Description:查询对象 *
 * @author:??
 * @date:2025/02/06
 *
 */
public class UserContactApplyQuery extends BaseQuery {
	/**
	 * 自增ID
	 */
	private Integer applyId;

	/**
	 * 申请人ID
	 */
	private String applyUserId;

	private String applyUserIdFuzzy;

	/**
	 * 接收人ID
	 */
	private String receiveUserId;

	private String receiveUserIdFuzzy;

	/**
	 * 最后申请时间
	 */
	private Long lastApplyTime;

	/**
	 * 状态 0:待处理 1:已同意 2:已拒绝 3:已拉黑
	 */
	private Integer status;

	/**
	 * 申请信息
	 */
	private String applyInfo;

	private String applyInfoFuzzy;
	/**
	 * 申请次数
	 */
	private String number;

	private String numberFuzzy;
	private Boolean queryContactInfo;

	private String nickName;
	private Integer sex;
	private Integer userStatus;
	private Long lastApplyTimestamp;
	/**
	 * 咨询类型
	 */
	private String type;

	private String typeFuzzy;

	/**
	 * 持续时间
	 */
	private String duration;

	private String durationFuzzy;

	/**
	 * 左眼病情
	 */
	private String leftEye;

	private String leftEyeFuzzy;

	/**
	 * 右眼病情
	 */
	private String rightEye;

	private String rightEyeFuzzy;

	/**
	 * 严重程度
	 */
	private Integer painLevel;

	/**
	 * 附件数量
	 */
	private String file;

	private String fileFuzzy;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Long getLastApplyTimestamp() {
		return lastApplyTimestamp;
	}

	public void setLastApplyTimestamp(Long lastApplyTimestamp) {
		this.lastApplyTimestamp = lastApplyTimestamp;
	}

	public Boolean getQueryContactInfo() {
		return queryContactInfo;
	}

	public void setQueryContactInfo(Boolean queryContactInfo) {
		this.queryContactInfo = queryContactInfo;
	}

	public void setApplyId(Integer applyId){
		this.applyId = applyId;
	}

	public Integer getApplyId(){
		return this.applyId;
	}

	public void setApplyUserId(String applyUserId){
		this.applyUserId = applyUserId;
	}

	public String getApplyUserId(){
		return this.applyUserId;
	}

	public void setReceiveUserId(String receiveUserId){
		this.receiveUserId = receiveUserId;
	}

	public String getReceiveUserId(){
		return this.receiveUserId;
	}


	public void setLastApplyTime(Long lastApplyTime){
		this.lastApplyTime = lastApplyTime;
	}

	public Long getLastApplyTime(){
		return this.lastApplyTime;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setApplyInfo(String applyInfo){
		this.applyInfo = applyInfo;
	}

	public String getApplyInfo(){
		return this.applyInfo;
	}

	public void setApplyUserIdFuzzy(String applyUserIdFuzzy){
		this.applyUserIdFuzzy = applyUserIdFuzzy;
	}

	public String getApplyUserIdFuzzy(){
		return this.applyUserIdFuzzy;
	}

	public void setReceiveUserIdFuzzy(String receiveUserIdFuzzy){
		this.receiveUserIdFuzzy = receiveUserIdFuzzy;
	}

	public String getReceiveUserIdFuzzy(){
		return this.receiveUserIdFuzzy;
	}

	public void setApplyInfoFuzzy(String applyInfoFuzzy){
		this.applyInfoFuzzy = applyInfoFuzzy;
	}

	public String getApplyInfoFuzzy(){
		return this.applyInfoFuzzy;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeFuzzy() {
		return typeFuzzy;
	}

	public void setTypeFuzzy(String typeFuzzy) {
		this.typeFuzzy = typeFuzzy;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDurationFuzzy() {
		return durationFuzzy;
	}

	public void setDurationFuzzy(String durationFuzzy) {
		this.durationFuzzy = durationFuzzy;
	}

	public String getLeftEye() {
		return leftEye;
	}

	public void setLeftEye(String leftEye) {
		this.leftEye = leftEye;
	}

	public String getLeftEyeFuzzy() {
		return leftEyeFuzzy;
	}

	public void setLeftEyeFuzzy(String leftEyeFuzzy) {
		this.leftEyeFuzzy = leftEyeFuzzy;
	}

	public String getRightEye() {
		return rightEye;
	}

	public void setRightEye(String rightEye) {
		this.rightEye = rightEye;
	}

	public String getRightEyeFuzzy() {
		return rightEyeFuzzy;
	}

	public void setRightEyeFuzzy(String rightEyeFuzzy) {
		this.rightEyeFuzzy = rightEyeFuzzy;
	}

	public Integer getPainLevel() {
		return painLevel;
	}

	public void setPainLevel(Integer painLevel) {
		this.painLevel = painLevel;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFileFuzzy() {
		return fileFuzzy;
	}

	public void setFileFuzzy(String fileFuzzy) {
		this.fileFuzzy = fileFuzzy;
	}

	public String getNumberFuzzy() {
		return numberFuzzy;
	}

	public void setNumberFuzzy(String numberFuzzy) {
		this.numberFuzzy = numberFuzzy;
	}
}