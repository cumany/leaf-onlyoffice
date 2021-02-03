package com.ideayp.leaf.onlyoffice.enums;

/**
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/13 </P>
 *
 * @author leaf
 * @version 1.0
 */
public enum TempHandlerEnum {
    /**
     * 本地
     */
    local("local"),
    /**
     * 远程
     */
    remote("remote");

    String code;

    TempHandlerEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
