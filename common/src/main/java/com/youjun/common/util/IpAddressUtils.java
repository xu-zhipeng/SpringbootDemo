package com.youjun.common.util;

import javax.servlet.http.HttpServletRequest;

public class IpAddressUtils {
    public static final String UNKNOWN = "unKnown";

    private IpAddressUtils() {
    }

    public static String getIpAddress(HttpServletRequest request) {
        String xIP = request.getHeader("X-Real-IP");
        String xFor = request.getHeader("X-Forwarded-For");
        //多次反向代理后会有多个ip值，第一个ip才是真实ip
        if (StringUtils.isNotEmpty(xFor) && !UNKNOWN.equalsIgnoreCase(xFor)) {
            int index = xFor.indexOf(",");
            if (index != -1) {
                return xFor.substring(0, index);
            } else {
                return xFor;
            }
        }
        xFor = xIP;
        if (StringUtils.isNotEmpty(xFor) && !UNKNOWN.equalsIgnoreCase(xFor))
            return xFor;

        if (StringUtils.isBlank(xFor) || UNKNOWN.equalsIgnoreCase(xFor))
            xFor = request.getHeader("Proxy-Client-IP");

        if (StringUtils.isBlank(xFor) || UNKNOWN.equalsIgnoreCase(xFor))
            xFor = request.getHeader("WL-Proxy-Client-IP");

        if (StringUtils.isBlank(xFor) || UNKNOWN.equalsIgnoreCase(xFor))
            xFor = request.getHeader("HTTP_CLIENT_IP");

        if (StringUtils.isBlank(xFor) || UNKNOWN.equalsIgnoreCase(xFor))
            xFor = request.getHeader("HTTP_X_FORWARDED_FOR");

        if (StringUtils.isBlank(xFor) || UNKNOWN.equalsIgnoreCase(xFor))
            xFor = request.getRemoteAddr();

        return xFor;
    }
}
