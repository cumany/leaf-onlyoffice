package com.ideayp.leaf.onlyoffice.dto.document;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * file 权限
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/11 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class DocumentPermission implements Serializable {

    /**
     * 定义文档是否可以被注释。
     * 在注释权限设置为“true”的情况下，文档侧栏将包含“注释”菜单选项；
     * 如果该模式参数设置为“编辑”，则文档注释仅可用于文档编辑器。
     * 默认值与编辑参数的值一致。
     * 默认为true
     */
    private Boolean comment;

    /**
     * 是否允许下载 默认为true
     */
    private Boolean download;

    /**
     * 是否允许编辑 默认true
     */
    private Boolean edit;

    /**
     * 是否允许打印 默认true
     */
    private Boolean print;

    /**
     * 是否可以填写表单 默认true
     */
    private Boolean fillForms;


    /**
     * 定义文档是否可以被审核。
     * 如果审阅权限设置为“true”，则文档状态栏将包含审阅菜单选项；
     * 如果模式参数设置为编辑，则文档审阅将仅对文档编辑器可用。
     * 默认值与编辑参数的值一致。
     * 默认：true
     */
    private Boolean review;

}
