package com.ideayp.leaf.onlyoffice.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description:         </p>
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/13 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Component
public class TempFileContext {

    private final Map<String, TempFileHandler> tempMaps = new ConcurrentHashMap<>();


    static Map<String, TempFileInfo> keyUrlInfo = new HashMap<>();

    @Autowired
    public TempFileContext(Map<String, TempFileHandler> tempMap) {
        this.tempMaps.clear();
        tempMap.forEach((k, v) -> this.tempMaps.put(v.getHandlerName().getCode(), v));
    }

    /**
     * 获取对应的执行
     *
     * @param url url
     * @return 结果
     */
    public TempFileHandler getHandlerByUrl(String url) {
        boolean http = url.startsWith("http");
        if (http) {
            return tempMaps.get("remote");
        } else {
            return tempMaps.get("local");
        }
    }

    /**
     * 获取对应的 处理方式
     *
     * @param key key
     * @return 结果
     */
    public TempFileHandler getHandlerByKey(String key) {
        for (TempFileHandler tempFileHandler : tempMaps.values()) {
            TempFileInfo tempFileInfo = keyUrlInfo.get(tempFileHandler.getHandlerName().getCode() + key);
            if (Objects.nonNull(tempFileInfo)) {
                return tempFileHandler;
            }
        }
        return null;
    }

}
