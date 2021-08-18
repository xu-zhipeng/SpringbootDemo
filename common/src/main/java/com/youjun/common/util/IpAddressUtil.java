package com.youjun.common.util;

import javax.servlet.http.HttpServletRequest;

public class IpAddressUtil {

    public static String getIpAddress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        //多次反向代理后会有多个ip值，第一个ip才是真实ip
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor))
            return XFor;

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getHeader("Proxy-Client-IP");

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getHeader("WL-Proxy-Client-IP");

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getHeader("HTTP_CLIENT_IP");

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");

        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor))
            XFor = request.getRemoteAddr();

        return XFor;
    }
}
