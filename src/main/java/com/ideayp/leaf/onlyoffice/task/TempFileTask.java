package com.ideayp.leaf.onlyoffice.task;

import com.ideayp.leaf.common.util.FileUtil;
import com.ideayp.leaf.common.util.RequestUtils;
import com.ideayp.leaf.onlyoffice.properties.OnlyProperties;
import com.ideayp.leaf.onlyoffice.util.OnlyOfficeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 定时任务
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/13 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Component
public class TempFileTask {

    private final OnlyProperties onlyProperties;

    @Autowired
    public TempFileTask(OnlyProperties onlyProperties) {
        this.onlyProperties = onlyProperties;
    }

    /**
     * 每天凌晨1点执行0 0 1 * * ?
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeTempFile() {
        String callBackUrl = onlyProperties.getCallBackUrl();
        if (!callBackUrl.startsWith("http")) {
            String serverPath = RequestUtils.getServerPath();
            // 临时存储的路径
            String storagePath = onlyProperties.getTempStorage();
            String directory = serverPath + storagePath + File.separator;
            FileUtil.deleteDirectory(directory);
        }
    }
}
