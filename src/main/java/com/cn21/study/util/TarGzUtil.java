package com.cn21.study.util;

import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.apache.tools.tar.TarOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author cbx
 * @date 2018/10/9
 **/
public class TarGzUtil {

    private static final Logger log = LoggerFactory.getLogger(TarGzUtil.class);

    /**
     * tar压缩
     * @param sourcePath
     * @param targetPath
     */
    public static void tarFile(String sourcePath,String targetPath){
        try(TarOutputStream tarOutputStream = new TarOutputStream(new FileOutputStream(new File(targetPath))) ){
            tarOperation(tarOutputStream,new File(sourcePath),"");
        }catch (Exception e){
            log.error("tarFile exception", e);
        }
    }

    /**
     * tar解压
     * @param sourcePath
     * @param targetPath
     */
    public static void unTarFile( String sourcePath,String targetPath){
        File targetFile = new File(targetPath);
        // 如果目录不存在，则创建
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        try(TarInputStream tarInputStream = new TarInputStream(new FileInputStream(new File(sourcePath)))){
            TarEntry entry = null;
            while ((entry = tarInputStream.getNextEntry()) != null){
                if(entry.isDirectory()){
                    continue;
                }
                String name = targetPath + "/" + entry.getName();
                // 需要判断文件所在的目录是否存在，处理压缩包里面有文件夹的情况
                File tempFile = new File(name.substring(0,name.lastIndexOf("/")));
                if (!tempFile.exists()){
                    tempFile.mkdirs();
                }
                try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(name)))){
                    int len;
                    byte[] buffer = new byte[1024];
                    while((len = tarInputStream.read(buffer)) > 0){
                        outputStream.write(buffer,0,len);
                    }
                }

            }

        }catch (Exception e){
            log.error("unTarFile exception", e);
        }
    }

    public static void gzFile(String sourcePath,String targetPath){
        try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(sourcePath)));
            GZIPOutputStream outputStream = new GZIPOutputStream(new FileOutputStream(new File(targetPath)))){
            byte[] buffer = new byte[1024];
            int len;
            while((len = in.read(buffer)) > 0){
                outputStream.write(buffer,0,len);
            }

        }catch (Exception e){
            log.error("gzFile exception",e);
        }
    }

    /**
     * 解压tar.gz文件
     * @param sourcePath
     * @param targetPath
     */
    public static void unGzFile(String sourcePath,String targetPath){
        String name = sourcePath.substring(sourcePath.lastIndexOf("/") + 1,sourcePath.length());
        String tarName = targetPath + name.substring(0,name.lastIndexOf("."));
        try(GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(new File(sourcePath)));
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(tarName)))){
            byte[] buffer = new byte[1024];
            int len;
            while((len = inputStream.read(buffer)) > 0){
                outputStream.write(buffer,0,len);
            }
            outputStream.flush();
            unTarFile(tarName,targetPath);

        }catch (Exception e){
            log.error("unGzFile exception",e);
        }
    }



    /**
     * tar压缩具体操作
     * @param tarOutputStream
     * @param file
     * @param path
     */
    private static void tarOperation(TarOutputStream tarOutputStream, File file, String path){
        // 如果是目录，则递归进行处理
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for (File tempFile : files) {
                tarOperation(tarOutputStream, tempFile,path + "/" + tempFile.getName());
            }
        }
        else{
            // 如果是单个文件，再进行压缩
            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
                //TarEntry entry = new TarEntry(path);
                // 如果不设置size,会出现request to write '1024' bytes exceeds size in header of '0' bytes for entry错误
                //entry.setSize(file.length());
                TarEntry entry = new TarEntry(file);
                tarOutputStream.putNextEntry(entry);

                int len;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) > 0) {
                    tarOutputStream.write(buffer, 0, len);
                }
                tarOutputStream.closeEntry();
            }catch (Exception e){
                log.error("zipOperation exception", e);
            }
        }


    }

}
