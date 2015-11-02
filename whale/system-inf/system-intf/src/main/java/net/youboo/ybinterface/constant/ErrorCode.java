package net.youboo.ybinterface.constant;

public enum ErrorCode {

	SUCCESS(0, "处理成功"),
	INF_SYSTEM_ERROR(1000, "接口系统错误"),
	ENCRYPT_ERROR(1001, "密文错误"),
	AUTH_ERROR(1002, "权限错误"),
	DATA_ERROR(1003, "Body数据错误"),
	PARAM_ERROR(1004, "系统参数错误"),
	SIGN_ERROR(1005, "签名错误"),
	UNKNOW_ERROR(1006, "签名错误"),
	SESSION_INVAIAL(1010, "Session过期");
	
	private Integer code;
	private String descripter;

	private ErrorCode(Integer code, String descripter) {
		this.code = code;
		this.descripter = descripter;
	}

	public Integer getCode() {
		return this.code;
	}

	public String getDescripter() {
		return this.descripter;
	}
}