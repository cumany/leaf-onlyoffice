package com.ideayp.leaf.onlyoffice.dto.edit;

import lombok.Data;

import java.io.Serializable;

/**
 *  当type =embedded 生效
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/11 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Data
public class FileEmbedded implements Serializable {

    /**
     * 文件url
     * 	"https://example.com/embedded?doc=exampledocument1.docx"
     */
    private String embedUrl="https://example.com/embedded?doc=exampledocument1.docx";

    /**
     * "https://example.com/embedded?doc=exampledocument1.docx#fullscreen"
     */
    private String fullscreenUrl="https://example.com/embedded?doc=exampledocument1.docx";

    /**
     * 保存的url
     * "https://example.com/download?doc=exampledocument1.docx"
     */
    private String saveUrl;

    /**
     * "https://example.com/view?doc=exampledocument1.docx"
     */
    private String shareUrl;

    /**
     * 定义嵌入式浏览器工具栏的位置，可以是顶部或底部
     * 默认top
     * bottom/top
     */
    private String toolbarDocked;
}
