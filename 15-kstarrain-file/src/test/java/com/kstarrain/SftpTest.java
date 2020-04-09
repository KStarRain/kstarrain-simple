package com.kstarrain;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Properties;

/**
 * @author: DongYu
 * @create: 2019-12-23 13:51
 * @description:
 */
@Slf4j
public class SftpTest {

    private String host = "10.161.16.214";
    private int port = 22;
    private String username = "userother";
    private String password = "userother";

    String filePath = "E:" + File.separator + "opt" + File.separator + "data" + File.separator + "wechat" + File.separator +"bill"+ File.separator + "helpRepay";
    String fileName = "wx_dr_20191222.txt";

    @Test
    public void upload(){

        Session session = null;
        Channel channel = null;
        ChannelSftp sftp = null;

        try {
            JSch jSch = new JSch();
            session = jSch.getSession(username, host, port);
            session.setPassword(password);

            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");

            session.setConfig(sshConfig);
            session.connect();
            log.info("Session is connected");

            channel = session.openChannel("sftp");
            channel.connect();
            log.info("channel is connected");

            sftp = (ChannelSftp) channel;

//            Vector ls = sftp.ls("/");
//            for (Object item : ls) {
//                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) item;
//                System.out.println(entry.getFilename());
//            }


            String wechat = "/upload/wx/ex";
            try {
                sftp.cd(wechat);
            } catch (SftpException e) {
                log.warn("directory is not exist");
                sftp.mkdir(wechat);
                sftp.cd(wechat);
            }

            ByteArrayInputStream inputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(filePath + File.separator + fileName)));

            sftp.put(inputStream, fileName);


        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {

            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                }
            }


            if (channel != null) {
                if (channel.isConnected()) {
                    channel.disconnect();
                }
            }

            if (session != null) {
                if (session.isConnected()) {
                    session.disconnect();
                }
            }



        }
    }
}
