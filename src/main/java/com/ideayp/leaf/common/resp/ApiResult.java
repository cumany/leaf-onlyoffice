package com.ideayp.leaf.common.resp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Description: 响应结果   </p>
 * <p>email: ypasdf@163.com </p>
 * <p>Copyright: Copyright (c) 2018 </p>
 * <P>Date: 2018/3/25 </P>
 *
 * @author common
 * @version 1.0
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T> {

    /**
     * 响应代码
     */
    private int code;

    /**
     * 响应结果
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;
}
