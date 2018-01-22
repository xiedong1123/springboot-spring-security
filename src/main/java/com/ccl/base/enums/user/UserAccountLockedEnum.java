package com.ccl.base.enums.user;


/**
 * 
 * @ClassName：UserAccountLockedEnum
 * @Description：用户锁定状态枚举
 * @Author：xiedong
 * @Date：2017年12月19日下午1:32:22
 * @version：1.0.0
 */
public enum UserAccountLockedEnum {
	/**
	 * 锁定
	 */
	LOCKED("锁定", (byte)1),
	/**
	 * 未锁定
	 */
	UNLOCKED("未锁定", (byte)0);

    private String type;
    private Byte code;
    
    private UserAccountLockedEnum(String type, Byte code) {
		this.type = type;
		this.code = code;
	}
	public void setCode(Byte code) {
		this.code = code;
	}
	public Byte getCode() {
		return code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	public static UserAccountLockedEnum getByCode(Byte code) {
		UserAccountLockedEnum[] arg3;
		int arg2 = (arg3 = values()).length;

		for (int arg1 = 0; arg1 < arg2; ++arg1) {
			UserAccountLockedEnum type = arg3[arg1];
			if (type.code == code) {
				return type;
			}
		}
		return null;

	}
}
