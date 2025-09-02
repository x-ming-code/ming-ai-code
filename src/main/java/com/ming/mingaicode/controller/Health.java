package com.ming.mingaicode.controller;

import com.ming.mingaicode.common.BaseResponse;
import com.ming.mingaicode.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ming
 * @description
 * @date 2025/9/2 9:48
 */

@RestController
@RequestMapping("/health")
public class Health {
    @GetMapping("/")
    public BaseResponse<String> health() {
        return ResultUtils.success("ok");
    }
}
