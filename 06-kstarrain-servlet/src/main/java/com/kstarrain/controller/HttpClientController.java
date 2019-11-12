package com.kstarrain.controller;

import com.alibaba.fastjson.JSON;
import com.kstarrain.app.RequestParam;
import com.kstarrain.response.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.List;

/**
 * @author: Dong Yu
 * @create: 2018-10-16 17:19
 * @description:
 */
@Slf4j
public class HttpClientController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("===============  初始化 Test01Controller  ===============");
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("开始执行 HttpClientController 的doGet方法");

        System.out.println("Cookie : " + request.getHeader("Cookie"));
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)){
            System.out.println("Cookie详情 : ");
            for (Cookie cookie : cookies) {
                System.out.println("         " + cookie.getName() + "=" + cookie.getValue());
            }
        }

        System.out.println("Content-Type : " + request.getHeader("Content-Type"));
        System.out.println("Authorization : " + request.getHeader("Authorization"));
        System.out.println("userName : " + request.getParameter("userName"));
        System.out.println("key : " + request.getParameter("key"));

        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setMessage("成功");

        //响应
//        response.setContentType("text/plain;charset=UTF-8");
//        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().append(JSON.toJSONString(resultDTO));

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("开始执行 HttpClientController 的doPost方法");

        //请求编码
        request.setCharacterEncoding("UTF-8");

        System.out.println("Authorization : " + request.getHeader("Authorization"));

        String store = request.getHeader("store");
        if (StringUtils.isNotBlank(store)){
            //需要decode去解析汉字
            System.out.println("store : " + new String(Base64.getDecoder().decode(store), "utf-8"));
        }


        //通过getHeader获取Cookie时，汉字编码会有问题
        System.out.println("Cookie : " + request.getHeader("Cookie"));

        //通过getCookies可以解决汉字编码问题
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)){
            System.out.println("Cookie详情 : ");
            for (Cookie cookie : cookies) {
                System.out.println("         " + cookie.getName() + "=" + cookie.getValue());
            }
        }

        System.out.println("接收数据---------------------");
        String contentType = request.getHeader("Content-Type");
        System.out.println("Content-Type : " + contentType);

        System.out.println("userName : " + request.getParameter("userName"));
        System.out.println("key : " + request.getParameter("key"));


        //接受 application/json;charset=UTF-8 或 application/json
        if (contentType.startsWith("application/json")){

            this.parseJsonData(request,response);
            this.returnJsonResponse(response);
        }
        //接受 multipart/form-data; 此时通过request.getParameter获取不到值 通过commons-io和commons-fileupload进行取值
        else if (contentType.startsWith("multipart/form-data")){

            this.parseMultipartData(request,response);
        }
        //接受 application/x-www-form-urlencoded 只有此时，request.getParameter才能获取到值
//        if ("application/x-www-form-urlencoded".equals(contentType)){
        else {

            System.out.println("userName : " + request.getParameter("userName"));
            System.out.println("key : " + request.getParameter("key"));

            this.returnJsonResponse(response);
        }

    }

    private void parseMultipartData(HttpServletRequest request, HttpServletResponse response){

        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");

            List<FileItem> fileItems = upload.parseRequest(request);
            for (FileItem fileItem : fileItems) {

                //提交的表单中，文件键值为file
                if(fileItem.getFieldName().equals("file")){

                    response.setHeader("Accept-Ranges","bytes");
                    response.setHeader("Cache-Control","no-store");

                    String fileName = fileItem.getName();

                    String contentType = "";
                    if (fileName.endsWith(".png")) {
                        contentType = "image/png";
                    }else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".jpe")) {
                        contentType = "image/jpeg";
                    }else if (fileName.endsWith(".gif")) {
                        contentType = "image/gif";
                    }

                    response.setContentType(contentType);

                    /**将传入的文件复制输出到本地上*/
                    copyFileToLocal(fileItem);

                    /**将传入的文件以流的形式输出到response中*/
                    writeFileToResponse(fileItem, response);


                    System.out.println(fileItem.getFieldName() + " : " + fileItem.getName());

                }else {
                    System.out.println(fileItem.getFieldName() + " : " + fileItem.getString("UTF-8"));
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

    }

    private void writeFileToResponse(FileItem fileItem, HttpServletResponse response){

        InputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = fileItem.getInputStream();
            //要从HttpServletResponse中获取输出流才管用
            output = new BufferedOutputStream(response.getOutputStream());

            byte b[] = new byte[1024];
            int len = input.read(b);
            while (len > 0) {
                output.write(b, 0, len);
                len = input.read(b);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {

            try {
                output.close();
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }

            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void copyFileToLocal(FileItem fileItem) {

        String toPath = "E:" + File.separator + "test" + File.separator + "file"  + File.separator + fileItem.getName();

        InputStream input = null;
        OutputStream output = null;

        try {

            File file = new File(toPath);
            if(!file.getParentFile().exists()){file.getParentFile().mkdirs();}

            input = fileItem.getInputStream();
            output = new FileOutputStream(file);

            byte[] bytes = new byte[1024];

            int len;
            while((len = input.read(bytes)) != -1){
                output.write(bytes,0,len);
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {

            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    //解析json类型的数据
    private void parseJsonData(HttpServletRequest request, HttpServletResponse response){
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            StringBuffer requestStrB = new StringBuffer();
            String temp;
            // 循环遍历一行一行读取数据
            while ((temp = reader.readLine()) != null) {
                requestStrB.append(temp);
                requestStrB.append("\r\n");
            }

            String requestStr = requestStrB.toString();
            System.out.println("request ：" + requestStr);

            RequestParam requestParam = JSON.parseObject(requestStr, RequestParam.class);

            if (StringUtils.isNotBlank(requestParam.getFile())){

                String toPath = "E:" + File.separator + "test" + File.separator + "file"  + File.separator + "catFromJson.jpg";


                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(new File(toPath));
                    outputStream.write(Base64.getDecoder().decode(requestParam.getFile()));

                }finally {
                    try {
                        if (outputStream != null) { // 安全关闭
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage(),e);
                    }
                }
            }

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
        }
    }

    private void returnJsonResponse(HttpServletResponse response) throws IOException {

        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setMessage("成功");

        //response
        response.setContentType("application/json;charset=UTF-8");
        //返回json
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(resultDTO));
        writer.close();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Put request to TestController success");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Delete request to TestController success");
    }

    @Override
    public void destroy() {
        System.out.println("===============  销毁 Test01Controller ===============");
    }
}
