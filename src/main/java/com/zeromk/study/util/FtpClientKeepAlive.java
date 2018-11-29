package com.zeromk.study.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

/**
 * @author cbx
 * @date 2018/11/29
 **/
public class FtpClientKeepAlive {

    private static final Logger log = LoggerFactory.getLogger(FtpClientKeepAlive.class);

    private KeepAliveThread keepAliveThread;

    @Autowired
    private FtpClientPool ftpClientPool;

    public void init() {
        // 启动心跳检测线程
        if (keepAliveThread == null) {
            keepAliveThread = new KeepAliveThread();
            Thread thread = new Thread(keepAliveThread);
            thread.start();
        }
    }

    class KeepAliveThread implements Runnable {
        @Override
        public void run() {
            FTPClient ftpClient = null;
            while (true) {
                try {
                    BlockingQueue<FTPClient> pool = ftpClientPool.getPool();
                    if (pool != null && pool.size() > 0) {
                        Iterator<FTPClient> it = pool.iterator();
                        while (it.hasNext()) {
                            ftpClient = it.next();
                            boolean result = ftpClient.sendNoOp();
                            log.info("心跳结果: {}",result);
                            if (!result) {
                                ftpClientPool.invalidateClient(ftpClient);
                            }
                        }

                    }
                } catch (Exception e) {
                    log.error("ftp心跳检测异常", e);
                    ftpClientPool.invalidateClient(ftpClient);
                }

                // 每30s发送一次心跳，服务器超时时间为60s
                try {
                    Thread.sleep(1000 * 30);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    log.error("ftp休眠异常", e);
                }
            }

        }
    }
}
