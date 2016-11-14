package com.baidu.disconf.client.common.model;

/**
 * 实例指纹
 *
 * @author liaoqiqi
 * @version 2014-6-27
 */
public class InstanceFingerprint {

    // 本实例应用名及所在机器的IP
    private String appName = "";

    // 可以表示本实例的pid
    private int pid = 0;

    // 一个实例固定的UUID
    private String uuid = "";

    public InstanceFingerprint(String appName, int pid, String uuid) {
        super();
        this.appName = appName;
        this.pid = pid;
        this.uuid = uuid;
    }


    public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}


	public int getPid() {
		return pid;
	}


	public void setPid(int pid) {
		this.pid = pid;
	}


	public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "InstanceFingerprint [appName=" + appName + ", pid=" + pid + "]";
    }

}
