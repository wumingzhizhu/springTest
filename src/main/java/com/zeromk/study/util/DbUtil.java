package com.zeromk.study.util;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * @author cbx
 * @date 2018/12/1
 **/
public class DbUtil {

    private static final Logger log = LoggerFactory.getLogger(DbUtil.class);

    /**
     *  数据库连接池
     */
    private static BasicDataSource dbcp;

    /**
     *  为不同线程管理连接
     */
    private static ThreadLocal<Connection> threadLocal;

    static {
        try {
            dbcp = new BasicDataSource();
            dbcp.setDriverClassName(PropertyUtil.getProperty("config", "other.jdbc.driver"));
            dbcp.setUrl(PropertyUtil.getProperty("config", "other.jdbc.url"));
            dbcp.setUsername(PropertyUtil.getProperty("config", "other.jdbc.user"));
            dbcp.setPassword(PropertyUtil.getProperty("config", "other.jdbc.password"));
            // 初始化
            dbcp.setInitialSize(Integer.parseInt(PropertyUtil.getProperty("config", "other.initSize")));
            // 最大连接数
            dbcp.setMaxActive(Integer.parseInt(PropertyUtil.getProperty("config", "other.maxAtive")));
            // 最大等待时间
            dbcp.setMaxWait(Long.parseLong(PropertyUtil.getProperty("config", "other.maxWait")));
            // 最大空闲数
            dbcp.setMaxIdle(Integer.parseInt(PropertyUtil.getProperty("config", "other.maxIdle")));
            // 最小空闲数
            dbcp.setMinIdle(Integer.parseInt(PropertyUtil.getProperty("config", "other.minIdle")));

            threadLocal = new ThreadLocal<>();

        } catch (Exception e) {
            log.error("DbUtil exception",e);
        }
    }

    /**
     * 获取一个连接
     * @return
     */
    public static Connection getConnection() {
        try {
            Connection connection = dbcp.getConnection();
            connection.setAutoCommit(false);
            threadLocal.set(connection);
            return connection;
        } catch (Exception e) {
            log.error("getConnection exception",e);
            return null;
        }
    }

    /**
     * 归还一个连接
     */
    public static void closeConnection() {
        try {
            Connection connection = threadLocal.get();
            if(connection != null) {
                // 实际上没有关闭连接，只是放在池子里
                connection.close();
                threadLocal.remove();
            }
        } catch (Exception e) {
            log.error("closeConnection exception",e);
        }

    }
}
