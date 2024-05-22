package com.codechen.scaffold.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * @author：Java陈序员
 * @date 2023-04-06 17:30
 * @description http 远程调用工具类
 * GET:
 *  创建远程连接
 *  设置连接方式（get、post、put。。。）
 *  设置连接超时时间
 *  设置响应读取时间
 *  发起请求
 *  获取请求数据
 *  关闭连接
 * POST:
 *  创建远程连接
 *  设置连接方式（get、post、put。。。）
 *  设置连接超时时间
 *  设置响应读取时间
 *  当向远程服务器传送数据/写数据时，需要设置为true（setDoOutput）
 *  当前向远程服务读取数据时，设置为true，该参数可有可无（setDoInput）
 *  设置传入参数的格式:（setRequestProperty）
 *  设置鉴权信息：Authorization:（setRequestProperty）
 *  设置参数
 *  发起请求
 *  获取请求数据
 *  关闭连接
 */
public class HttpUtil {

    /**
     * 默认超时时间
     */
    private static final int TIMEOUT = 15000;

    /**
     * GET 请求
     */
    private static final String GET_METHOD = "GET";

    /**
     * POST 请求
     */
    private static final String POST_METHOD = "POST";

    /**
     * 创建远程连接
     *
     * @param url           请求 URL
     * @param requestMethod 请求方式 get/post
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getConnection(URL url, String requestMethod) throws IOException {
        return getConnection(url, requestMethod, TIMEOUT);
    }

    /**
     * 创建远程连接
     *
     * @param url           请求 URL
     * @param requestMethod 请求方式 get/post
     * @param timeout       连接超时时间
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getConnection(URL url, String requestMethod, Integer timeout) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置请求方式
        connection.setRequestMethod(requestMethod);
        // 射界连接超时时间
        connection.setConnectTimeout(timeout);

        return connection;
    }

    /**
     * 关闭连接
     *
     * @param connection
     */
    private static void closeConnection(HttpURLConnection connection) {
        connection.disconnect();
    }

    private static String buildUrl(String httpUrl, Map<String, String> paraMap) {
        if (paraMap != null) {
            httpUrl += "?";
            Set<String> keySet = paraMap.keySet();
            for (String key : keySet) {
                httpUrl += "&" + key + "=" + paraMap.get(key);
            }
        }

        return httpUrl;
    }

    private static void buildHeader(HttpURLConnection connection, Map<String, String> headerMap) {
        if (headerMap != null) {
            Set<String> keySet = headerMap.keySet();
            keySet.forEach(key -> {
                connection.addRequestProperty(key, headerMap.get(key));
            });
        }
    }

    private static void buildCommon(HttpURLConnection connection) {
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
    }

    public static String doGet(String httpUrl) {
        return doGet(httpUrl, null);
    }

    public static String doGet(String httpUrl, Map<String, String> paramMap) {
        return doGet(httpUrl, null, paramMap);
    }

    public static String doGet(String httpUrl, Map<String, String> headerMap, Map<String, String> paramMap) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuffer result = new StringBuffer();

        try {
            httpUrl = buildUrl(httpUrl, paramMap);
            URL url = new URL(httpUrl);
            connection = getConnection(url, GET_METHOD);
            buildHeader(connection, headerMap);

            // 连接
            connection.connect();

            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String temp = null;
                    while ((temp = bufferedReader.readLine()) != null) {
                        result.append(temp);
                    }
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }


        return result.toString();
    }

    public static String doPost(String httpUrl, Map<String, String> headerMap, String param) {
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuffer result = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            connection = getConnection(url, POST_METHOD);
            buildHeader(connection, headerMap);
            // DoOutput设置是否向httpUrlConnection输出
            connection.setDoOutput(true);
            // DoInput设置是否从 httpUrlConnection 读入，此外发送 post 请求必须设置这两个
            connection.setDoInput(true);

            // 设置参数
            if (param != null && param != "") {
                outputStream = connection.getOutputStream();
                outputStream.write(param.getBytes("UTF-8"));
            }

            // 连接
            connection.connect();

            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
                    String temp = null;
                    while ((temp = bufferedReader.readLine()) != null) {
                        result.append(temp);
                    }
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭通道
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return result.toString();
    }

}
