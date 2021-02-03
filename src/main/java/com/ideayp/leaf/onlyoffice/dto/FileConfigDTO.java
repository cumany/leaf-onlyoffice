package com.ideayp.leaf.onlyoffice.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * only office 配置信息
 * 以下参数必须填写
 * 1、docServiceApiUrl
 * 2、fileType
 * 3、key
 * 4、title
 * 5、url
 * 6、callbackUrl
 *
 * https://api.onlyoffice.com/editors/config/
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/10 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Data
public class FileConfigDTO implements Serializable {
    /**
     * 类型 embedded/desktop
     * 默认为desktop
     */
    private String type = "desktop";

    /**
     * 文档类型 text/spreadsheet/presentation
     * open a text document (.doc, .docm, .docx, .dot, .dotm, .dotx, .epub, .fodt, .htm, .html, .mht, .odt, .ott, .pdf, .rtf, .txt, .djvu, .xps)
     * open a spreadsheet (.csv, .fods, .ods, .ots, .xls, .xlsm, .xlsx, .xlt, .xltm, .xltx)
     * open a presentation (.fodp, .odp, .otp, .pot, .potm, .potx, .pps, .ppsm, .ppsx, .ppt, .pptm, .pptx)
     *
     */
    private DocumentType documentType;

    /**
     * 自定义签名
     */
    private String token;

    /**
     * 文档配置信息
     */
    @JSONField(name = "document")
    private FileDocument fileDocument;

    /**
     * 编辑配置
     */
    @JSONField(name = "editorConfig")
    private EditorConfig editorConfig;

    /**
     * 访问api 路径
     */
    private String docServiceApiUrl;

}
