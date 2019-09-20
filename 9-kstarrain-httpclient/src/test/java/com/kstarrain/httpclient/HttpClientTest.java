package com.kstarrain.httpclient;

import com.alibaba.fastjson.JSON;
import com.kstarrain.app.RequestParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author: Dong Yu
 * @create: 2019-04-09 08:56
 * @description:
 */
@Slf4j
public class HttpClientTest {

    final String requestUrl = "http://localhost:8080/servlet/httpclient";
    final String readFilePath = "E:" + File.separator + "其他" + File.separator + "cat.jpg";


    @Test
    public void doGet1() {

        CloseableHttpClient httpClient = null;

        try {
            // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
            httpClient = HttpClients.createDefault();

            // 方式1 直接拼接参数
            Map<String, String> params = new HashMap<String, String>();
            params.put("userName", "董宇");
            params.put("key", "1234qwer");

            //拼接参数
            StringBuilder urlbuf = new StringBuilder(requestUrl);
            if (MapUtils.isNotEmpty(params)){
                int num = 0;
                for (Map.Entry<String, String> entry : params.entrySet()) {

                    if (!urlbuf.toString().contains("?")) {
                        urlbuf.append("?");
                    }
                    if (num != 0) {
                        urlbuf.append("&");
                    }
                    urlbuf.append(entry.getKey())
                            .append("=")
                            .append(entry.getValue() == null ? "" : URLEncoder.encode(entry.getValue()));
                    num++;
                }
            }

            // 创建Get请求
            HttpGet httpGet = new HttpGet(urlbuf.toString());

            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)  -- 连接建立时间，三次握手完成时间
                    .setConnectTimeout(10000)
                    // 设置请求超时时间(单位毫秒)  -- httpclient使用连接池来管理连接，这个时间就是从连接池获取连接的超时时间，可以想象下数据库连接池
                    .setConnectionRequestTimeout(10000)
                    // socket读写超时时间(单位毫秒) -- 数据传输过程中数据包之间间隔的最大时间
                    .setSocketTimeout(10000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);

            httpGet.setHeader("Content-Type","application/json;charset=UTF-8");
            httpGet.setHeader("Cookie","testMethod=GET;accessToken=2c81fd43-a991-4f78-bbce-21be2054431e_105502");

            // 由客户端执行(发送)Get请求
            CloseableHttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String originalContent = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println(originalContent);
            }else {
                String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println("错误码-" + response.getStatusLine().getStatusCode() + " response-" + responseString);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("httpclient close error",e);
                }
            }
        }
    }


    @Test
    public void doGet2() {

        CloseableHttpClient httpClient = null;

        try {
            httpClient = HttpClients.createDefault();

            // 方式2 使用URI获得HttpGet
            // 将参数放入键值对类NameValuePair中,再放入集合中
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("userName", "董宇"));
            params.add(new BasicNameValuePair("key", "1234qwer"));

            // 设置uri信息,并将参数集合放入uri;
            // 注:这里也支持一个键值对一个键值对地往里面放setParameter(String key, String value)
            URI uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(8080).setPath("/servlet/httpclient")
                    .setParameters(params).build();


            // 创建Get请求
            HttpGet httpGet = new HttpGet(uri);


            httpGet.setHeader("Content-Type","application/json;charset=UTF-8");
            httpGet.setHeader("Cookie","testMethod=GET;accessToken=2c81fd43-a991-4f78-bbce-21be2054431e_105502");

            // 由客户端执行(发送)Get请求
            CloseableHttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String originalContent = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println(originalContent);
            }else {
                String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println("错误码-" + response.getStatusLine().getStatusCode() + " response-" + responseString);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("httpclient close error",e);
                }
            }
        }
    }



    @Test
    public void doPost_x_www_form_urlencoded() {

        CloseableHttpClient httpClient = null;

        try {
            // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
            httpClient = HttpClients.createDefault();


            // 创建POST请求
            HttpPost httpPost = new HttpPost(requestUrl);
            httpPost.setHeader("Cookie","testMethod=GET;accessToken=2c81fd43-a991-4f78-bbce-21be2054431e_105502");
            httpPost.setHeader("Authorization","authorization_" + UUID.randomUUID().toString());
            httpPost.setHeader("store",Base64.getEncoder().encodeToString("华为应用商店".getBytes("utf-8")));
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");


            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("userName", "董宇"));
            params.add(new BasicNameValuePair("key", "1234qwer"));

            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));


            // 由客户端执行(发送)Get请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String originalContent = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println(originalContent);
            }else {
                String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println("错误码-" + response.getStatusLine().getStatusCode() + " response-" + responseString);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("httpclient close error",e);
                }
            }
        }

    }


    @Test
    public void doPost_multipart_form_data() {

        CloseableHttpClient httpClient = null;

        try {
            // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
            httpClient = HttpClients.createDefault();


            // 创建POST请求
            HttpPost httpPost = new HttpPost(requestUrl);
            httpPost.setHeader("Cookie","testMethod=GET;accessToken=2c81fd43-a991-4f78-bbce-21be2054431e_105502");
            httpPost.setHeader("Authorization","authorization_" + UUID.randomUUID().toString());
            httpPost.setHeader("store",Base64.getEncoder().encodeToString("华为应用商店".getBytes("utf-8")));


            // MultipartEntityBuilder会自动声明Content-Type为multipart/form-data
            httpPost.setEntity(MultipartEntityBuilder.create()
                                .addPart("userName",new StringBody("董宇", StandardCharsets.UTF_8))
                                .addPart("key",new StringBody("1234qwer", StandardCharsets.UTF_8))
                                .addPart("file",new FileBody(new File(readFilePath)))
                                .build());


            // 由客户端执行(发送)Get请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String originalContent = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println(originalContent);
            }else {
                String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println("错误码-" + response.getStatusLine().getStatusCode() + " response-" + responseString);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("httpclient close error",e);
                }
            }
        }


    }



    @Test
    public void doPostJSON() {

        CloseableHttpClient httpClient = null;

        try {
            // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
            httpClient = HttpClients.createDefault();

            File file = new File(readFilePath);
            if (!file.exists()){return;}

            FileInputStream input = new FileInputStream(file);
            // input.available()返回文件的字节长度
            byte[] bytes = new byte[input.available()];
            // 将文件中的内容读入到数组中
            input.read(bytes);
            //将字节流数组转换为字符串
            String strBase64 = Base64.getEncoder().encodeToString(bytes);
            input.close();


            //POST参数  将参数信息 输出到连接中 （我方服务器 输出到 目标服务器）
            RequestParam requestParam = new RequestParam();
            requestParam.setUserName("吕布");
            requestParam.setKey("1234qwer");
            requestParam.setFile(strBase64);


            // 创建POST请求
            HttpPost httpPost = new HttpPost(requestUrl);
            httpPost.setHeader("Cookie","testMethod=GET;accessToken=2c81fd43-a991-4f78-bbce-21be2054431e_105502");
            httpPost.setHeader("Authorization","authorization_" + UUID.randomUUID().toString());
            httpPost.setHeader("store",Base64.getEncoder().encodeToString("华为应用商店".getBytes("utf-8")));
            httpPost.setHeader("Content-Type","application/json;charset=UTF-8");

            // MultipartEntityBuilder会自动声明Content-Type为multipart/form-data
            httpPost.setEntity(new StringEntity(JSON.toJSONString(requestParam), "UTF-8"));


            // 由客户端执行(发送)Get请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String originalContent = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println(originalContent);
            }else {
                String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println("错误码-" + response.getStatusLine().getStatusCode() + " response-" + responseString);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("httpclient close error",e);
                }
            }
        }


    }
}
