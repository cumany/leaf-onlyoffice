package com.ideayp.leaf.onlyoffice.dto.edit;

import lombok.Data;

import java.io.Serializable;

/**
 * 最近打开
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/11 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Data
public class FileRecent implements Serializable {

    /**
     * 文件夹
     */
    private String folder;

    /**
     * 名称
     */
    private String title;

    /**
     * url 绝对路径
     */
    private String url;

}
