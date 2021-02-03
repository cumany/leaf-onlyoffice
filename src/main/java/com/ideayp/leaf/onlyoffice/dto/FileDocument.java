package com.ideayp.leaf.onlyoffice.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.ideayp.leaf.onlyoffice.dto.document.DocumentInfo;
import com.ideayp.leaf.onlyoffice.dto.document.DocumentPermission;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * file文档部分
 * https://api.onlyoffice.com/editors/config/document
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/11 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Data
@Builder(toBuilder = true)
public class FileDocument implements Serializable {
    /**
     * 文件类型 如 docx
     * 只需要文件的扩展名
     */
    private String fileType;

    /**
     * 文件名称
     */
    private String title;

    /**
     * 文件访问的url
     */
    private String url;

    /**
     * 定义用于服务的文档识别的唯一文档标识符。在发送已知密钥的情况下，文档将从高速缓存中取出。
     * 每次编辑和保存文档时，必须重新生成密钥。
     * 文档url可以用作键，但是没有特殊字符，长度限制为20个符号。
     */
    private String key;

    /**
     * 文件作者信息
     */
    @JSONField(name = "info")
    private DocumentInfo info;

    /**
     * 权限
     */
    @JSONField(name = "permissions")
    private DocumentPermission permission;

}
