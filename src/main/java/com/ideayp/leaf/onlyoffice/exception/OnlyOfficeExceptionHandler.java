package com.ideayp.leaf.onlyoffice.exception;

import com.ideayp.leaf.common.resp.ApiResult;
import com.ideayp.leaf.common.resp.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <p>Description:         </p>
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/14 </P>
 *
 * @author leaf
 * @version 1.0
 */
@SuppressWarnings("ALL")
@ControllerAdvice
@ResponseBody
@Slf4j
public class OnlyOfficeExceptionHandler {
    /**
     * 找不到文件
     *
     * @param e
     * @return
     */
    @ExceptionHandler({FileNotFoundException.class, IOException.class})
    public ApiResult handlerFileNotFoundExection(IOException e) {
        return ApiUtil.notFound(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResult handleLeafException(Exception e) {
        if ("Required String parameter 'url' is not present".equals(e.getMessage())) {
            return ApiUtil.fail("url 不能为空");
        }
        log.error(e.getMessage(), e);
        return ApiUtil.fail(e.getMessage());
    }
}
