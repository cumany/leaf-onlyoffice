/**
 * @Project RTMS
 * @Title MD5Util.java
 * @Package com.rainbow.util.security
 * @author tuxy
 * @date 2013-6-8 下午1:51:12
 * @Copyright ©2013Chongqing Rainbow Technology Co., Ltd. All Rights Reserved.
 * @version V1.0
 */
package com.ideayp.leaf.common.util.encodes;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * MD5工具类
 * 信息-摘要算法
 *
 * @author yp
 */
public class MD5Util {

    /**
     * 获取字符串md5
     *
     * @param s 需要加密的字符串
     * @return 加密的结果
     */
    public static String MD5(String s) {
        return DigestUtils.md5Hex(s);
    }


    /**
     * 获取一个文件的md5值(可处理大文件)
     *
     * @param file 文件
     * @return md5 value
     */
    public static String MD5(File file) {
        InputStream fileInputStream = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(md5.digest()));
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取输入流的md5
     *
     * @param inputStream 输入流
     * @return md5 值
     */
    public static String MD5(InputStream inputStream) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(md5.digest()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        long beginTime = System.currentTimeMillis();
        File file = new File("C:\\Users\\Leaf\\Desktop\\新建文本文档.txt");
        File file1 = new File("C:\\Users\\Leaf\\Desktop\\新建文本文档 - 副本.txt");
        InputStream inputStream = new FileInputStream(file);

        String md5 = MD5(inputStream);
        String md52 = MD5(file1);
        long endTime = System.currentTimeMillis();
        boolean equals = md52.equals(md5);
        System.err.println(md5.length());
        System.err.println(equals);
//        System.out.println("MD5:" + md5 + "\n 耗时:" + ((endTime - beginTime) / 1000) + "s");
//        System.out.println(md5.equals(MD5Util.MD5("asdasdasda")));
//        System.err.println(MD5Util.MD5("asdasdasda"));
//        System.err.println("结果");
//        System.err.println(md5);
//        System.err.println(md52);
    }

}
