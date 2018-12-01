package com.zeromk.study.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * 读取properties文件
 *
 * @author cbx
 * @date 2018/12/1
 **/
public class PropertyUtil {

    private static final Logger log = LoggerFactory.getLogger(PropertyUtil.class);

    public static String getProperty(String fileName,String key){
        try {
            PropertyResourceBundle propertyResourceBundle = (PropertyResourceBundle) ResourceBundle.getBundle(fileName);
            return propertyResourceBundle.getString(key);
        }catch (Exception e){
            log.error("getProperty exception", e);
        }
        return null;
    }

}
