package com.refund.common.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import com.refund.common.config.RuoYiConfig;
import com.refund.common.constant.Constants;
import com.refund.common.utils.DateUtils;
import com.refund.common.utils.StringUtils;
import com.refund.common.utils.uuid.IdUtils;

/**
 * 文件处理工具类
 * 
 * @author ruoyi
 */
public class FileUtils
{
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 输出指定文件的byte数组
     * 
     * @param filePath 文件路径
     * @param os 输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException
    {
        // 如果是OSS URL，直接返回错误，因为OSS文件应该通过URL直接访问
        if (isOssUrl(filePath)) {
            throw new IOException("OSS文件不支持直接写入，应通过URL访问");
        }
        
        FileInputStream fis = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            IOUtils.close(os);
            IOUtils.close(fis);
        }
    }

    /**
     * 写数据到文件中
     *
     * @param data 数据
     * @return 目标文件
     * @throws IOException IO异常
     */
    public static String writeImportBytes(byte[] data) throws IOException
    {
        return writeBytes(data, RuoYiConfig.getImportPath());
    }

    /**
     * 写数据到文件中
     *
     * @param data 数据
     * @param uploadDir 目标文件
     * @return 目标文件
     * @throws IOException IO异常
     */
    public static String writeBytes(byte[] data, String uploadDir) throws IOException
    {
        // 如果使用OSS，则不写入本地文件
        if (RuoYiConfig.isUseOss()) {
            throw new IOException("当前使用OSS存储，不支持本地文件写入");
        }
        
        FileOutputStream fos = null;
        String pathName = "";
        try
        {
            String extension = getFileExtendName(data);
            pathName = uploadDir + "/" + IdUtils.fastUUID() + "." + extension;
            fos = new FileOutputStream(pathName);
            fos.write(data);
        }
        finally
        {
            IOUtils.close(fos);
        }
        return pathName;
    }

    /**
     * 删除文件
     * 
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        // 如果是OSS URL，不执行删除操作，因为OSS文件无法通过此方法删除
        if (isOssUrl(filePath)) {
            // OSS文件应该通过OSS API删除，而不是本地删除
            return false;
        }
        
        if (StringUtils.isEmpty(filePath))
        {
            return false;
        }
        File del = new File(filePath);
        return del.exists() && del.delete();
    }

    /**
     * 文件名称验证
     * 
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename)
    {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 检查文件是否允许下载
     * 
     * @param resource 需要下载的文件
     * @return true 正常 false 非法
     */
    public static boolean checkAllowDownload(String resource)
    {
        // 如果是OSS URL，允许下载（通过URL访问）
        if (isOssUrl(resource)) {
            return true;
        }
        
        // 检查文件路径是否包含".."，防止路径遍历攻击
        if (StringUtils.contains(resource, ".."))
        {
            return false;
        }

        return true;
    }

    /**
     * 下载文件名重新编码
     * 
     * @param request 请求对象
     * @param fileName 文件名
     * @return
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException
    {
        String encodedfileName = null;
        if (request.getHeader("User-Agent").toUpperCase().contains("MSIE") || request.getHeader("User-Agent").toUpperCase().contains("TRIDENT"))
        {
            encodedfileName = URLEncoder.encode(fileName, "UTF-8");
        }
        else
        {
            encodedfileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        }
        return encodedfileName;
    }

    /**
     * 下载文件名重新编码
     *
     * @param response 响应对象
     * @param realFileName 真实文件名
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException
    {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=")
                .append(percentEncodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(percentEncodedFileName);

        response.setHeader("Content-Disposition", contentDispositionValue.toString());
    }

    /**
     * 百分号编码
     *
     * @param s 需要编码的字符串
     * @return 编码结果
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException
    {
        String encode = URLEncoder.encode(s, "UTF-8");
        // 解决空格被编码为+号的问题
        encode = encode.replace("+", "%20");
        encode = encode.replace("*", "%2A");
        encode = encode.replace("~", "%7E");
        encode = encode.replace("/", "%2F");
        return encode;
    }

    /**
     * 获取文件名
     * 
     * @param filePath 文件路径
     * @return 文件名
     */
    public static final String getName(String filePath)
    {
        if (null == filePath)
        {
            return null;
        }
        int separatorIndex = filePath.lastIndexOf("/");
        if (separatorIndex < 0)
        {
            separatorIndex = filePath.lastIndexOf("\\");
        }
        if (separatorIndex < 0)
        {
            return filePath;
        }
        return StringUtils.substring(filePath, separatorIndex + 1);
    }

    /**
     * 获取文件后缀名
     * 
     * @param filePath 文件路径
     * @return 后缀名
     */
    public static final String getExtension(String filePath)
    {
        if (null == filePath)
        {
            return null;
        }
        int separatorIndex = filePath.lastIndexOf(".");
        if (separatorIndex < 0)
        {
            return "";
        }
        return StringUtils.substring(filePath, separatorIndex + 1);
    }

    /**
     * 获取文件后缀
     * 
     * @param path 文件路径
     * @return 后缀
     */
    public static String getFileExtendName(byte[] photoByte)
    {
        String strFileExtendName = "jpg";
        if (photoByte[0] == 71 && photoByte[1] == 73 && photoByte[2] == 70)
        {
            strFileExtendName = "gif";
        }
        else if (photoByte[6] == 74 && photoByte[7] == 70 && photoByte[8] == 73 && photoByte[9] == 70)
        {
            strFileExtendName = "jpg";
        }
        else if (photoByte[0] == 66 && photoByte[1] == 77)
        {
            strFileExtendName = "bmp";
        }
        else if (photoByte[1] == 80 && photoByte[2] == 78 && photoByte[3] == 71)
        {
            strFileExtendName = "png";
        }
        return strFileExtendName;
    }

    /**
     * 获取路径
     * 
     * @param filePath 文件路径
     * @return 路径
     */
    public static final String getSubPrefix(String filePath)
    {
        if (null == filePath)
        {
            return null;
        }
        int separatorIndex = filePath.lastIndexOf("/");
        if (separatorIndex < 0)
        {
            separatorIndex = filePath.lastIndexOf("\\");
        }
        if (separatorIndex < 0)
        {
            return "";
        }
        return StringUtils.substring(filePath, 0, separatorIndex);
    }

    /**
     * 获取文件相对路径
     * 
     * @param path 文件路径
     * @param remotePath 相对路径
     * @return 文件相对路径
     */
    public static String stripPrefix(String path, String remotePath)
    {
        if (StringUtils.isEmpty(path))
        {
            return path;
        }
        if (StringUtils.isEmpty(remotePath))
        {
            return StringUtils.substringAfter(path, Constants.RESOURCE_PREFIX);
        }
        return StringUtils.substringAfter(path, remotePath + "/");
    }

    /**
     * 获取文件相对路径
     * 
     * @param path 文件路径
     * @return 文件相对路径
     */
    public static String stripPrefix(String path)
    {
        return stripPrefix(path, "");
    }

    /**
     * 判断是否为OSS URL
     * 
     * @param path 路径
     * @return 是否为OSS URL
     */
    public static boolean isOssUrl(String path)
    {
        return StringUtils.isNotEmpty(path) && (path.startsWith("http://") || path.startsWith("https://"));
    }
}