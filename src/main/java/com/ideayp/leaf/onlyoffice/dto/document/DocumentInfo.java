package com.ideayp.leaf.onlyoffice.dto.document;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * file文档部分
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/11 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Data
@Builder(toBuilder = true)
public class DocumentInfo implements Serializable {

    /**
     * 作者
     */
    private String author;

    /**
     * 创建时间（格式化后数据）
     */
    private String created;

    /**
     * 存储文件夹可以为空
     */
    private String folder;

    /**
     * 分享
     * Defines the settings which will allow to share the document with other users:
     *   permissions - the access rights for the user with the name above. Can be Full Access, Read Only or Deny Access
     *     type: string
     *     example: "Full Access"
     *   user - the name of the user the document will be shared with
     *     type: string
     *     example: "John Smith".
     */
    private List<Map<String,String>> sharingSettings;
}
