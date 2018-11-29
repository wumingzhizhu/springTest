package com.zeromk.study.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cbx
 * @date 2018/11/29
 **/
public class FtpClientFactory {

    public static final Logger log = LoggerFactory.getLogger(FtpClientFactory.class);

    private FtpClientConfig config;

    private String encode = "UTF8";

    public FtpClientFactory(FtpClientConfig config) {
        this.config = config;
    }

    public FtpClientFactory(FtpClientConfig config,String encode) {
        this.encode = encode;
    }


    public FTPClient makeClient() throws Exception{
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(1000 * 10);
        try {
            ftpClient.connect(config.getHost(), config.getPort());
            boolean result = ftpClient.login(config.getUsername(), config.getPassword());
            if(!result) {
                log.info("ftp登录失败,username: {}",config.getUsername());
                return null;
            }

            ftpClient.setControlEncoding(encode);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //被动模式 被动模式是客户端向服务端发送PASV命令，服务端随机开启一个端口并通知客户端，客户端根据该端口与服务端建立连接，然后发送数据。服务端是两种模式的，
            //使用哪种模式取决于客户端，同时关键点在于网络环境适合用哪种模式，比如客户端在防火墙内，则最好选择被动模式
            //在mac下测试用被动模式没问题，用主动模式则报错，在linux服务器上则相反
            //ftpClient.enterLocalPassiveMode();
            ftpClient.enterLocalActiveMode();

        } catch (Exception e) {
            log.error("makeClient exception",e);
            destroyClient(ftpClient);
            throw e;
        }
        return ftpClient;
    }

    public void destroyClient(FTPClient ftpClient) {
        try {
            if(ftpClient != null && ftpClient.isConnected()) {
                ftpClient.logout();
            }
        } catch (Exception e) {
            log.error("ftpClient logout exception",e);
        } finally {
            try {
                if(ftpClient != null) {
                    ftpClient.disconnect();
                }
            } catch (Exception e2) {
                log.error("ftpClient disconnect exception",e2);
            }

        }
    }

    public boolean validateClient(FTPClient ftpClient) {
        try {
            return ftpClient.sendNoOp();
        } catch (Exception e) {
            log.error("ftpClient validate exception",e);
        }
        return false;
    }
}
