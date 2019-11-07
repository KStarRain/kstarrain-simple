package com.kstarrain.httpclient;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;


@Slf4j
public class OcrTest  {


    private static final String HASH_ALGORITHM = "HmacSHA256";

    private String api_key = "af901ce02e57415d9e69d47f333d6c7a";

    private String api_secret = "86ee7ac406734b8ba346bc883b3252d9";

    private String url_ocr_idcard = "https://v2-auth-api.visioncloudapi.com/ocr/idcard/stateless";

    final String uploadPath = "E:" + File.separator + "其他" + File.separator + "idcard.jpg";



    @Test
    public void ocrTest() {


        CloseableHttpClient httpclient = null;

            try {
                httpclient = HttpClients.createDefault();
                HttpPost post = new HttpPost(url_ocr_idcard);
                post.setHeader("Authorization", this.genAuthorization());
                post.setEntity(MultipartEntityBuilder.create()
                        .addPart("image_file", new FileBody(new File(uploadPath)))
                        .addTextBody("classify", "true")
                        .addTextBody("return_score", "true")
                        .build());


                log.info("【调用商汤科技服务开始】 -- url:{}, 请求头:{}, 请求体:{}", url_ocr_idcard, post.getAllHeaders(), post.getEntity().toString());
                HttpResponse response = httpclient.execute(post);
                log.info("【调用商汤科技服务结束】 -- 网络状态码:{}, 响应体:{}", response.getStatusLine().getStatusCode() , EntityUtils.toString(response.getEntity(), "UTF-8"));

            } catch (Exception e) {
                log.error("【调用商汤科技服务异常】 -- {}",e.getMessage(), e);
            } finally {
                this.closeResource(httpclient);
            }


    }


    private String genAuthorization() throws SignatureException {

        //1.获得timestamp(unix时间戳)
        String timestamp = System.currentTimeMillis() + "";
        //2.生成长度为16的字母和数字的随机组合字符串nonce
        String nonce = RandomStringUtils.randomAlphanumeric(16);
        //3.将timestamp、nonce、api_key 这三个字符串进行升序排列（依据字符串首位字符的ASCII码)，并join成一个字符串
        String joinString = this.genJoinString(api_key, timestamp, nonce);
        //4.用api_secret对join_string做hamc-sha256签名，且以16进制编码
        String encryptedString = this.genEncryptString(joinString);

        String authorization = "key=" + api_key
                + ",timestamp=" + timestamp
                + ",nonce=" + nonce
                + ",signature=" + encryptedString;
        return authorization;
    }

    private String genJoinString(String apiKey, String timestamp, String nonce) {

        ArrayList<String> beforesort = new ArrayList<String>();
        beforesort.add(apiKey);
        beforesort.add(timestamp);
        beforesort.add(nonce);

        //排序
        Collections.sort(beforesort, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                try {
                    String s1 = new String(o1.toString().getBytes("GB2312"), "ISO-8859-1");
                    String s2 = new String(o2.toString().getBytes("GB2312"), "ISO-8859-1");
                    return s1.compareTo(s2);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                return 0;
            }
        });

        //拼接字符串
        StringBuffer aftersort = new StringBuffer();
        for (String str : beforesort) {
            aftersort.append(str);
        }

        return aftersort.toString();
    }

    private String genEncryptString(String oriString) throws SignatureException {
        Formatter formatter = null;
        try {
            //根据给定的字节数组和算法(hamc-sha256)构造一个密钥。
            Key sk = new SecretKeySpec(api_secret.getBytes(), HASH_ALGORITHM);
            //根据指定算法(hamc-sha256)构造Mac
            Mac mac = Mac.getInstance(sk.getAlgorithm());
            //使用给定的键初始化此Mac对象
            mac.init(sk);
            //处理给定的字节数组并完成Mac操作
            final byte[] hmac = mac.doFinal(oriString.getBytes());
            //加密后的字符串
            StringBuilder sb = new StringBuilder(hmac.length * 2);
            //准备将数据转换成指定格式
            formatter = new Formatter(sb);
            for (byte b : hmac) {
                //以十六进制输出,2为指定的输出字段的宽度.如果位数小于2,则左端补0
                formatter.format("%02x", b);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e1) {
            throw new SignatureException("error building signature, no such algorithm in device " + HASH_ALGORITHM);
        } catch (InvalidKeyException e) {
            throw new SignatureException("error building signature, invalid key " + HASH_ALGORITHM);
        } finally {
            if (formatter != null) {
                formatter.close();
            }
        }
    }

    private void closeResource(CloseableHttpClient httpclient) {
        if (httpclient != null) {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error("httpclient close error", e);
            }
        }
    }


}
