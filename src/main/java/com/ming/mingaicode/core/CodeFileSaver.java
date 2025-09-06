package com.ming.mingaicode.core;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.ming.mingaicode.ai.model.HtmlCodeResult;
import com.ming.mingaicode.ai.model.MultiFileCodeResult;
import com.ming.mingaicode.model.enums.CodeGenTypeEnum;

import java.io.File;
import java.io.IOException;

/**
 * @author ming
 * @description 代码文件保存类
 * @date 2025/9/5 14:42
 */

public class CodeFileSaver {
    //文件保存根目录
    public static final String FILE_SAVE_ROOT_DIR = System.getProperty("user.dir") + "/tmp/code_output";

    /**
     * 保存 HtmlCodeResult
     *
     * @param htmlCodeResult
     */
    public static File saveHtmlCodeResult(HtmlCodeResult htmlCodeResult) {
        // 保存代码文件的逻辑
        String dirPath = buildUniqueDir(CodeGenTypeEnum.HTML.getValue());
        writeToFile(dirPath, "index.html", htmlCodeResult.getHtmlCode());
        return new File(dirPath);
    }

    public static File saveMultiFileCodeResult(MultiFileCodeResult multiFileCodeResult) {
        String dirPath = buildUniqueDir(CodeGenTypeEnum.MULTI_FILE.getValue());
        writeToFile(dirPath, "index.html", multiFileCodeResult.getHtmlCode());
        writeToFile(dirPath, "style.css", multiFileCodeResult.getCssCode());
        writeToFile(dirPath, "script.js", multiFileCodeResult.getJsCode());
        return new File(dirPath);
    }

    /**
     * 构建唯一目录路径 tmp/code_output/业务类型_雪花id 拼接成完整的路径
     */
    private static String buildUniqueDir(String bizType) {
        String uniqeDirName = StrUtil.format("{}_{}", bizType, IdUtil.getSnowflakeNextIdStr());
        String dirPath = FILE_SAVE_ROOT_DIR + File.separator + uniqeDirName;
        return dirPath;
    }

    //写入单个文件
    private static void writeToFile(String dirPath, String fileName, String content) {
        String filePath = dirPath + File.separator + fileName;
        FileUtil.writeUtf8String(content, filePath);
    }
}
