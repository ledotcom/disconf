package com.baidu.disconf.client.config.inner;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.disconf.client.common.model.InstanceFingerprint;
import com.baidu.disconf.core.common.utils.MachineInfo;

/**
 * 一些通用的数据
 *
 * @author liaoqiqi
 * @version 2014-7-1
 * 修改本机信息显示的逻辑 Dimmacro 2016年11月14日15:31:20
 */
public class DisClientComConfig {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DisClientComConfig.class);
    
    protected static final DisClientComConfig INSTANCE = new DisClientComConfig();
    
    public static DisClientComConfig getInstance() {
        return INSTANCE;
    }

    private DisClientComConfig() {

        initInstanceFingerprint();
    }

    /**
     * 初始化实例指纹<br/>
     * 以IP和PORT为指紋，如果找不到则以本地IP为指纹
     */
    private void initInstanceFingerprint() {
        String appName = StringUtils.join(new String[]{MachineInfo.getHostIp(),MachineInfo.getAppName()},"-");;
        int pid = MachineInfo.getPid();
        instanceFingerprint = new InstanceFingerprint(appName, pid, UUID.randomUUID().toString());
    }


	private InstanceFingerprint instanceFingerprint;

    /**
     * 获取指纹
     */
    public String getInstanceFingerprint() {
        return instanceFingerprint.getAppName() + "_" + String.valueOf(instanceFingerprint.getPid()) + "_" +
                instanceFingerprint.getUuid();
    }
    
}
