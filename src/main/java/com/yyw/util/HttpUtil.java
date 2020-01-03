package com.yyw.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * @author yyw
 * @date 2018/12/11 14:58
 */
public class HttpUtil {
    /**
     * 发送post请求
     *
     * @param pRequest       请求报文
     * @param url
     * @param connectTimeout 连接超时时间
     * @param readTimeout    读取超时时间
     * @return
     */
    public static String post(String pRequest, String url, int connectTimeout, int readTimeout) {
        String resMes = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpPost httpPost = null;
        try {
            SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(SSLContexts.custom()
                    .loadTrustMaterial(null, (chain, type) -> true).build(),
                    NoopHostnameVerifier.INSTANCE);
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setSocketTimeout(connectTimeout)
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(readTimeout)
                    .build();
            //创建自定义的httpclient对象
            httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).setSSLSocketFactory(factory).build();

            //创建post方式请求对象
            httpPost = new HttpPost(url);

            //装填参数
            StringEntity stringEntity = new StringEntity(pRequest, StandardCharsets.UTF_8);
            stringEntity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            stringEntity.setContentType("application/json");
            //设置参数到请求对象中
            httpPost.setEntity(stringEntity);
            //设置header信息
            //指定报文头【Content-type】、【User-Agent】
            httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
//            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //执行请求操作，并拿到结果（同步阻塞）
            response = httpClient.execute(httpPost);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                resMes = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeHttpClient(httpClient);
            closeResponse(response);
            releaseConnect(httpPost);
        }
        return resMes;
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * <p>
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
     * <p>
     * 用户真实IP为： 192.168.1.110
     */
    public static String getIpAddress(HttpServletRequest request) {
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
        return ip;
    }

    public static String getHeader(HttpServletRequest request) {
        Enumeration headerNames = request.getHeaderNames();
        JSONObject jsonObject = new JSONObject();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            jsonObject.put(headerName, headerValue);
        }
        return jsonObject.toJSONString();
    }

    private static void closeInputStream(InputStream c) {
        try {
            if (c != null) {
                c.close();
            }

        } catch (Exception ex) {

        }
    }

    private static void closeOutputStream(BufferedReader c) {
        try {
            if (c != null) {
                c.close();
            }

        } catch (Exception ex) {

        }
    }

    private static void closeHttpClient(CloseableHttpClient c) {
        try {
            if (c != null) {
                c.close();
            }

        } catch (Exception ex) {

        }
    }

    private static void closeResponse(CloseableHttpResponse c) {
        try {
            if (c != null) {
                c.close();
            }

        } catch (Exception ex) {

        }
    }

    private static void releaseConnect(HttpPost httpPost) {
        try {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }

        } catch (Exception ex) {

        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println();
    }
}
