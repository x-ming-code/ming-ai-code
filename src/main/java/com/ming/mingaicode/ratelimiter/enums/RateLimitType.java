package com.ming.mingaicode.ratelimiter.enums;

/**
 * @author ming
 * @description 限流类型枚举
 * @date 2025/9/23 10:48
 */

public enum RateLimitType {

    /**
     * 接口级别限流
     */
    API,

    /**
     * 用户级别限流
     */
    USER,

    /**
     * IP级别限流
     */
    IP
}
