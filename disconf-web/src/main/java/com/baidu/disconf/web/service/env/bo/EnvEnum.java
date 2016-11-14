package com.baidu.disconf.web.service.env.bo;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @Description:
 * @Author denghong1
 * @Create time: 2016年11月10日下午7:50:47
 *
 */
public enum EnvEnum {

    LOCAL(1), TEST(2), PREVIEW(3), ONLINE(4);
    private static final Map<Long, EnvEnum> intToEnum = new HashMap<Long, EnvEnum>();

    static {
        for (EnvEnum enEnum : values()) {
            intToEnum.put(enEnum.value, enEnum);
        }
    }

    private final long value;

    EnvEnum(long value) {
        this.value = value;
    }

    public static EnvEnum fromInt(int symbol) {
        return intToEnum.get(symbol);
    }

    public long getValue() {
        return value;
    }

	public static boolean isLocalEnv(Long envId) {
		if(null == envId){
			return false;
		}
		return LOCAL.getValue() == envId;
	}
}
