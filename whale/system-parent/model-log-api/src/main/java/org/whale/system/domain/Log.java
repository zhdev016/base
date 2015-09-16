package org.whale.system.domain;

import java.io.Serializable;

import org.whale.system.annotation.jdbc.Column;
import org.whale.system.annotation.jdbc.Id;
import org.whale.system.annotation.jdbc.Table;
import org.whale.system.common.util.Strings;


@Table(value="sys_log", cnName="日志")
public class Log implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final Integer RS_NORMAL = 1;
	public static final Integer RS_SysException = 2;
	public static final Integer RS_OrmException = 3;
	public static final Integer RS_RunTimeException = 4;
	public static final Integer RS_BusinessException = 5;
	public static final Integer RS_OTHER = 0;

	@Id(auto=false)
	@Column(cnName="id")
	private String id;
	
	@Column(cnName="所属应用")
	private String appId;
	
	@Column(cnName="操作方法")
    private String opt;
	
	@Column(cnName="对象名称")
	private String cnName;
	
	@Column(cnName="表名称")
	private String tableName;
	
	@Column(cnName="请求路径")
	private String uri;
	
	@Column(cnName="执行sql语句")
	private String sqlStr;
	
	@Column(cnName="请求参数")
    private String argStr;
    
	@Column(cnName="处理结果")
    private String rsStr;
    
	@Column(cnName="ip地址")
    private String ip;
	
	@Column(cnName="创建时间")
    private Long createTime;
	
	@Column(cnName="操作人")
    private String userName;
	
	@Column(cnName="调用链顺序")
	private Integer callOrder;
	
	@Column(cnName="方法耗时")
	private Integer methodCostTime;
	
	@Column(cnName="调用耗时")
	private Integer costTime;
	
	//1 正常， 2. SysException 3.OrmException 4.RunTimeException 0.other
	@Column(cnName="结果类型")
	private Integer rsType;
	
	
	//参数
	private Object arg;
	
	private Object rs;
	
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Object getArg() {
		return arg;
	}
	public void setArg(Object arg) {
		this.arg = arg;
	}
	
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getCallOrder() {
		return callOrder;
	}
	public void setCallOrder(Integer callOrder) {
		this.callOrder = callOrder;
	}
	public Integer getMethodCostTime() {
		return methodCostTime;
	}
	public void setMethodCostTime(Integer methodCostTime) {
		this.methodCostTime = methodCostTime;
	}
	public Integer getCostTime() {
		return costTime;
	}
	public void setCostTime(Integer costTime) {
		this.costTime = costTime;
	}
	public Integer getRsType() {
		return rsType;
	}
	public void setRsType(Integer rsType) {
		this.rsType = rsType;
	}
	public String getSqlStr() {
		return sqlStr;
	}
	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}
	public String getArgStr() {
		return argStr;
	}
	public void setArgStr(String argStr) {
		if(Strings.isNotBlank(argStr) && argStr.length() > 1024){
			argStr = argStr.substring(0, 1023);
		}
		this.argStr = argStr;
	}
	public String getRsStr() {
		return rsStr;
	}
	public void setRsStr(String rsStr) {
		if(Strings.isNotBlank(rsStr) && rsStr.length() > 1024){
			rsStr = rsStr.substring(0, 1023);
		}
		this.rsStr = rsStr;
	}
	public Object getRs() {
		return rs;
	}
	public void setRs(Object rs) {
		this.rs = rs;
	}
	@Override
	public String toString() {
		return "Log [id=" + id + ", appId=" + appId + ", opt=" + opt
				+ ", cnName=" + cnName + ", tableName=" + tableName + ", uri="
				+ uri + ", sqlStr=" + sqlStr + ", argStr=" + argStr
				+ ", rsStr=" + rsStr + ", ip=" + ip + ", createTime="
				+ createTime + ", userName=" + userName + ", callOrder="
				+ callOrder + ", methodCostTime=" + methodCostTime
				+ ", costTime=" + costTime + ", rsType=" + rsType + ", arg="
				+ arg + ", rs=" + rs + "]";
	}

	
}