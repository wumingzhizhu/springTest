package com.cn21.study.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * zip工具类
 * @author cbx
 * @date 2018/10/5
 **/
public class ZipUtil {

    private final static Logger log = LoggerFactory.getLogger(ZipUtil.class);

    /**
     * zip压缩
     * @param sourcePath
     * @param targetPath
     */
    public static void createZip(String sourcePath, String targetPath) {
        //获取该目录下所有文件以及文件夹
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File(targetPath))))) {
            zipOperation(zipOutputStream,new File(sourcePath),"");
        } catch (IOException e) {
            log.error("createZip exception", e);
        }

    }

    /**
     * 解压zip文件
     * @param sourcePath
     * @param targetPath
     */
    public static void unZip(String sourcePath,String targetPath){
        File targetFile = new File(targetPath);
        // 如果目录不存在，则创建
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        try(ZipFile zipFile = new ZipFile(new File(sourcePath))) {
            Enumeration enumeration = zipFile.entries();
            while(enumeration.hasMoreElements()){
                ZipEntry entry = (ZipEntry) enumeration.nextElement();
                String name = entry.getName();
                if(entry.isDirectory()){
                    continue;
                }
                try(BufferedInputStream inputStream = new BufferedInputStream(zipFile.getInputStream(entry))){
                    // 需要判断文件所在的目录是否存在，处理压缩包里面有文件夹的情况
                    String outName = targetPath + "/" + name;
                    File outFile = new File(outName);
                    File tempFile = new File(outName.substring(0,outName.lastIndexOf("/")));
                    if (!tempFile.exists()){
                        tempFile.mkdirs();
                    }
                    try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile))){
                        int len;
                        byte[] buffer = new byte[1024];
                        while((len = inputStream.read(buffer)) > 0){
                            outputStream.write(buffer,0,len);
                        }
                    }

                }

            }

        } catch (Exception e){
            log.error("unzip exception", e);
        }
    }

    /**
     * 压缩具体操作
     * @param zipOutputStream
     * @param file
     * @param path
     */
    private static void zipOperation(ZipOutputStream zipOutputStream,File file,String path){
        // 如果是目录，则递归进行处理
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for (File tempFile : files) {
                zipOperation(zipOutputStream, tempFile,path + "/" + tempFile.getName());
            }
        }
        else{
            // 如果是单个文件，再进行压缩
            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
                ZipEntry entry = new ZipEntry(path);
                zipOutputStream.putNextEntry(entry);

                int len;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }
                zipOutputStream.closeEntry();
            }catch (Exception e){
                log.error("zipOperation exception", e);
            }
        }


    }

}
