package com.baidu.disconf.web.service.user.bo;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @Description:
 * @Author denghong1
 * @Create time: 2016年11月10日上午10:23:40
 * 1 为admin用户，全局可看可改
 * 2 为read_admin用户，全局只可看不可改，因为其roleId为3
 * 3 为test用户，可以看出所有app，但只能看自己创建的config项
 */
public enum UserEnum {

    ADMIN(1), READ_ADMIN(2),TEST(3), OPERATION (4);
    private static final Map<Long, UserEnum> longToEnum = new HashMap<Long, UserEnum>();

    static {
        for (UserEnum roleEnum : values()) {
            longToEnum.put(roleEnum.value, roleEnum);
        }
    }

    private final long value;

    UserEnum(int value) {
        this.value = value;
    }

    public static UserEnum fromLong(long symbol) {
        return longToEnum.get(symbol);
    }

    public long getValue() {
        return value;
    }
    
    public static boolean isAdmin(Long userId){
    	return (null != userId) && (ADMIN.getValue() == userId || READ_ADMIN.getValue() == userId);
    }

    /**
     * 判断是否是普通用户
     * @Description:
     * @param userId
     * @return
     * @Author:denghong1 2016年11月10日下午7:58:43
     * @update1:
     *
     */
	public static boolean isNormalUser(Long userId) {
		if(null == userId){
			return false;
		}
		return !longToEnum.containsKey(userId);
	}
}
