package com.ming.mingaicode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ming.mingaicode.ai.AiCodeGenTypeRoutingService;
import com.ming.mingaicode.constant.AppConstant;
import com.ming.mingaicode.core.AiCodeGeneratorFacade;
import com.ming.mingaicode.core.builder.VueProjectBuilder;
import com.ming.mingaicode.core.handler.StreamHandlerExecutor;
import com.ming.mingaicode.exceptioon.BusinessException;
import com.ming.mingaicode.exceptioon.ErrorCode;
import com.ming.mingaicode.exceptioon.ThrowUtils;
import com.ming.mingaicode.model.dto.app.AppAddRequest;
import com.ming.mingaicode.model.dto.app.AppQueryRequest;
import com.ming.mingaicode.model.dto.app.AppVO;
import com.ming.mingaicode.model.entity.User;
import com.ming.mingaicode.model.enums.ChatHistoryMessageTypeEnum;
import com.ming.mingaicode.model.enums.CodeGenTypeEnum;
import com.ming.mingaicode.model.vo.UserVO;
import com.ming.mingaicode.service.ChatHistoryService;
import com.ming.mingaicode.service.ScreenshotService;
import com.ming.mingaicode.service.UserService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ming.mingaicode.model.entity.App;
import com.ming.mingaicode.mapper.AppMapper;
import com.ming.mingaicode.service.AppService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务层实现。
 *
 * @author <a href="https://ming-code.work/">ming</a>
 */
