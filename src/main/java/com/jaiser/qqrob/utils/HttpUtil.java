package com.jaiser.qqrob.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class HttpUtil {

    private final static int STR_LEN = 7500;

    public final static String HTTP_POST = "POST";
    public final static String HTTP_GET = "GET";

    private static final Logger log
        = LoggerFactory.getLogger(HttpUtil.class);


    public static String postRequest(Map<String, String> headParam, String param, RestTemplate restTemplate, String url) {
        ResponseEntity<String> rss = null;
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            if (headParam != null ) {
                for(Map.Entry<String, String> entry : headParam.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    requestHeaders.add(key, value);
                }
            }
            if (url.contains("https")) {
                log.info("使用https请求");
                restTemplate = new RestTemplate(new SSLUtil());
            }
            HttpEntity<String> requestEntity = new HttpEntity(param, requestHeaders);
            long startTime = System.currentTimeMillis();
            rss = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class, new Object[0]);
            long endTime = System.currentTimeMillis();
            if (StringUtils.isNotBlank(param) && param.length() < STR_LEN) {
                log.info("\n请求信息：\nurl《" + url + "》\n请求成功,请求参数《" + param + "》\n响应信息：\ncode《" + rss.getStatusCode() + "》\n响应体《" + rss.getBody() + "》\n耗时《" + (endTime - startTime) + "ms》");
            }else {
                log.debug("\n请求信息：\nurl《" + url + "》\n请求成功,请求参数《" + param + "》\n响应信息：\ncode《" + rss.getStatusCode() + "》\n响应体《" + rss.getBody() + "》\n耗时《" + (endTime - startTime) + "ms》");
            }
            return rss.getBody();
        } catch (Exception e) {
            log.error("请求信息：url《" + url + "》\n请求失败,请求参数《" + param + "》\n响应信息：\ncode《" + (rss != null ? rss.getStatusCode() : "") + "》");
            throw e;
        }
    }

    public static String postRequestNoLog(Map<String, String> headParam, String param, RestTemplate restTemplate, String url) {
        ResponseEntity<String> rss = null;
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            if (headParam != null ) {
                for(Map.Entry<String, String> entry : headParam.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    requestHeaders.add(key, value);
                }
            }
            if (url.contains("https")) {
                log.info("使用https请求");
                restTemplate = new RestTemplate(new SSLUtil());
            }
            HttpEntity<String> requestEntity = new HttpEntity(param, requestHeaders);
            rss = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class, new Object[0]);
            return rss.getBody();
        } catch (Exception e) {
            log.error("请求信息：url《" + url + "》\n请求失败,请求参数《" + param + "》\n响应信息：\ncode《" + (rss != null ? rss.getStatusCode() : "") + "》");
            throw e;
        }
    }

    public static String postRequest(Map<String, String> headParam, String param, RestTemplate restTemplate, String url, MediaType mediaType) {
        ResponseEntity<String> rss = null;
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(mediaType);
            if (headParam != null ) {
                for(Map.Entry<String, String> entry : headParam.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    requestHeaders.add(key, value);
                }
            }
            if (url.contains("https")) {
                log.info("使用https请求");
                restTemplate = new RestTemplate(new SSLUtil());
            }
            HttpEntity<String> requestEntity = new HttpEntity(param, requestHeaders);

            long startTime = System.currentTimeMillis();
            rss = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class, new Object[0]);
            long endTime = System.currentTimeMillis();
            if (StringUtils.isNotBlank(param) && param.length() < STR_LEN) {
                log.info("\n请求信息：\nurl《" + url + "》\n请求成功,请求头类型《" + mediaType.getType() + "/" + mediaType.getSubtype() + "》\n请求参数《" + param + "》\n响应信息：\ncode《" + rss.getStatusCode() + "》\n响应体《" + rss.getBody() + "》\n耗时《" + (endTime - startTime) + "ms》");
            }else {
                log.debug("\n请求信息：\nurl《" + url + "》\n请求成功,请求头类型《" + mediaType.getType() + "/" + mediaType.getSubtype() + "》\n请求参数《" + param + "》\n响应信息：\ncode《" + rss.getStatusCode() + "》\n响应体《" + rss.getBody() + "》\n耗时《" + (endTime - startTime) + "ms》");
            }
            return rss.getBody();
        } catch (Exception e) {
            log.error("请求信息：url《" + url + "》\n请求失败,请求头类型《" + mediaType.getType() + "/" + mediaType.getSubtype() + "》\n请求参数《" + param + "》\n响应信息：\ncode《" + (rss != null ? rss.getStatusCode() : "") + "》");
            throw e;
        }
    }

    public static String putRequest(Map<String, String> headParam, String param, RestTemplate restTemplate, String url) {
        ResponseEntity<String> rss = null;
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            if (headParam != null ) {
                for(Map.Entry<String, String> entry : headParam.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    requestHeaders.add(key, value);
                }
            }
            HttpEntity<String> requestEntity = new HttpEntity(param, requestHeaders);
            long startTime = System.currentTimeMillis();
            rss = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class, new Object[0]);
            long endTime = System.currentTimeMillis();
            if (StringUtils.isNotBlank(param) && param.length() < STR_LEN) {
                log.info("\n请求信息：\nurl《" + url + "》\n请求成功,请求参数《" + param + "》\n响应信息：\ncode《" + rss.getStatusCode() + "》\n响应体《" + rss.getBody() + "》\n耗时《" + (endTime - startTime) + "ms》");
            }else {
                log.debug("\n请求信息：\nurl《" + url + "》\n请求成功,请求参数《" + param + "》\n响应信息：\ncode《" + rss.getStatusCode() + "》\n响应体《" + rss.getBody() + "》\n耗时《" + (endTime - startTime) + "ms》");
            }
            return rss.getBody();
        } catch (Exception e) {
            log.error("请求信息：url《" + url + "》\n请求失败,请求参数《" + param + "》\n响应信息：\ncode《" + (rss != null ? rss.getStatusCode() : "") + "》");
            throw e;
        }
    }

    public static String getRequest(Map<String, String> headParam, String param, RestTemplate restTemplate, String url) {
        ResponseEntity<String> rss = null;
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            if (headParam != null ) {
                for(Map.Entry<String, String> entry : headParam.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    requestHeaders.add(key, value);
                }
            }
            if (url.contains("https")) {
                log.info("使用https请求");
                restTemplate = new RestTemplate(new SSLUtil());
            }
            HttpEntity<String> requestEntity = new HttpEntity(param, requestHeaders);
            long startTime = System.currentTimeMillis();
            rss = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class, new Object[0]);
            long endTime = System.currentTimeMillis();
            if (StringUtils.isNotBlank(param) && param.length() < STR_LEN) {
                log.info("\n请求信息：\nurl《" + url + "》\n请求成功,请求参数《" + param + "》\n响应信息：\ncode《" + rss.getStatusCode() + "》\n响应体《" + rss.getBody() + "》\n耗时《" + (endTime - startTime) + "ms》");
            }else {
                log.debug("\n请求信息：\nurl《" + url + "》\n请求成功,请求参数《" + param + "》\n响应信息：\ncode《" + rss.getStatusCode() + "》\n响应体《" + rss.getBody() + "》\n耗时《" + (endTime - startTime) + "ms》");
            }
            return rss.getBody();
        } catch (Exception e) {
            log.error("请求信息：url《" + url + "》\n请求失败,请求参数《" + param + "》\n响应信息：\ncode《" + (rss != null ? rss.getStatusCode() : "") + "》");
            throw e;
        }
    }
    public static String getTimeStr(Date date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        } catch (Exception e) {
            log.error("getTimeStr error", e);
            return "";
        }

    }

}
