package com.ming.mingaicode.core.saver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.ming.mingaicode.constant.AppConstant;
import com.ming.mingaicode.exceptioon.BusinessException;
import com.ming.mingaicode.exceptioon.ErrorCode;
import com.ming.mingaicode.model.enums.CodeGenTypeEnum;

import java.io.File;
import java.nio.charset.StandardCharsets;


/**
 * @author ming
 * @description 抽象代码文件保存器 - 模板方法模式
 * @date 2025/9/6 11:57
 */

public abstract class CodeFileSaverTemplate<T> {
    public static final String FILE_SAVE_ROOT_DIR = AppConstant.CODE_OUTPUT_ROOT_DIR;

    /**
     * 模板方法：保存代码的标准流程
     *
     * @param result 代码结果对象
     * @return 保存的目录
     */
    public final File saveCode(T result,Long appId) {
        //校验参数
        validateInput(result);

        //构建唯一目录
        String baseDirPath = buildUniqueDir(appId);
        //保存文件，由子类实现
        saveFiles(result, baseDirPath);
        //返回文件对象
        return new File(baseDirPath);
    }
    /**
     * 验证输入参数（可由子类覆盖）
     *
     * @param result 代码结果对象
     */
    protected void validateInput(T result) {
        if (result == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "代码结果对象不能为空");
        }
    }

    /**
     * 写入单个文件的工具方法
     *
     * @param dirPath  目录路径
     * @param filename 文件名
     * @param content  文件内容
     */
    protected final void writeToFile(String dirPath, String filename, String content) {
        if (StrUtil.isNotBlank(content)) {
            String filePath = dirPath + File.separator + filename;
            FileUtil.writeString(content, filePath, StandardCharsets.UTF_8);
        }
    }
    /**
     * 构建唯一目录路径 tmp/code_output/业务类型_雪花id 拼接成完整的路径
     */
    protected final String buildUniqueDir(Long appId) {
        if (appId == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "appId不能为空");
        }
        String codeType = getCodeType().getValue();
        String uniqeDirName = StrUtil.format("{}_{}", codeType, appId);
        String dirPath = FILE_SAVE_ROOT_DIR + File.separator + uniqeDirName;
        return dirPath;
    }
    /**
     * 获取代码类型（由子类实现）
     *
     * @return 代码生成类型
     */
    protected abstract CodeGenTypeEnum getCodeType();

    /**
     * 保存文件的具体实现（由子类实现）
     *
     * @param result      代码结果对象
     * @param baseDirPath 基础目录路径
     */
    protected abstract void saveFiles(T result, String baseDirPath);

}
