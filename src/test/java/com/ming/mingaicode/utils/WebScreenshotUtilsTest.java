package com.ming.mingaicode.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebScreenshotUtilsTest {

    @Test
    void saveWebPageScreenshot() {
        String webUrl = "https://codercot.cn";
        String webPageScreenshot = WebScreenshotUtils.saveWebPageScreenshot(webUrl);
        Assertions.assertNotNull(webPageScreenshot);
    }
}