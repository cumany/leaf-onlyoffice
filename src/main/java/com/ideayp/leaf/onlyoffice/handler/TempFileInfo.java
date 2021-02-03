package com.ideayp.leaf.onlyoffice.handler;

import lombok.Builder;
import lombok.Data;

/**
 * <p>Description:         </p>
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/13 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Data
@Builder(toBuilder = true)
public class TempFileInfo {
    /**
     * 文件可访问的url
     */
    private String url;
    /**
     * 文件标示符 最大长度为10位
     */
    private String key;
    /**
     * 文件名称  源文件名
     */
    private String oldName;
    /**
     * 文件位置  源文件
     */
    private String oldFilePath;
    /**
     * 存储的文件名称（包含扩展名）
     */
    private String storageName;

    /**
     * 本地存储的位置
     */
    private String storagePath;


}
