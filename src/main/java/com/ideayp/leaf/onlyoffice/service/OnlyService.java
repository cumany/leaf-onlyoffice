package com.ideayp.leaf.onlyoffice.service;

import com.alibaba.fastjson.JSONObject;
import com.ideayp.leaf.onlyoffice.dto.FileConfigDTO;

/**
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/13 </P>
 *
 * @author leaf
 * @version 1.0
 */
public interface OnlyService {
    /**
     * 打开only office
     *
     * @param url 文件路径
     * @return 配置信息
     */
    FileConfigDTO openEditConfig(String url);

    /**
     * 处理保存结果
     *
     * @param jsonObject 信息
     */
    void handlerStatus(JSONObject jsonObject);
}
