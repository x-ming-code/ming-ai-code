package com.ming.mingaicode.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.ming.mingaicode.exceptioon.ErrorCode;
import com.ming.mingaicode.exceptioon.ThrowUtils;
import com.ming.mingaicode.manager.CosManager;
import com.ming.mingaicode.service.ScreenshotService;
import com.ming.mingaicode.utils.WebScreenshotUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author ming
 * @description 生成截图
 * @date 2025/9/14 21:13
 */
@Service
@Slf4j
public class ScreenshotServiceImpl implements ScreenshotService {

    @Resource
    private CosManager cosManager;

    /**
     * 生成本地截图并上传到cos中
     *
     * @param webUrl 需要截图的网页URL
     * @return 截图URL
     */
    @Override
    public String generateAndUploadScreenshot(String webUrl) {
        ThrowUtils.throwIf(webUrl == null, ErrorCode.PARAMS_ERROR, "网页URL不能为空");
        log.info("开始生成截图url {}", webUrl);
        // 1. 生成本地截图
        String localScreenshotPath = WebScreenshotUtils.saveWebPageScreenshot(webUrl);
        ThrowUtils.throwIf(localScreenshotPath == null, ErrorCode.SYSTEM_ERROR, "本地截图生成失败");
        try {
            //2. 上传COS对象
            String cosUrl = uploadScreenshotToCos(localScreenshotPath);
            ThrowUtils.throwIf(cosUrl == null, ErrorCode.SYSTEM_ERROR, "上传截图到COS失败");
            log.info("截图上传成功: {}", cosUrl);
            return cosUrl;
        } finally {
            //3.清理本地图片
            cleanupLocalFile(localScreenshotPath);
        }
    }

    /**
     * 上传截图到对象存储
     *
     * @param localScreenshotPath 本地截图路径
     * @return 对象存储访问URL，失败返回null
     */
    private String uploadScreenshotToCos(String localScreenshotPath) {
        if (StrUtil.isBlank(localScreenshotPath)) {
            return null;
        }
        File screenshotFile = new File(localScreenshotPath);
        if (!screenshotFile.exists()) {
            log.error("截图文件不存在: {}", localScreenshotPath);
            return null;
        }
        //生成cos对象键
        String fileName = UUID.randomUUID().toString().substring(0, 8) + "_compressed.jpg";
        //最终生成的是：/screenshots/2025/07/31/filename.jpg
        String cosKey = generateScreenshotKey(fileName);
        return cosManager.uploadFile(cosKey, screenshotFile);
    }

    /**
     * 生成截图的对象存储键
     * 格式：/screenshots/2025/07/31/filename.jpg
     */
    private String generateScreenshotKey(String fileName) {
        //格式化分类，以日期格式化分类
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return String.format("/screenshots/%s/%s", datePath, fileName);
    }

    /**
     * 删除本地截图文件
     *
     * @param localScreenshotPath 本地截图路径
     */

    private void cleanupLocalFile(String localScreenshotPath) {
        File screenshotFile = new File(localScreenshotPath);
        if (screenshotFile.exists()) {
            File projectDir = screenshotFile.getParentFile();
            FileUtil.del(projectDir);
            log.info("本地截图文件已清理: {}", localScreenshotPath);
        }
    }

}
