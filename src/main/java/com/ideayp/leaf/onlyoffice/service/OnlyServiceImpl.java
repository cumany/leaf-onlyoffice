package com.ideayp.leaf.onlyoffice.service;

import com.alibaba.fastjson.JSONObject;
import com.ideayp.leaf.common.util.FileUtil;
import com.ideayp.leaf.common.util.RequestUtils;
import com.ideayp.leaf.onlyoffice.constants.Constant;
import com.ideayp.leaf.onlyoffice.dto.EditorConfig;
import com.ideayp.leaf.onlyoffice.dto.FileConfigDTO;
import com.ideayp.leaf.onlyoffice.dto.FileDocument;
import com.ideayp.leaf.onlyoffice.handler.TempFileContext;
import com.ideayp.leaf.onlyoffice.handler.TempFileHandler;
import com.ideayp.leaf.onlyoffice.handler.TempFileInfo;
import com.ideayp.leaf.onlyoffice.properties.OnlyProperties;
import com.ideayp.leaf.onlyoffice.util.OnlyOfficeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * only office 实现类
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/13 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Service
@Slf4j
public class OnlyServiceImpl implements OnlyService {


    private final OnlyProperties onlyProperties;

    private final TempFileContext tempFileContext;

    @Autowired
    public OnlyServiceImpl(OnlyProperties onlyProperties, TempFileContext tempFileContext) {
        this.onlyProperties = onlyProperties;
        this.tempFileContext = tempFileContext;
    }

    @Override
    public FileConfigDTO openEditConfig(String url) {
        TempFileHandler tempFileHandler = tempFileContext.getHandlerByUrl(url);
        TempFileInfo tempFileInfo = tempFileHandler.handlerTempFile(url);
        FileConfigDTO fileConfigDTO = this.buildInitConfig(tempFileInfo.getUrl(), tempFileInfo.getKey(), tempFileInfo.getOldName());
        // TODO: 添加更多详细的自定义信息
        return fileConfigDTO;
    }

    @Override
    public void handlerStatus(JSONObject jsonObject) {
        /**
         * 0 - 找不到具有密钥标识符的文档
         * 1 - 正在编辑文档
         * 2 - 文档已准备好保存
         * 3 - 发生文档保存错误
         * 4 - 不作任何更改就关闭文档
         * 6 - 正在编辑文档，但保存当前文档状态
         * 7 - 强制保存文档时发生错误
         */
        int status = jsonObject.getIntValue("status");
        log.info("status[{}]:{}", status, jsonObject);
        String key = jsonObject.getString("key");

        TempFileHandler tempFileHandler = tempFileContext.getHandlerByKey(key);
        if (Objects.nonNull(tempFileHandler)) {
            if (2 == status || 3 == status || 6 == status || 7 == status) {
                String url = jsonObject.getString("url");
                // 下载文件
                byte[] fileByte = OnlyOfficeUtil.getFileByte(url);
                // 保存文件
                tempFileHandler.saveFile(fileByte, key);
            } else if (4 == status || 0 == status) {
                // 移除临时文件
                tempFileHandler.removeTempFile(key);
            }
        }

    }

    /**
     * 初始化 only office 最基础的信息必要数据
     *
     * @param fileUrl  可访问的url路径
     * @param key      唯一标示符 20个字符以内
     * @param fileName 文件名称
     * @return 配置信息
     */
    private FileConfigDTO buildInitConfig(String fileUrl, String key, String fileName) {
        FileConfigDTO fileConfigDTO = new FileConfigDTO();
        // 1、文档类型
        fileConfigDTO.setDocumentType(OnlyOfficeUtil.getDocumentType(fileName));
        // 2、api位置
        fileConfigDTO.setDocServiceApiUrl(onlyProperties.getDocService() + Constant.DOC_API_URL);

        // ========文档类型=============
        String typePart = FileUtil.getTypePart(fileName);
        FileDocument fileDocument = FileDocument.builder()
                // 文件名
                .title(fileName)
                // 扩展名
                .fileType(typePart)
                // 可访问的url
                .url(fileUrl)
                // 唯一有标示符
                .key(key)
                .build();
        fileConfigDTO.setDocument(fileDocument);

        // ==========编辑配置===============
        String callBackUrl = onlyProperties.getCallBackUrl();
        if (!callBackUrl.startsWith("http")) {
            callBackUrl = RequestUtils.getServerUrl() + callBackUrl;
        }
        EditorConfig editorConfig = new EditorConfig(callBackUrl);
        fileConfigDTO.setEditorConfig(editorConfig);
        return fileConfigDTO;
    }
}
