package com.kstarrain.exception;

/**
 * 业务异常 编码/描述接口
 */
public interface ErrorCode {

    /**
     * 业务异常编码
     * @return
     */
    String getCode();

    /**
     * 业务异常描述
     * @return
     */
    String getDesc();
}
