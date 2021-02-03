package com.ideayp.leaf.onlyoffice.handler;

import com.ideayp.leaf.onlyoffice.enums.TempHandlerEnum;

/**
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/13 </P>
 *
 * @author leaf
 * @version 1.0
 */
public interface TempFileHandler {
    /**
     * 处理名称
     *
     * @return 名称
     */
    TempHandlerEnum getHandlerName();

    /**
     * 处理临时文件
     *
     * @param url url
     * @return 结果
     */
    TempFileInfo handlerTempFile(String url);

    /**
     * 文件
     *
     * @param file 结果
     * @param key  文件
     */
    void saveFile(byte[] file, String key);

    /**
     * 移除临时文件
     *
     * @param key key
     */
    void removeTempFile(String key);
}
