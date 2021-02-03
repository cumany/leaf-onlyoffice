package com.ideayp.leaf.onlyoffice.handler;

import com.ideayp.leaf.common.exception.LeafException;
import com.ideayp.leaf.common.util.FileUtil;
import com.ideayp.leaf.common.util.FileUtils;
import com.ideayp.leaf.common.util.RequestUtils;
import com.ideayp.leaf.common.util.encodes.MD5Util;
import com.ideayp.leaf.onlyoffice.enums.TempHandlerEnum;
import com.ideayp.leaf.onlyoffice.properties.OnlyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Optional;

/**
 * 本地文件处理方式
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/13 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Component
@Slf4j
public class LocalTempFileHandler implements TempFileHandler {


    private final OnlyProperties onlyProperties;

    @Autowired
    public LocalTempFileHandler(OnlyProperties onlyProperties) {
        this.onlyProperties = onlyProperties;
    }

    @Override
    public TempHandlerEnum getHandlerName() {
        return TempHandlerEnum.local;
    }

    @Override
    public TempFileInfo handlerTempFile(String url) {
        String typePart = FileUtil.getTypePart(url);
        try {
            File file = new File(url);
            String fileName = MD5Util.MD5(file);
            if (null == fileName) {
                throw new LeafException(url + "系统找不到该路径文件");
            }
            // 唯一标示符
            String key = fileName.substring(0, 11);

            Optional<TempFileInfo> tempFileInfoOptional = this.handlerKey(key);
            if (tempFileInfoOptional.isPresent()) {
                return tempFileInfoOptional.get();
            }
            String fileStorageName = fileName + "." + typePart;
            // 临时可访问的url
            String tempUrl = this.createTempServerFile(file, fileStorageName);
            // 本机存储的位置
            String storagePath = this.storagePath(fileStorageName);
            TempFileInfo tempFileInfo = TempFileInfo.builder()
                    // 可访问路径
                    .url(tempUrl)
                    // 文件名
                    .storageName(fileStorageName)
                    // 存储的路径
                    .storagePath(storagePath)
                    // 原来的文件路径
                    .oldFilePath(url)
                    // 原来的文件名
                    .oldName(file.getName())
                    // 唯一标识
                    .key(key)
                    .build();
            TempFileContext.keyUrlInfo.put(getHandlerName().getCode() + key, tempFileInfo);
            return tempFileInfo;
        } catch (IOException e) {
            log.error("生成临时文件失败", e);
            throw new LeafException(e.getMessage());
        }
    }

    @Override
    public void saveFile(byte[] file, String key) {
        Optional<TempFileInfo> fileInfo = this.handlerKey(key);
        if (fileInfo.isPresent()) {
            try {
                FileUtils.saveFile(file, fileInfo.get().getOldFilePath());
                log.debug("保存文件成功" + fileInfo.get().getOldFilePath());
                this.removeTempFile(key);
            } catch (Exception e) {
                log.error("保存文件失败" + key, e);
                throw new NullPointerException();
            }
        } else {
            log.error("没有找到对应的配置信息" + key);
        }
    }

    @Override
    public void removeTempFile(String key) {
        Optional<TempFileInfo> tempFileInfo = this.handlerKey(key);
        if (tempFileInfo.isPresent()) {
            FileUtils.deleteFile(tempFileInfo.get().getStoragePath());
            TempFileContext.keyUrlInfo.remove(getHandlerName().getCode() + key);
        }
    }


    private Optional<TempFileInfo> handlerKey(String key) {
        TempFileInfo tempFileInfo = TempFileContext.keyUrlInfo.get(getHandlerName().getCode() + key);
        return Optional.ofNullable(tempFileInfo);
    }

    /**
     * 创建一个临时 并获取一个可访问的地址
     * 如果文件存储 则不覆盖
     *
     * @param file     源文件
     * @param fileName 文件名（唯一不能重名）
     * @return 临时文件
     */
    private String createTempServerFile(File file, String fileName) throws IOException {
        String saveFilePath = this.storagePath(fileName);
        File saveFile = new File(saveFilePath);
        InputStream stream = FileUtils.openInputStream(file);
        try (FileOutputStream out = new FileOutputStream(saveFile)) {
            int read;
            final byte[] bytes = new byte[1024];
            while ((read = stream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
        }
        return this.getFileUri(fileName);
    }


    /**
     * 获取存储的路径
     * 存储在static 以便于文件直接访问
     *
     * @param fileName 文件名
     * @return 结果
     */
    private String storagePath(String fileName) {
        // 服务器路径
        String serverPath = RequestUtils.getServerPath();
        // 临时存储的路径
        String storagePath = onlyProperties.getTempStorage();
        String directory = serverPath + storagePath + File.separator;
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdir();
        }
        return directory + fileName;
    }

    /**
     * 获取文件可访问的地址
     *
     * @param fileName 结果
     * @return 地址
     */
    private String getFileUri(String fileName) {
        try {
            // 服务器地址
            String serverPath = RequestUtils.getServerUrl();
            // 本地地址
            String storagePath = onlyProperties.getTempStorage();
            return serverPath + "/" + storagePath + "/" + URLEncoder.encode(fileName, java.nio.charset.StandardCharsets.UTF_8.toString()).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
