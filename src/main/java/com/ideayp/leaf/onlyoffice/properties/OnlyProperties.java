package com.ideayp.leaf.onlyoffice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置信息
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/11 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "only-office")
public class OnlyProperties {

    /**
     * 回调地址 不包含http表示本机地址
     */
    private String callBackUrl = "/onlyOffice/save";

    /**
     * only office服务路径
     * 必须存在
     */
    private String docService;

    /**
     * 临时存储位置
     */
    private String tempStorage = "reports";

    private String key;
}
