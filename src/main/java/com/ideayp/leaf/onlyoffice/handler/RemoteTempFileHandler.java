package com.ideayp.leaf.onlyoffice.handler;

import com.ideayp.leaf.common.util.FileUtil;
import com.ideayp.leaf.onlyoffice.enums.TempHandlerEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * 远程文件处理方式
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/13 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Component
public class RemoteTempFileHandler implements TempFileHandler {
    @Override
    public TempHandlerEnum getHandlerName() {
        return TempHandlerEnum.remote;
    }

    @Override
    public TempFileInfo handlerTempFile(String url) {
        String key = RandomStringUtils.randomAlphabetic(10);
        String namePart = FileUtil.getDocName(url);
        return TempFileInfo.builder()
                .url(url)
                .oldName(namePart)
                .oldFilePath(url)
                .key(key)
                .build();
    }

    @Override
    public void saveFile(byte[] file, String key) {

    }

    @Override
    public void removeTempFile(String key) {

    }
}
