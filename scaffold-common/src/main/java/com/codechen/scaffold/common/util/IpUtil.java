package com.codechen.scaffold.common.util;

import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author：Java陈序员
 * @date 2023-07-10 10:25
 * @description ip 工具类
 */
public class IpUtil {

    private static final String UNKNOWN = "unknown";
    private static final String HEADER_FORWARDED = "x-forwarded-for";
    private static final String HEADER_PROXY = "Proxy-Client-IP";
    private static final String HEADER_WL_PROXY = "WL-Proxy-Client-IP";
    private static final String HEADER_HTTP = "HTTP_CLIENT_IP";
    private static final String HEADER_HTTP_FORWARDED = "HTTP_X_FORWARDED_FOR";
    private static final String LOCAL_IP = "127.0.0.1";
    private static final String LOCAL_HOST = "localhost";

    private static Searcher searcher;

    /**
     * 获取 IP 地址
     *
     * Host：获取客户端（client）的真实域名和端口号
     * X-Real-IP：获取客户端真实 IP 地址
     * X-Forwarded-For：也是获取客户端真实 IP 地址，如果有多层代理时会获取客户端真实 IP 及每层代理服务器的 IP 地址
     * X-Forwarded-Proto：获取客户端的真实协议（如 http、https）
     * getRemoteAddr()用于获取没有代理服务器情况下用户的 IP 地址2
     *  1.当用户的请求经过一个代理服务器后到达最终服务器，此时在最终服务器端通过getRemoteAddr()只能得到代理服务器的 IP 地址，通过在代理服务器中配置proxy_set_header X-Real-IP $remote_addr，最终的服务器可以通过X-Real-IP获取用户的真实 IP 地址。
     *  2.当用户的请求经过多个代理服务器后到达最终服务器时，配置proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for后可通过X-Forwarded-For获取用户真实 IP 地址（请求通过第一台 nginx时：X-Forwarded-For = X-Real-IP = 用户真实 IP 地址；请求通过第二台 nginx 时：X-Forwarded-For = 用户真实 IP 地址， X-Real-IP = 上一台 nginx 的 IP 地址 ）。
     *  3.获取客户端的IP地址不仅可以通过proxy_set_header X-real-ip $remote_addr;获取，也可以通过proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;获取。
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader(HEADER_FORWARDED);

        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HEADER_PROXY);
        }

        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HEADER_WL_PROXY);
        }

        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HEADER_HTTP);
        }

        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HEADER_HTTP_FORWARDED);
        }

        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 本机访问
        if (LOCAL_IP.equalsIgnoreCase(ip) || LOCAL_HOST.equalsIgnoreCase(ip) || "0:0:0:0:0:0:0:1".equalsIgnoreCase(ip)) {
            // 根据网卡取本机配置的 IP
            try {
                InetAddress localHost = InetAddress.getLocalHost();
                ip = localHost.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        // 对于通过多个代理的情况，第一个 IP 为客户端真实 IP,多个 IP 按照','分割
        if (ip.indexOf(",") > 0) {
            String[] ips = ip.split(",");
            if (ips.length > 0) {
                ip = ips[0];
            }
        }

        return ip;
    }

    /**
     * 获取 mac 地址
     *
     * @returnf
     * @throws Exception
     */
    public static String getMacAddress() throws Exception {
        // 获取 mac 地址
        byte[] hardwareAddress = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();

        // 将 mac 地址转成字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < hardwareAddress.length; i++) {
            if (i != 0) {
                stringBuilder.append("-");
            }

            // hardwareAddress[i] & 0xFF 是为了把 byte 转化为正整数
            String s = Integer.toHexString(hardwareAddress[i] & 0xFF);
            stringBuilder.append(s.length() == 1 ? 0 + s : s);
        }
        return stringBuilder.toString().trim().toUpperCase();
    }


    /**
     * 判断是否为合法 IP
     * @return
     */
    public static boolean checkIp(String ipAddress) {
        String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    /**
     * 在服务启动时，将 ip2region 加载到内存中
     */
    @PostConstruct
    private static void initIp2Region() {
        try {
            InputStream inputStream = new ClassPathResource("/ipdb/ip2region.xdb").getInputStream();
            byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
            searcher = Searcher.newWithBuffer(bytes);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 获取 ip 所属地址
     *
     * @param ip ip
     * @return
     */
    public static String getIpRegion(String ip) {

        boolean isIp = checkIp(ip);

        if (isIp) {

            initIp2Region();

            try {
                String searchIpInfo = searcher.search(ip);
                // searchIpInfo 的数据格式： 国家|区域|省份|城市|ISP
                // 192.168.31.160 0|0|0|内网IP|内网IP
                // 47.52.236.180 中国|0|香港|0|阿里云
                // 220.248.12.158 中国|0|上海|上海市|联通
                // 164.114.53.60 美国|0|华盛顿|0|0

                String[] splitIpInfo = searchIpInfo.split("\\|");

                if (splitIpInfo.length > 0) {
                    if ("中国".equals(splitIpInfo[0])) {
                        // 国内属地返回省份
                        return splitIpInfo[2];
                    } else if ("0".equals(splitIpInfo[0])) {
                        if ("内网IP".equals(splitIpInfo[4])) {
                            // 内网 IP
                            return splitIpInfo[4];
                        } else {
                            return "";
                        }
                    } else {
                        // 国外属地返回国家
                        return splitIpInfo[0];
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        } else {
            throw new IllegalArgumentException("非法的IP地址");
        }

    }

}
