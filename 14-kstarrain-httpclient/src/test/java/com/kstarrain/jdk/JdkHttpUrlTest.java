package com.kstarrain.jdk;

import com.alibaba.fastjson.JSON;
import com.kstarrain.model.Order;
import com.kstarrain.model.Student;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: Dong Yu
 * @create: 2019-04-09 08:56
 * @description:
 */
@Slf4j
public class JdkHttpUrlTest {

    final String requestUrl = "http://localhost:8080/servlet/httpclient";
    final String readFilePath = "E:" + File.separator + "其他" + File.separator + "cat.jpg";

    // boundary就是request头和上传文件内容的分隔符
    final String BOUNDARY = "123821742118716";

    @Test
    public void doGet() {

        HttpURLConnection conn = null;

        BufferedReader reader = null;
        try {

            Map<String, String> params = new HashMap<String, String>();
            params.put("userName", "董宇");
            params.put("key", "1234qwer");

            //拼接参数
            StringBuilder urlbuf = new StringBuilder("http://localhost:8080/mvc/http/get");
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


            System.out.println(urlbuf.toString());
            // 创建远程url连接对象
            URL url = new URL(urlbuf.toString());
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            conn = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            conn.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            conn.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            conn.setReadTimeout(60000);

            conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            conn.setRequestProperty("Cookie","testMethod=GET;accessToken=2c81fd43-a991-4f78-bbce-21be2054431e_105502");
            // 发送请求
            conn.connect();

           log.info("response code : {}",conn.getResponseCode());


            // 通过connection连接，获取输入流
            if (conn.getResponseCode() == 200) {
                //读取连接中的返回数据, 并对输入流对象进行包装:charset根据工作项目组的要求来设置
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            // 存放数据
            StringBuffer sbf = new StringBuffer();
            String temp;
            while ((temp = reader.readLine()) != null) {
                sbf.append(temp);
            }
            System.out.println(sbf.toString());

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }

            if (conn != null){
                conn.disconnect();
            }
        }
    }

