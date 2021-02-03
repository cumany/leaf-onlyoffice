package com.ideayp.leaf.onlyoffice.dto.edit;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description:         </p>
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/11 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Data
public class FileUser implements Serializable {
    /**
     * 名称后面前面
     */
    @JSONField(name = "firstname")
    private String firstName;

    /**
     * 用户唯一标识
     */
    private String id;
    /**
     * 名称后面部分
     */
    @JSONField(name = "lastname")
    private String lastName;
    /**
     * 用户 全称
     */
    private String name;
}
