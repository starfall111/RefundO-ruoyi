package com.refund.root.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * APK文件MD5计算工具类
 */
public class ApkMd5Utils {

    // 缓存大小（8KB），平衡读取效率和内存占用
    private static final int BUFFER_SIZE = 8192;
    // MD5算法名称
    private static final String MD5_ALGORITHM = "MD5";

    /**
     * 计算文件的MD5值（32位小写字符串）
     * @param filePath 文件绝对路径（如APK路径）
     * @return MD5字符串（32位小写），计算失败返回null
     */
    public static String calculateFileMd5(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.err.println("文件不存在或不是合法文件：" + filePath);
            return null;
        }
        return calculateFileMd5(file);
    }

    /**
     * 重载方法：直接传入File对象计算MD5
     * @param file 目标文件（APK）
     * @return MD5字符串（32位小写），计算失败返回null
     */
    public static String calculateFileMd5(File file) {
        MessageDigest md5Digest = null;
        FileInputStream fis = null;
        try {
            // 初始化MD5摘要算法
            md5Digest = MessageDigest.getInstance(MD5_ALGORITHM);
            fis = new FileInputStream(file);

            // 分块读取文件字节，避免大文件占用过多内存
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                md5Digest.update(buffer, 0, bytesRead);
            }

            // 计算最终哈希值
            byte[] md5Bytes = md5Digest.digest();
            // 将字节数组转换为32位小写十六进制字符串
            return bytesToHexString(md5Bytes);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("MD5算法不存在（JDK环境异常）：" + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("读取文件失败：" + e.getMessage());
            return null;
        } finally {
            // 关闭流资源
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    System.err.println("关闭文件流失败：" + e.getMessage());
                }
            }
        }
    }

    /**
     * 重载方法：支持远程URL计算MD5
     * @param urlStr 远程文件URL地址
     * @return MD5字符串（32位小写），计算失败返回null
     */
    public static String calculateRemoteFileMd5(String urlStr) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            
            // 设置请求参数
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000); // 10秒连接超时
            connection.setReadTimeout(30000);    // 30秒读取超时
            
            // 检查响应码
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("远程文件访问失败，响应码：" + responseCode);
                return null;
            }
            
            inputStream = connection.getInputStream();
            
            // 初始化MD5摘要算法
            MessageDigest md5Digest = MessageDigest.getInstance(MD5_ALGORITHM);
            
            // 分块读取远程文件字节，避免大文件占用过多内存
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                md5Digest.update(buffer, 0, bytesRead);
            }
            
            // 计算最终哈希值
            byte[] md5Bytes = md5Digest.digest();
            // 将字节数组转换为32位小写十六进制字符串
            return bytesToHexString(md5Bytes);
            
        } catch (NoSuchAlgorithmException e) {
            System.err.println("MD5算法不存在（JDK环境异常）：" + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("读取远程文件失败：" + e.getMessage());
            return null;
        } finally {
            // 关闭连接和流资源
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.err.println("关闭输入流失败：" + e.getMessage());
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    /**
     * 统一方法：根据路径类型自动判断是本地文件还是远程URL
     * @param filePathOrUrl 本地文件路径或远程URL地址
     * @return MD5字符串（32位小写），计算失败返回null
     */
    public static String getFileMD5(String filePathOrUrl) {
        if (filePathOrUrl == null || filePathOrUrl.isEmpty()) {
            return null;
        }
        
        // 使用正则表达式检测是否为URL
        Pattern urlPattern = Pattern.compile("^https?://.*");
        if (urlPattern.matcher(filePathOrUrl).matches()) {
            // 是URL，使用远程文件计算方法
            return calculateRemoteFileMd5(filePathOrUrl);
        } else {
            // 是本地路径，使用原有方法
            return calculateFileMd5(filePathOrUrl);
        }
    }

    /**
     * 将字节数组转换为十六进制字符串（32位小写）
     * @param bytes 哈希计算后的字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHexString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder hexSb = new StringBuilder();
        for (byte b : bytes) {
            // 转换为无符号整数（0-255）
            int intValue = b & 0xFF;
            // 不足两位补0（如0x0A → "0a"）
            if (intValue < 16) {
                hexSb.append("0");
            }
            hexSb.append(Integer.toHexString(intValue));
        }
        return hexSb.toString();
    }
}