@Service
@Slf4j
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    @Resource
    private UserService userService;

    @Resource
    private AiCodeGeneratorFacade aiCodeGeneratorFacade;

    @Resource
    private ChatHistoryService chatHistoryService;

    // 根据类型决定处理不通的代码流
    @Resource
    private StreamHandlerExecutor streamHandlerExecutor;

    @Resource
    private VueProjectBuilder vueProjectBuilder;

    @Resource
    private ScreenshotService screenshotService;

    @Resource
    private AiCodeGenTypeRoutingService aiCodeGenTypeRoutingService;

    /**
     * 应用聊天生成代码（流式 SSE）
     *
     * @param appId   应用 ID
     * @param message 用户消息
     * @return 生成结果流
     */
    @Override
    public Flux<String> chatToGenCode(Long appId, String message, User loginUser) {
        // 1. 参数校验
        if (appId == null || appId <= 0 || loginUser == null) {
            ThrowUtils.throwIf(true, ErrorCode.PARAMS_ERROR);
        }
        // 2. 查询应用信息
        App app = this.getById(appId);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR, "应用不存在");
        // 3. 校验用户权限
        if (!app.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 4. 获取应用的代码生成类型
        String codeGenType = app.getCodeGenType();
        CodeGenTypeEnum enumByValue = CodeGenTypeEnum.getEnumByValue(codeGenType);
        if (enumByValue == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "应用代码生成类型错误");
        }
        //通过校验后，将用户消息添加到对话历史中
        chatHistoryService.addChatHistory(appId, loginUser.getId(), message, ChatHistoryMessageTypeEnum.USER.getValue());
        // 5. 调用 AI 生成代码(流式)
        Flux<String> stringFlux = aiCodeGeneratorFacade.generateAndSaveCodeStream(message, enumByValue, appId);
        return streamHandlerExecutor.doExecute(stringFlux, chatHistoryService, appId, loginUser, enumByValue);


    }

    /**
     * 创建应用
     *
     * @param appAddRequest 应用创建请求
     * @param loginUser     登录用户
     * @return 应用 ID
     */
    @Override
    public Long createApp(AppAddRequest appAddRequest, User loginUser) {
        // 参数校验
        String initPrompt = appAddRequest.getInitPrompt();
        ThrowUtils.throwIf(StrUtil.isBlank(initPrompt), ErrorCode.PARAMS_ERROR, "初始化 prompt 不能为空");
        // 构造入库对象
        App app = new App();
        BeanUtil.copyProperties(appAddRequest, app);
        app.setUserId(loginUser.getId());
        // 应用名称暂时为 initPrompt 前 12 位
        app.setAppName(initPrompt.substring(0, Math.min(initPrompt.length(), 12)));
        // 使用 AI 智能选择代码生成类型
        CodeGenTypeEnum selectedCodeGenType = aiCodeGenTypeRoutingService.routeCodeGenType(initPrompt);
        app.setCodeGenType(selectedCodeGenType.getValue());
        // 插入数据库
        boolean result = this.save(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        log.info("应用创建成功，ID: {}, 类型: {}", app.getId(), selectedCodeGenType.getValue());
        return app.getId();
    }

    /**
     * 应用部署
     *
     * @param appId     应用 ID
     * @param loginUser 登录用户
     * @return 部署成功后可访问的网址
     */
    //应用部署
    public String deployApp(Long appId, User loginUser) {
        //1.校验参数
        if (appId == null || appId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        //2.查询应用是否存在
        App app = this.getById(appId);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR, "应用不存在");
        //3.校验用户权限，是否是应用创建者
        if (!app.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无访部署该应用");
        }
        //4.检查deployKey是否创建 不存在就创建一个6位数的
        String deployKey = app.getDeployKey();
        if (StrUtil.isBlank(deployKey)) {
            deployKey = RandomUtil.randomString(6);
        }
        //5.获取代码生成类型，获取代码生成的路径（应用访问目录）
        String codeGenType = app.getCodeGenType();
        //获取文件生成的文件名称
        String sourceDirName = codeGenType + "_" + app.getId();
        //获取完整的文件路径
        String sourceDir = AppConstant.CODE_OUTPUT_ROOT_DIR + File.separator + sourceDirName;
        //6.检查路径是否存在
        File sourceDirFile = new File(sourceDir);
        if (!sourceDirFile.exists() || !sourceDirFile.isDirectory()) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用代码路径不存在，请先生成应用");
        }
        //先判断要生成的类型
        CodeGenTypeEnum codeGenTypeEnum = CodeGenTypeEnum.getEnumByValue(codeGenType);
        if (codeGenTypeEnum == CodeGenTypeEnum.VUE_PROJECT) {
            // Vue 项目需要构建
            boolean buildSuccess = vueProjectBuilder.buildProject(sourceDir);
            ThrowUtils.throwIf(!buildSuccess, ErrorCode.SYSTEM_ERROR, "Vue 项目构建失败，请检查代码和依赖");
            // 检查 dist 目录是否存在
            File distDir = new File(sourceDir, "dist");
            ThrowUtils.throwIf(!distDir.exists(), ErrorCode.SYSTEM_ERROR, "Vue 项目构建完成但未生成 dist 目录");
            // 将 dist 目录作为部署源
            sourceDirFile = distDir;
            log.info("Vue 项目构建成功，将部署 dist 目录: {}", distDir.getAbsolutePath());
        }
        //8.复制文件到部署目录
        //构建部署目录路径
        String deployDirPath = AppConstant.CODE_DEPLOY_ROOT_DIR + File.separator + deployKey;
        try {
            FileUtil.copyContent(sourceDirFile, new File(deployDirPath), true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "部署失败：" + e.getMessage());
        }
        //9.更新数据库
        App updateApp = new App();
        updateApp.setId(appId);
        updateApp.setDeployKey(deployKey);
        updateApp.setDeployedTime(LocalDateTime.now());
        boolean res = this.updateById(updateApp);
        ThrowUtils.throwIf(!res, ErrorCode.OPERATION_ERROR, "更新应用部署信息失败");

        //10. 部署成功后使用截图服务进行截图和插入数据库
        String appDeployUrl = String.format("%s/%s", AppConstant.CODE_DEPLOY_HOST, deployKey);
        //异步生成截图并插入数据库
        generateAppScreenshotAsync(appId, appDeployUrl);
        return appDeployUrl;
    }

    /**
     * 异步生成应用截图并更新封面
     *
     * @param appId        应用ID
     * @param appDeployUrl 应用访问URL
     */
    @Override
    public void generateAppScreenshotAsync(Long appId, String appDeployUrl) {
        // 异步生成截图并插入数据库
        Thread.ofVirtual().start(() -> {
            try {
                // 调用截图服务生成截图并上传
                String screenshotUrl = screenshotService.generateAndUploadScreenshot(appDeployUrl);
                // 更新应用封面字段
                App updateApp = new App();
                updateApp.setId(appId);
                updateApp.setCover(screenshotUrl);
                boolean res = this.updateById(updateApp);
                ThrowUtils.throwIf(!res, ErrorCode.OPERATION_ERROR, "更新应用封面字段失败");
            } catch (Exception e) {
                log.info("异步生成截图并插入数据库失败：" + e.getMessage());
            }
        });
    }


    // 关联查询用户信息
    @Override
    public AppVO getAppVO(App app) {
        if (app == null) {
            return null;
        }
        AppVO appVO = new AppVO();
        BeanUtil.copyProperties(app, appVO);
        // 关联查询用户信息
        Long userId = app.getUserId();
        if (userId != null) {
            User user = userService.getById(userId);
            UserVO userVO = userService.getUserVO(user);
            appVO.setUser(userVO);
        }
        return appVO;
    }

    //构造查询对象
    @Override
    public QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest) {
        if (appQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = appQueryRequest.getId();
        String appName = appQueryRequest.getAppName();
        String cover = appQueryRequest.getCover();
        String initPrompt = appQueryRequest.getInitPrompt();
        String codeGenType = appQueryRequest.getCodeGenType();
        String deployKey = appQueryRequest.getDeployKey();
        Integer priority = appQueryRequest.getPriority();
        Long userId = appQueryRequest.getUserId();
        String sortField = appQueryRequest.getSortField();
        String sortOrder = appQueryRequest.getSortOrder();
        return QueryWrapper.create().eq("id", id).like("appName", appName).like("cover", cover).like("initPrompt", initPrompt).eq("codeGenType", codeGenType).eq("deployKey", deployKey).eq("priority", priority).eq("userId", userId).orderBy(sortField, "ascend".equals(sortOrder));
    }

    //分页查询用户创建的应用信息
    @Override
    public List<AppVO> getAppVOList(List<App> appList) {
        if (CollUtil.isEmpty(appList)) {
            return new ArrayList<>();
        }
        // 批量获取用户信息，避免 N+1 查询问题
        Set<Long> userIds = appList.stream().map(app -> {
            return app.getUserId();
        }).collect(Collectors.toSet());
        List<User> users = userService.listByIds(userIds);
        //将用户id作为键 UserVO 对象为值的 Map
        Map<Long, UserVO> userVOMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> userService.getUserVO(user)));
//        Map<Long, UserVO> userVOMap = userService.listByIds(userIds).stream()
//                .collect(Collectors.toMap(User::getId, userService::getUserVO));
        //将一个 App 对象列表，转换为带有用户信息的 AppVO 对象列表。
        return appList.stream().map(app -> {
            AppVO appVO = getAppVO(app);
            UserVO userVO = userVOMap.get(app.getUserId());
            appVO.setUser(userVO);
            return appVO;
        }).collect(Collectors.toList());
    }

    /**
     * 删除应用时关联删除对话历史
     *
     * @param id 应用ID
     * @return 是否成功
     */
    @Override
    public boolean removeById(Serializable id) {
        if (id == null) {
            return false;
        }
        Long appId = Long.valueOf(id.toString());
        if (appId <= 0) {
            return false;
        }
        // 先删除关联的对话历史
        try {
            chatHistoryService.deleteByAppId(appId);
        } catch (Exception e) {
            // 记录日志但不阻止应用删除
            log.error("删除应用关联对话历史失败: {}", e.getMessage());
        }
        return super.removeById(id);
    }

}
