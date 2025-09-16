package com.ming.mingaicode.service;

import org.springframework.stereotype.Service;


public interface ScreenshotService {

    /**
     * 截取网页并上传
     *
     * @param webUrl 需要截图的网页URL
     * @return 生成截图后可访问的图片URL地址
     */
    String generateAndUploadScreenshot(String webUrl);
}
