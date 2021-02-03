package com.ideayp.leaf.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

/**
 * <p>Description:  Request工具类  </p>
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2017</p>
 *
 * @author yangpeng
 * @version 1.0
 * @date 2017/12/6
 * @since 1.8
 */
@SuppressWarnings({"ALL", "unchecked"})
public class RequestUtils {

    /**
     * 获取项目的绝对路径
     * 与resource下文件同级
     * @return 项目的绝对路径
     */
    public static String getRootPath() {
        String classPath = "";
        try {
            // 防止有空格,%20等的出现
            classPath = URLDecoder.decode(Objects.requireNonNull(RequestUtils.class.getClassLoader().getResource("")).getPath(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return classPath;
    }


    /**
     * 获取服务器地址url
     * 与getServerPath结合使用 生成文件可以直接访问
     * @return 可访问地址
     */
    public static String getServerUrl() {
        HttpServletRequest request = getCurrentRequest();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    /**
     * 获取服务器运行的地址
     * 可以用new file 保存文件 防止 打成jar的时候 生成临时文件出错
     * 与getServerUrl结合使用 生成文件可以直接访问
     * @return 地址
     */
    public static String getServerPath() {
        HttpServletRequest request =getCurrentRequest();
        return request.getServletContext().getRealPath("/");
    }



    /**
     * 在主线程使用
     * @return 当前的reqeust
     */
    public static HttpServletRequest getCurrentRequest() {
        return getServletRequestAttributes().getRequest();
    }

    /**
     * 在主线程使用
     * @return 当前的response
     */
    public static HttpServletResponse getCurrentResponse() {
        return getServletRequestAttributes().getResponse();
    }

    /**
     * 当前上下文
     * 只能在主线程使用
     *
     * @return 结果
     */
    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }

    /**
     * 获取当前的真实ip
     *
     * @return ip地址
     */
    public static String getIpAddr() {
        return getIpAddr(getCurrentRequest());
    }

    /**
     * 获取真实地址
     *
     * @param request 请求
     * @return 当前ip
     */
    public static String getIpAddr(final HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return ip;
    }
}
