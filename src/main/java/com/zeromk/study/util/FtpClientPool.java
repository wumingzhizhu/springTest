package com.zeromk.study.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author cbx
 * @date 2018/11/29
 **/
public class FtpClientPool {

    private static final Logger log = LoggerFactory.getLogger(FtpClientPool.class);

    private static final int DEFAULT_POOL_SIZE = 16;

    private BlockingQueue<FTPClient> pool;

    private FtpClientFactory factory;

    public FtpClientPool(FtpClientFactory factory) {
        this(factory, DEFAULT_POOL_SIZE);
    }

    public FtpClientPool(FtpClientFactory factory,int size) {
        this.factory = factory;
        this.pool = new ArrayBlockingQueue<>(size);
        initPool(size);
    }

    /**
     * 初始化
     * @param maxPoolSize
     * @throws Exception
     */
    private void initPool(int maxPoolSize) {
        try {
            int count = 0;
            while (count < maxPoolSize) {
                pool.offer(factory.makeClient(),10,TimeUnit.SECONDS);
                count ++;
            }
        } catch (Exception e) {
            log.error("ftp连接池初始化失败",e);
        }

    }

    public FTPClient borrowClient() throws Exception{
        FTPClient client = pool.take();
        if(client == null) {
            client = factory.makeClient();
            //addClient(client);
            returnClient(client);
        }else if(!factory.validateClient(client)) {
            invalidateClient(client);
            client = factory.makeClient();
            //addClient(client);
            returnClient(client);
        }
        return client;

    }

    /**
     * 添加客户端
     * @param ftpClient
     */
    public void addClient(FTPClient ftpClient) {
        pool.add(ftpClient);
    }


    /**
     * 归还一个对象，如果10秒内无法归还，则销毁改对象
     * @param ftpClient
     */
    public void returnClient(FTPClient ftpClient) throws Exception{
        try {
            if(ftpClient != null && !pool.offer(ftpClient, 10, TimeUnit.SECONDS)) {
                factory.destroyClient(ftpClient);
            }
        } catch (Exception e) {
            log.error("归还对象失败",e);
            throw e;
        }
    }

    /**
     * 使客户端无效
     * @param ftpClient
     */
    public void invalidateClient(FTPClient ftpClient) {
        pool.remove(ftpClient);
        factory.destroyClient(ftpClient);
    }

    public void close() throws Exception {
        while(pool.iterator().hasNext()){
            FTPClient client = pool.take();
            factory.destroyClient(client);
        }
    }

    public BlockingQueue<FTPClient> getPool() {
        return pool;
    }

    public FtpClientFactory getFactory() {
        return factory;
    }
}