    @Test
    public void doPost_x_www_form_urlencoded() {

        HttpURLConnection conn = null;

        PrintWriter out1 = null;
        OutputStreamWriter out2 = null;
        OutputStream out3 = null;

        BufferedReader reader = null;

        try {

            // 创建远程url连接对象
            URL url = new URL(requestUrl);
            // 通过远程url连接对象打开连接
            conn = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            conn.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：3000毫秒
            conn.setConnectTimeout(3000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            conn.setReadTimeout(60000);


            // 发送POST请求必须设置如下两行
            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            conn.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            conn.setDoInput(true);
            //不允许缓存
            conn.setUseCaches(false);

            conn.setRequestProperty("Cookie","testMethod=POST;accessToken=2c81fd43-a991-4f78-bbce-21be2054431e_105502;type=香香");
            conn.setRequestProperty("Authorization", "authorization_" + UUID.randomUUID().toString());
            conn.setRequestProperty("store", Base64.getEncoder().encodeToString("华为应用商店".getBytes("utf-8")));


            //application/x-www-form-urlencoded
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            //POST参数  将参数信息 输出到连接中 （我方服务器 输出到 目标服务器）
//            out1 = new OutputStreamWriter(conn.getOutputStream());
//            out1.write("userName=董宇&key=1234qwer");
//            out1.flush();

            // 或者   form表单
//            out2 = new PrintWriter(conn.getOutputStream());
//            out2.print("&userName=董宇");//设置一个参数
//            out2.print("&key="+"1234qwer");//设置一个参数n
//            out2.flush();

            //POST参数  将参数信息 输出到连接中 （我方服务器 输出到 目标服务器）
            out3 = new DataOutputStream(conn.getOutputStream());
            out3.write("userName=董宇&key=1234qwer".getBytes());
            out3.flush();



            // 如果code为200
            if (conn.getResponseCode() == 200) {

                //读取连接中的返回数据, 并对输入流对象进行包装:charset根据工作项目组的要求来设置
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                //返回的response结果
                StringBuffer responseContent = new StringBuffer();
                String temp;
                // 循环遍历一行一行读取数据
                while ((temp = reader.readLine()) != null) {
                    responseContent.append(temp);
                    responseContent.append("\r\n");
                }
                System.out.println(responseContent.toString());

            }else{
                System.out.println("错误码-" + conn.getResponseCode());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (out1 != null) {
                out1.close();
            }

            if (out2 != null) {
                try {
                    out2.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }

            if (out3 != null) {
                try {
                    out3.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }

            if (conn != null){
                conn.disconnect();
            }
        }
    }


    @Test
    public void doPost_multipart_form_data() {

        Map<String, String> textMap = new HashMap<String, String>();
        textMap.put("userName", "董宇");
        textMap.put("key", "1234qwer");

        //设置file的name，路径
        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("file", readFilePath);

        HttpURLConnection conn = null;

        OutputStream out = null;
        BufferedReader reader = null;

        try {

            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setRequestProperty("Cookie","testMethod=POST;accessToken=2c81fd43-a991-4f78-bbce-21be2054431e_105502;type=香香");
            conn.setRequestProperty("Authorization", "authorization_" + UUID.randomUUID().toString());
            conn.setRequestProperty("store", Base64.getEncoder().encodeToString("华为应用商店".getBytes("utf-8")));


            conn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);

            //POST参数  将参数信息 输出到连接中 （我方服务器 输出到 目标服务器）
            out = new DataOutputStream(conn.getOutputStream());

            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();

                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }

            // file
            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {continue;}

                    File file = new File(inputValue);
                    if (!file.exists()){continue;}

                    //没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
                    String contentType = new MimetypesFileTypeMap().getContentType(file);

                    if(StringUtils.isBlank(contentType)){
                        contentType = "application/octet-stream";
                    }

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + file.getName() + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());

                    DataInputStream in = new DataInputStream(new FileInputStream(file));

                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = in.read(bytes)) != -1) {
                        out.write(bytes, 0,  len);
                    }

                    in.close();
                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();

            // 如果code为200
            if (conn.getResponseCode() == 200) {

                //读取连接中的返回数据, 并对输入流对象进行包装:charset根据工作项目组的要求来设置
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                //返回的response结果
                StringBuffer responseContent = new StringBuffer();
                String temp;
                // 循环遍历一行一行读取数据
                while ((temp = reader.readLine()) != null) {
                    responseContent.append(temp);
                    responseContent.append("\r\n");
                }
                System.out.println(responseContent.toString());

            }else{
                System.out.println("错误码-" + conn.getResponseCode());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }

            if (conn != null){
                conn.disconnect();
            }

        }
    }



    @Test
    public void doPostJSON() {

        HttpURLConnection conn = null;

        OutputStreamWriter out = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setRequestProperty("Cookie","testMethod=POST;accessToken=2c81fd43-a991-4f78-bbce-21be2054431e_105502;type=香香");
            conn.setRequestProperty("Authorization", "authorization_" + UUID.randomUUID().toString());
            String store = Base64.getEncoder().encodeToString("华为应用商店".getBytes("utf-8"));
            System.out.println(store);
            conn.setRequestProperty("store", store);


            //application/json;charset=UTF-8
            conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");



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
            Map<String, String> requestParam = new HashMap<>();
            requestParam.put("userName", "吕布");
            requestParam.put("key",      "1234qwer");
            requestParam.put("file",     strBase64);

            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(JSON.toJSONString(requestParam));
            out.flush();


            // 如果code为200
            if (conn.getResponseCode() == 200) {


                //读取连接中的返回数据, 并对输入流对象进行包装:charset根据工作项目组的要求来设置
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                //返回的response结果
                StringBuffer responseContent = new StringBuffer();
                String temp;
                // 循环遍历一行一行读取数据
                while ((temp = reader.readLine()) != null) {
                    responseContent.append(temp);
                    responseContent.append("\r\n");
                }
                System.out.println(responseContent.toString());

            }else{
                System.out.println("错误码-" + conn.getResponseCode());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }

            if (conn != null){
                conn.disconnect();
            }
        }
    }



    @Test
    public void doPostXML() {

        HttpURLConnection conn = null;

        OutputStreamWriter out = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setRequestProperty("Cookie","testMethod=POST;accessToken=2c81fd43-a991-4f78-bbce-21be2054431e_105502;type=香香");
            conn.setRequestProperty("Authorization", "authorization_" + UUID.randomUUID().toString());
            String store = Base64.getEncoder().encodeToString("华为应用商店".getBytes("utf-8"));
            conn.setRequestProperty("store", store);


            //application/json;charset=UTF-8
            conn.setRequestProperty("Content-Type","application/xml");

            //POST参数  将参数信息 输出到连接中 （我方服务器 输出到 目标服务器）
            Student student1 = new Student();
            student1.setId(UUID.randomUUID().toString().replace("-", ""));
            student1.setName("貂蝉Mm");
            student1.setMoney(new BigDecimal("1314.98"));
            student1.setAliveFlag(true);

            Order order1 = new Order();
            order1.setGoodsName("上衣");

            Order order2 = new Order();
            order2.setGoodsName("裙子");

            List<Order> orderList = new ArrayList<>();
            orderList.add(order1);
            orderList.add(order2);

            student1.setOrderList(orderList);

            XStream xStream = new XStream();
            xStream.processAnnotations(Student.class);
            String xmlStr = xStream.toXML(student1);

            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(xmlStr);
            out.flush();


            // 如果code为200
            if (conn.getResponseCode() == 200) {


                //读取连接中的返回数据, 并对输入流对象进行包装:charset根据工作项目组的要求来设置
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                //返回的response结果
                StringBuffer responseContent = new StringBuffer();
                String temp;
                // 循环遍历一行一行读取数据
                while ((temp = reader.readLine()) != null) {
                    responseContent.append(temp);
                    responseContent.append("\r\n");
                }
                System.out.println(responseContent.toString());

            }else{
                System.out.println("错误码-" + conn.getResponseCode());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }

            if (conn != null){
                conn.disconnect();
            }
        }
    }
}
