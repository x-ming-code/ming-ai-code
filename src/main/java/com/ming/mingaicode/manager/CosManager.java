package com.ming.mingaicode.manager;

import com.ming.mingaicode.config.CosClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author ming
 * @description COS对象存储管理器
 * @date 2025/9/14 20:58
 */
@Component
@Slf4j
public class CosManager {
    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private COSClient cosClient;

    /**
     * 上传对象
     *
     * @param key  唯一键
     * @param file 文件
     * @return 上传结果
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key, file);
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 上传文件到 COS 并返回访问 URL
     *
     * @param key  COS对象键（完整路径）
     * @param file 要上传的文件
     * @return 文件的访问URL，失败返回null
     */
    public String uploadFile(String key, File file) {
        PutObjectResult putObjectResult = putObject(key, file);
        if (putObjectResult != null) {
            // 构建访问URL
            /**
             *假设 cosClientConfig.getHost() 返回：https://my-bucket.cos.ap-beijing.myqcloud.com/
             * key 是：images/photo.jpg
             * 生成的 URL 就是 https://my-bucket.cos.ap-beijing.myqcloud.com/images/photo.jpg
             */
            String url = String.format("%s%s", cosClientConfig.getHost(), key);
            log.info("文件上传COS成功: {} -> {}", file.getName(), url);
            return url;
        } else {
            log.error("文件上传COS失败，返回结果为空");
            return null;
        }
    }
}
