package com.baidu.disconf.core.common.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MachineInfo
 *
 * @author liaoqiqi
 * @version 2014-7-30
 */
public final class MachineInfo {
	private static final Logger logger = LoggerFactory.getLogger(MachineInfo.class);
	
	private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
	
	private static final RuntimeMXBean RUNTIME_MBEAN = ManagementFactory.getRuntimeMXBean();
	
	// 定义获取host需要的系统属性名列表，以取到的第一个不为空的值为止
    private static final String[] appKeyPropertys = "dubbo.application.name,pinpoint.applicationName,appName,applicationName,VCAP_APP_HOST".split(",");


    private static int PID = 0;


    public static int getPid() {
        if (PID == 0) {
            PID = getPid0();
        }
        return PID;
    }
    
    /**
     * 
     * @Description:获取vm参数
     * @return
     * @Author:denghong1 2016年11月14日下午4:06:38
     * @update1:
     *
     */
    public static List<String> getVmArgs() {
        final List<String> vmArgs = RUNTIME_MBEAN.getInputArguments();
        if (vmArgs == null) {
            return Collections.emptyList();
        }
        return vmArgs;
    }

    private static int getPid0() {
        final String name = RUNTIME_MBEAN.getName();
        final int pidIndex = name.indexOf('@');
        if (pidIndex == -1) {
            return -1;
        }
        String strPid = name.substring(0, pidIndex);
        try {
            return Integer.parseInt(strPid);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    
    private MachineInfo() {

    }

    /**
     * @return
     *
     * @Description: 获取机器名
     */
    public static String getHostName() throws Exception {

        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostname = addr.getHostName();

            return hostname;

        } catch (UnknownHostException e) {

            throw new Exception(e);
        }
    }
    
    public static String getAppName() {
    	String appName;
    	for(String appKey : appKeyPropertys){
    		appName = System.getProperty(appKey);
    		if(StringUtils.isNotEmpty(appName)){
    			return appName;
    		}
    	}
		return "";
	}

    /**
     * @return
     *
     * @Description: 获取机器名
     */
    public static String getHostIp() {
		try {
			InetAddress localAddress = InetAddress.getLocalHost();
			if (null != localAddress && localAddress instanceof Inet4Address && !localAddress.isLoopbackAddress() && null != localAddress.getHostAddress() && !"0.0.0.0".equals(localAddress.getHostAddress())
					&& !"127.0.0.1".equals(localAddress.getHostAddress())  && IP_PATTERN.matcher(localAddress.getHostAddress()).matches()) {
				return localAddress.getHostAddress();
			}
		} catch (final UnknownHostException uhe) {
			logger.error("Failed to retriving ip address, " + uhe.getMessage(), uhe);
		}
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			if (interfaces != null) {
				while (interfaces.hasMoreElements()) {
					NetworkInterface network = interfaces.nextElement();
					Enumeration<InetAddress> addresses = network.getInetAddresses();
					if (addresses != null) {
						while (addresses.hasMoreElements()) {
							InetAddress address = addresses.nextElement();
							if (null != address && address instanceof Inet4Address && !address.isLoopbackAddress() && null != address.getHostAddress() && !"0.0.0.0".equals(address.getHostAddress())
									&& !"127.0.0.1".equals(address.getHostAddress())  && IP_PATTERN.matcher(address.getHostAddress()).matches()) {
								return address.getHostAddress();
							}
						}
					}
				} // loop while
			}
		} catch (Exception e) {
			logger.error("Failed to retriving ip address, " +e.getMessage(), e);
		}
		return "127.0.0.1";
	}

}
