package com.ideayp.leaf.common.util;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

/**
 * <p>Description:  文件工具   </p>
 * <p>email: ypasdf@163.com </p>
 * <p>Copyright: Copyright (c) 2018 </p>
 * <P>Date: 2018/11/29 </P>
 *
 * @author leaf
 * @version 1.0
 */
@SuppressWarnings({"ALL", "unchecked"})
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    /**
     * 逗号
     */
    private static final String COMMA = ".";
    /**
     * 斜杠
     */
    private static final String SPRIT = "/";

    /**
     * 获取附件名称 13位时间戳+5位随机数
     *
     * @return 结果
     */
    public static String createFileName() {
        return System.currentTimeMillis() + "" + RandomUtils.nextInt(0, 100000);
    }


    /**
     * 判断是否图片格式
     *
     * @param suffix 后缀
     * @return 结果
     */
    public static Boolean getImageFormat(String suffix) {
        return "TFF".equalsIgnoreCase(suffix) || "TIFF".equalsIgnoreCase(suffix) || "PNG".equalsIgnoreCase(suffix) || "GIF".equalsIgnoreCase(suffix) || "JPG".equalsIgnoreCase(suffix) || "JPEG".equalsIgnoreCase(suffix);
    }


    /**
     * 保存文件到磁盘
     *
     * @param file     上传的文件
     * @param savePath 保存的路径
     */
    public static File saveFileToDisk(MultipartFile file, String savePath) {
        File localFile = new File(savePath);
        if (!localFile.getParentFile().exists()) {
            boolean mkdirs = localFile.getParentFile().mkdirs();
            if (mkdirs) {
                if (logger.isDebugEnabled()) {
                    logger.debug("mkdirs" + savePath);
                }
            }
        }
        try {
            Files.copy(file.getInputStream(), Paths.get(savePath));
            return localFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存文件到磁盘
     *
     * @param file     保存的数据
     * @param filePath 文件路径
     * @param fileName 文件名
     * @throws Exception
     */
    public static void saveFile(byte[] file, String filePath, String fileName) throws Exception {
        saveFile(file, filePath +File.separator+ fileName);
    }

    public static void saveFile(byte[] file, String fileFullPath) throws Exception {
        File targetFile = new File(fileFullPath);
        File parentFile = targetFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(fileFullPath)) {
            out.write(file);
            out.flush();
        }
    }

    /**
     * 删除文件
     *
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.info("删除单个文件-> {}成功！", fileName);
                return true;
            } else {
                logger.info("删除单个文件-> {}失败！", fileName);
                return false;
            }
        } else {
            logger.info("删除单个文件失败-> {}不存在！", fileName);
            return false;
        }
    }

    /**
     * 复制单个文件，如果目标文件存在，则不覆盖
     *
     * @param srcFileName  待复制的文件名
     * @param descFileName 目标文件名
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String descFileName) {
        return FileUtils.copyFileCover(srcFileName, descFileName, false);
    }

    /**
     * 复制单个文件
     *
     * @param srcFileName  待复制的文件名
     * @param descFileName 目标文件名
     * @param coverlay     如果目标文件已存在，是否覆盖
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFileCover(String srcFileName, String descFileName, boolean coverlay) {
        File srcFile = new File(srcFileName);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            logger.debug("复制文件失败，源文件 " + srcFileName + " 不存在!");
            return false;
        }
        // 判断源文件是否是合法的文件
        else if (!srcFile.isFile()) {
            logger.debug("复制文件失败，" + srcFileName + " 不是一个文件!");
            return false;
        }
        File descFile = new File(descFileName);
        // 判断目标文件是否存在
        if (descFile.exists()) {
            // 如果目标文件存在，并且允许覆盖
            if (coverlay) {
                logger.debug("目标文件已存在，准备删除!");
                if (!FileUtils.delFile(descFileName)) {
                    logger.debug("删除目标文件 " + descFileName + " 失败!");
                    return false;
                }
            } else {
                logger.debug("复制文件失败，目标文件 " + descFileName + " 已存在!");
                return false;
            }
        } else {
            if (!descFile.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建目录
                logger.debug("目标文件所在的目录不存在，创建目录!");
                // 创建目标文件所在的目录
                if (!descFile.getParentFile().mkdirs()) {
                    logger.debug("创建目标文件所在的目录失败!");
                    return false;
                }
            }
        }

        // 准备复制文件
        // 读取的位数
        int readByte;
        InputStream ins = null;
        OutputStream outs = null;
        try {
            // 打开源文件
            ins = new FileInputStream(srcFile);
            // 打开目标文件的输出流
            outs = new FileOutputStream(descFile);
            byte[] buf = new byte[1024];
            // 一次读取1024个字节，当readByte为-1时表示文件已经读取完毕
            while ((readByte = ins.read(buf)) != -1) {
                // 将读取的字节流写入到输出流
                outs.write(buf, 0, readByte);
            }
            logger.debug("复制单个文件 " + srcFileName + " 到" + descFileName + "成功!");
            return true;
        } catch (Exception e) {
            logger.debug("复制文件失败：" + e.getMessage());
            return false;
        } finally {
            // 关闭输入输出流，首先关闭输出流，然后再关闭输入流
            if (outs != null) {
                try {
                    outs.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException ine) {
                    ine.printStackTrace();
                }
            }
        }
    }

    /**
     * 复制整个目录的内容，如果目标目录存在，则不覆盖
     *
     * @param srcDirName  源目录名
     * @param descDirName 目标目录名
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectory(String srcDirName, String descDirName) {
        return FileUtils.copyDirectoryCover(srcDirName, descDirName, false);
    }

    /**
     * 复制整个目录的内容
     *
     * @param srcDirName  源目录名
     * @param descDirName 目标目录名
     * @param coverlay    如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectoryCover(String srcDirName,
                                             String descDirName, boolean coverlay) {
        File srcDir = new File(srcDirName);
        // 判断源目录是否存在
        if (!srcDir.exists()) {
            logger.debug("复制目录失败，源目录 " + srcDirName + " 不存在!");
            return false;
        }
        // 判断源目录是否是目录
        else if (!srcDir.isDirectory()) {
            logger.debug("复制目录失败，" + srcDirName + " 不是一个目录!");
            return false;
        }
        // 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        // 如果目标文件夹存在
        if (descDir.exists()) {
            if (coverlay) {
                // 允许覆盖目标目录
                logger.debug("目标目录已存在，准备删除!");
                if (!FileUtils.delFile(descDirNames)) {
                    logger.debug("删除目录 " + descDirNames + " 失败!");
                    return false;
                }
            } else {
                logger.debug("目标目录复制失败，目标目录 " + descDirNames + " 已存在!");
                return false;
            }
        } else {
            // 创建目标目录
            logger.debug("目标目录不存在，准备创建!");
            if (!descDir.mkdirs()) {
                logger.debug("创建目标目录失败!");
                return false;
            }

        }

        boolean flag = true;
        // 列出源目录下的所有文件名和子目录名
        File[] files = srcDir.listFiles();
        if (null == files) {
            return true;
        }
        for (File file : files) {
            // 如果是一个单个文件，则直接复制
            if (file.isFile()) {
                flag = FileUtils.copyFile(file.getAbsolutePath(),
                        descDirName + file.getName());
                // 如果拷贝文件失败，则退出循环
                if (!flag) {
                    break;
                }
            }
            // 如果是子目录，则继续复制目录
            if (file.isDirectory()) {
                flag = FileUtils.copyDirectory(file
                        .getAbsolutePath(), descDirName + file.getName());
                // 如果拷贝目录失败，则退出循环
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            logger.debug("复制目录 " + srcDirName + " 到 " + descDirName + " 失败!");
            return false;
        }
        logger.debug("复制目录 " + srcDirName + " 到 " + descDirName + " 成功!");
        return true;

    }

    /**
     * 删除文件，可以删除单个文件或文件夹
     *
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否是返回false
     */
    public static boolean delFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            logger.debug(fileName + " 文件不存在!");
            return true;
        } else {
            if (file.isFile()) {
                return FileUtils.deleteFile(fileName);
            } else {
                return FileUtils.deleteDirectory(fileName);
            }
        }
    }


    /**
     * 删除目录及目录下的文件
     *
     * @param dirName 被删除的目录所在的文件路径
     * @return 如果目录删除成功，则返回true，否则返回false
     */
    public static boolean deleteDirectory(String dirName) {
        String dirNames = dirName;
        if (!dirNames.endsWith(File.separator)) {
            dirNames = dirNames + File.separator;
        }
        File dirFile = new File(dirNames);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            logger.debug(dirNames + " 目录不存在!");
            return true;
        }
        boolean flag = true;
        // 列出全部文件及子目录
        File[] files = dirFile.listFiles();
        if (null == files) {
            return true;
        }
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = FileUtils.deleteFile(file.getAbsolutePath());
                // 如果删除文件失败，则退出循环
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = FileUtils.deleteDirectory(file
                        .getAbsolutePath());
                // 如果删除子目录失败，则退出循环
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            logger.debug("删除目录失败!");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            logger.debug("删除目录 " + dirName + " 成功!");
            return true;
        } else {
            logger.debug("删除目录 " + dirName + " 失败!");
            return false;
        }

    }

    /**
     * 创建单个文件
     *
     * @param descFileName 文件名，包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createFile(String descFileName) {
        File file = new File(descFileName);
        if (file.exists()) {
            logger.debug("文件 " + descFileName + " 已存在!");
            return false;
        }
        if (descFileName.endsWith(File.separator)) {
            logger.debug(descFileName + " 为目录，不能创建目录!");
            return false;
        }
        if (!file.getParentFile().exists()) {
            // 如果文件所在的目录不存在，则创建目录
            if (!file.getParentFile().mkdirs()) {
                logger.debug("创建文件所在的目录失败!");
                return false;
            }
        }

        // 创建文件
        try {
            if (file.createNewFile()) {
                //因为是生成到 Linux 上，需要设置权限，自己写的
                //设置可执行权限，第二个参数默认为true（表示root权限才可以执行该文件，false为所有人都可以）
                file.setExecutable(true, false);
                //设置可读权限，第二个参数默认为true（同上）
                file.setReadable(true, false);
                //设置可写权限，第二个参数默认为true（这里可以省略不写）
                file.setWritable(true);
                logger.debug(descFileName + " 文件创建成功!");
                return true;
            } else {
                logger.debug(descFileName + " 文件创建失败!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(descFileName + " 文件创建失败!");
            return false;
        }

    }

    /**
     * 创建目录
     *
     * @param descDirName 目录名,包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            logger.debug("目录 " + descDirNames + " 已存在!");
            return false;
        }
        // 创建目录
        if (descDir.mkdirs()) {
            logger.debug("目录 " + descDirNames + " 创建成功!");
            return true;
        } else {
            logger.debug("目录 " + descDirNames + " 创建失败!");
            return false;
        }

    }

    /**
     * 写入文件
     *
     * @param fileName 文件路径
     * @param content  内容
     * @param append   是否添加
     */
    public static void writeToFile(String fileName, String content, boolean append) {
        try {
            FileUtils.write(new File(fileName), content, "utf-8", append);
            logger.debug("文件 " + fileName + " 写入成功!");
        } catch (IOException e) {
            logger.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }

    /**
     * 写入文件
     *
     * @param fileName 文件路径
     * @param content  内容
     * @param encoding 编码
     * @param append   是否添加
     */
    public static void writeToFile(String fileName, String content, String encoding, boolean append) {
        try {
            FileUtils.write(new File(fileName), content, encoding, append);
            logger.debug("文件 " + fileName + " 写入成功!");
        } catch (IOException e) {
            logger.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }

    /**
     * 获取待压缩文件在ZIP文件中entry的名字，即相对于跟目录的相对路径名
     *
     * @param dirPath 目录名
     * @param file    entry文件名
     * @return 结果
     */
    private static String getEntryName(String dirPath, File file) {
        String dirPaths = dirPath;
        if (!dirPaths.endsWith(File.separator)) {
            dirPaths = dirPaths + File.separator;
        }
        String filePath = file.getAbsolutePath();
        // 对于目录，必须在entry名字后面加上"/"，表示它将以目录项存储
        if (file.isDirectory()) {
            filePath += "/";
        }
        int index = filePath.indexOf(dirPaths);
        return filePath.substring(index + dirPaths.length());
    }

    /**
     * 根据“文件名的后缀”获取文件内容类型（而非根据File.getContentType()读取的文件类型）
     *
     * @param returnFileName 带验证的文件名
     * @return 返回文件类型
     */
    public static String getContentType(String returnFileName) {
        String contentType = "application/octet-stream";
        if (returnFileName.lastIndexOf(COMMA) < 0) {
            return contentType;
        }
        returnFileName = returnFileName.toLowerCase();
        returnFileName = returnFileName.substring(returnFileName.lastIndexOf(".") + 1);
        switch (returnFileName) {
            case "html":
            case "htm":
            case "shtml":
                contentType = "text/html";
                break;
            case "apk":
                contentType = "application/vnd.android.package-archive";
                break;
            case "sis":
                contentType = "application/vnd.symbian.install";
                break;
            case "sisx":
                contentType = "application/vnd.symbian.install";
                break;
            case "exe":
                contentType = "application/x-msdownload";
                break;
            case "msi":
                contentType = "application/x-msdownload";
                break;
            case "css":
                contentType = "text/css";
                break;
            case "xml":
                contentType = "text/xml";
                break;
            case "gif":
                contentType = "image/gif";
                break;
            case "jpeg":
            case "jpg":
                contentType = "image/jpeg";
                break;
            case "js":
                contentType = "application/x-javascript";
                break;
            case "atom":
                contentType = "application/atom+xml";
                break;
            case "rss":
                contentType = "application/rss+xml";
                break;
            case "mml":
                contentType = "text/mathml";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            case "jad":
                contentType = "text/vnd.sun.j2me.app-descriptor";
                break;
            case "wml":
                contentType = "text/vnd.wap.wml";
                break;
            case "htc":
                contentType = "text/x-component";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "tif":
            case "tiff":
                contentType = "image/tiff";
                break;
            case "wbmp":
                contentType = "image/vnd.wap.wbmp";
                break;
            case "ico":
                contentType = "image/x-icon";
                break;
            case "jng":
                contentType = "image/x-jng";
                break;
            case "bmp":
                contentType = "image/x-ms-bmp";
                break;
            case "svg":
                contentType = "image/svg+xml";
                break;
            case "jar":
            case "var":
            case "ear":
                contentType = "application/java-archive";
                break;
            case "doc":
                contentType = "application/msword";
                break;
            case "pdf":
                contentType = "application/pdf";
                break;
            case "rtf":
                contentType = "application/rtf";
                break;
            case "xls":
                contentType = "application/vnd.ms-excel";
                break;
            case "ppt":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case "7z":
                contentType = "application/x-7z-compressed";
                break;
            case "rar":
                contentType = "application/x-rar-compressed";
                break;
            case "swf":
                contentType = "application/x-shockwave-flash";
                break;
            case "rpm":
                contentType = "application/x-redhat-package-manager";
                break;
            case "der":
            case "pem":
            case "crt":
                contentType = "application/x-x509-ca-cert";
                break;
            case "xhtml":
                contentType = "application/xhtml+xml";
                break;
            case "zip":
                contentType = "application/zip";
                break;
            case "mid":
            case "midi":
            case "kar":
                contentType = "audio/midi";
                break;
            case "mp3":
                contentType = "audio/mpeg";
                break;
            case "ogg":
                contentType = "audio/ogg";
                break;
            case "m4a":
                contentType = "audio/x-m4a";
                break;
            case "ra":
                contentType = "audio/x-realaudio";
                break;
            case "3gpp":
            case "3gp":
                contentType = "video/3gpp";
                break;
            case "mp4":
                contentType = "video/mp4";
                break;
            case "mpeg":
            case "mpg":
                contentType = "video/mpeg";
                break;
            case "mov":
                contentType = "video/quicktime";
                break;
            case "flv":
                contentType = "video/x-flv";
                break;
            case "m4v":
                contentType = "video/x-m4v";
                break;
            case "mng":
                contentType = "video/x-mng";
                break;
            case "asx":
            case "asf":
                contentType = "video/x-ms-asf";
                break;
            case "wmv":
                contentType = "video/x-ms-wmv";
                break;
            case "avi":
                contentType = "video/x-msvideo";
                break;
            default:
        }
        return contentType;
    }

    /**
     * 向浏览器发送文件下载，支持断点续传
     *
     * @param file     要下载的文件
     * @param request  请求对象
     * @param response 响应对象
     * @return 返回错误信息，无错误信息返回null
     */
    public static String downFile(File file, HttpServletRequest request, HttpServletResponse response) {
        return downFile(file, request, response, null);
    }

    /**
     * 向浏览器发送文件下载，支持断点续传
     *
     * @param file     要下载的文件
     * @param request  请求对象
     * @param response 响应对象
     * @param fileName 指定下载的文件名
     * @return 返回错误信息，无错误信息返回null
     */
    public static String downFile(File file, HttpServletRequest request, HttpServletResponse response, String fileName) {
        String error = null;
        if (file != null && file.exists()) {
            if (file.isFile()) {
                if (file.length() <= 0) {
                    error = "该文件是一个空文件。";
                }
                if (!file.canRead()) {
                    error = "该文件没有读取权限。";
                }
            } else {
                error = "该文件是一个文件夹。";
            }
        } else {
            error = "文件已丢失或不存在！";
        }
        if (error != null) {
            logger.debug("---------------" + file + " " + error);
            return error;
        }

        // 记录文件大小
        long fileLength = file.length();
        // 记录已下载文件大小
        long pastLength = 0;
        // 0：从头开始的全文下载；1：从某字节开始的下载（bytes=27000-）；2：从某字节开始到某字节结束的下载（bytes=27000-39000）
        int rangeSwitch = 0;
        // 记录客户端需要下载的字节段的最后一个字节偏移量（比如bytes=27000-39000，则这个值是为39000）
        long toLength;
        // 客户端请求的字节总量
        long contentLength;
        // 记录客户端传来的形如“bytes=27000-”或者“bytes=27000-39000”的内容
        String rangeBytes = "";
        // 负责读取数据
        RandomAccessFile raf = null;
        // 写出数据
        OutputStream os;
        // 缓冲
        OutputStream out = null;
        // 暂存容器
        byte b[] = new byte[1024];

        if (request.getHeader("Range") != null) {
            // 客户端请求的下载的文件块的开始字节
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            logger.debug("request.getHeader(\"Range\") = " + request.getHeader("Range"));
            rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");
            // bytes=969998336-
            if (rangeBytes.indexOf('-') == rangeBytes.length() - 1) {
                rangeSwitch = 1;
                rangeBytes = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                pastLength = Long.parseLong(rangeBytes.trim());
                contentLength = fileLength - pastLength;
                // 客户端请求的是 969998336  之后的字节
            } else {
                // bytes=1275856879-1275877358
                rangeSwitch = 2;
                String temp0 = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                String temp2 = rangeBytes.substring(rangeBytes.indexOf('-') + 1, rangeBytes.length());
                // bytes=1275856879-1275877358，从第 1275856879 个字节开始下载
                pastLength = Long.parseLong(temp0.trim());
                // bytes=1275856879-1275877358，到第 1275877358 个字节结束
                toLength = Long.parseLong(temp2);
                // 客户端请求的是 1275856879-1275877358 之间的字节
                contentLength = toLength - pastLength;
            }
        } else {
            // 从开始进行下载
            // 客户端要求全文下载
            contentLength = fileLength;
        }

        // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。 响应的格式是:
        // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
        // ServletActionContext.getResponse().setHeader("Content- Length", new Long(file.length() - p).toString());
        // 告诉客户端允许断点续传多线程连接下载,响应的格式是:Accept-Ranges: bytes
        response.reset();
        if (pastLength != 0) {
            // 如果是第一次下,还没有断点续传,状态是默认的 200,无需显式设置;响应的格式是:HTTP/1.1 200 OK
            response.setHeader("Accept-Ranges", "bytes");
            // 不是从最开始下载, 响应的格式是: Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
            logger.debug("---------------不是从开始进行下载！服务器即将开始断点续传...");
            switch (rangeSwitch) {
                case 1: {
                    // 针对 bytes=27000- 的请求
                    String contentRange = "bytes " + Long.toString(fileLength) + "-" +
                            Long.toString(fileLength - 1) + "/" + Long.toString(fileLength);
                    response.setHeader("Content-Range", contentRange);
                    break;
                }
                case 2: {
                    // 针对 bytes=27000-39000 的请求
                    String contentRange = rangeBytes + "/" + Long.toString(fileLength);
                    response.setHeader("Content-Range", contentRange);
                    break;
                }
                default: {
                    break;
                }
            }
        } else {
            // 是从开始下载
            logger.debug("---------------是从开始进行下载！");
        }

        try {
            String f = StringUtils.isBlank(fileName) ? file.getName() : fileName;
            boolean isMSIE = HttpUtils.isMSBrowser(request);
            if (isMSIE) {
                //IE浏览器的乱码问题解决
                f = URLEncoder.encode(fileName, "UTF-8");
            } else {
                //万能乱码问题解决
                f = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.addHeader("Content-Disposition", "attachment; filename=" + f);
            // set the MIME type.
            response.setContentType(getContentType(file.getName()));
            response.addHeader("Content-Length", String.valueOf(contentLength));
            os = response.getOutputStream();
            out = new BufferedOutputStream(os);
            raf = new RandomAccessFile(file, "r");
            try {
                switch (rangeSwitch) {
                    // 普通下载，或者从头开始的下载 同1
                    case 0: {
                    }
                    // 针对 bytes=27000- 的请求
                    case 1: {
                        // 形如 bytes=969998336- 的客户端请求，跳过 969998336 个字节
                        raf.seek(pastLength);
                        int n = 0;
                        while ((n = raf.read(b, 0, 1024)) != -1) {
                            out.write(b, 0, n);
                        }
                        break;
                    }
                    // 针对 bytes=27000-39000 的请求
                    case 2: {
                        // 形如 bytes=1275856879-1275877358 的客户端请求，找到第 1275856879 个字节
                        raf.seek(pastLength);
                        int n = 0;
                        // 记录已读字节数
                        long readLength = 0;
                        // 大部分字节在这里读取
                        while (readLength <= contentLength - 1024) {
                            n = raf.read(b, 0, 1024);
                            readLength += 1024;
                            out.write(b, 0, n);
                        }
                        // 余下的不足 1024 个字节在这里读取
                        if (readLength <= contentLength) {
                            n = raf.read(b, 0, (int) (contentLength - readLength));
                            out.write(b, 0, n);
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
                out.flush();
                logger.debug("---------------下载完成！");
            } catch (IOException ie) {
                /**
                 * 在写数据的时候， 对于 ClientAbortException 之类的异常，
                 * 是因为客户端取消了下载，而服务器端继续向浏览器写入数据时， 抛出这个异常，这个是正常的。
                 * 尤其是对于迅雷这种吸血的客户端软件， 明明已经有一个线程在读取 bytes=1275856879-1275877358，
                 * 如果短时间内没有读取完毕，迅雷会再启第二个、第三个。。。线程来读取相同的字节段， 直到有一个线程读取完毕，迅雷会 KILL
                 * 掉其他正在下载同一字节段的线程， 强行中止字节读出，造成服务器抛 ClientAbortException。
                 * 所以，我们忽略这种异常
                 */
                logger.debug("提醒：向客户端传输时出现IO异常，但此异常是允许的，有可能客户端取消了下载，导致此异常，不用关心！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    private static class HttpUtils {
        private static String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};

        private static boolean isMSBrowser(HttpServletRequest request) {
            String userAgent = request.getHeader("User-Agent");
            for (String signal : IEBrowserSignals) {
                if (userAgent.contains(signal)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 修正路径，将 \\ 或 / 等替换为 File.separator
     *
     * @param path 待修正的路径
     * @return 修正后的路径
     */
    public static String path(String path) {
        String p = StringUtils.replace(path, "\\", SPRIT);
        p = StringUtils.join(StringUtils.split(p, SPRIT), SPRIT);
        if (!StringUtils.startsWithAny(p, SPRIT) && StringUtils.startsWithAny(path, "\\", SPRIT)) {
            p += "/";
        }
        if (!StringUtils.endsWithAny(p, SPRIT) && StringUtils.endsWithAny(path, "\\", SPRIT)) {
            p = p + "/";
        }
        if (path != null && path.startsWith(SPRIT)) {
            // linux下路径
            p = "/" + p;
        }
        return p;
    }

    /**
     * 获目录下的文件列表
     *
     * @param dir        搜索目录
     * @param searchDirs 是否是搜索目录
     * @return 文件列表
     */
    public static List<String> findChildrenList(File dir, boolean searchDirs) {
        List<String> files = new ArrayList<>();
        Optional.ofNullable(dir.list()).ifPresent(item -> {
            for (String subFiles : item) {
                File file = new File(dir + SPRIT + subFiles);
                boolean flag = ((searchDirs) && (file.isDirectory())) || ((!searchDirs) && (!file.isDirectory()));
                if (flag) {
                    files.add(file.getName());
                }
            }
        });
        return files;
    }

    /**
     * 获取文件扩展名(返回小写)
     *
     * @param fileName 文件名
     * @return 例如：test.jpg  返回：  jpg
     */
    public static String getFileExtension(String fileName) {
        if ((fileName == null) || (fileName.lastIndexOf(COMMA) == -1) || (fileName.lastIndexOf(COMMA) == fileName.length() - 1)) {
            return null;
        }
        return StringUtils.lowerCase(fileName.substring(fileName.lastIndexOf(COMMA) + 1));
    }

    /**
     * 获取文件名，不包含扩展名
     *
     * @param fileName 文件名
     * @return 例如：d:\files\test.jpg  返回：d:\files\test
     */
    public static String getFileNameWithoutExtension(String fileName) {
        if ((fileName == null) || (fileName.lastIndexOf(COMMA) == -1)) {
            return null;
        }
        return fileName.substring(0, fileName.lastIndexOf(COMMA));
    }
}
