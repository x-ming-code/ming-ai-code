package com.ming.mingaicode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ming.mingaicode.constant.AppConstant;
import com.ming.mingaicode.core.AiCodeGeneratorFacade;
import com.ming.mingaicode.exceptioon.BusinessException;
import com.ming.mingaicode.exceptioon.ErrorCode;
import com.ming.mingaicode.exceptioon.ThrowUtils;
import com.ming.mingaicode.model.dto.app.AppQueryRequest;
import com.ming.mingaicode.model.dto.app.AppVO;
import com.ming.mingaicode.model.entity.User;
import com.ming.mingaicode.model.enums.ChatHistoryMessageTypeEnum;
import com.ming.mingaicode.model.enums.CodeGenTypeEnum;
import com.ming.mingaicode.model.vo.UserVO;
import com.ming.mingaicode.service.ChatHistoryService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        Flux<String> stringFlux = aiCodeGeneratorFacade.generateAndSaveCodeStreamT(message, enumByValue, appId);
        StringBuilder stringBuilder = new StringBuilder();
        return stringFlux
                .map(chunk -> {
                    stringBuilder.append(chunk);
                    return chunk;
                }).doOnComplete(() -> {
                    //流式响应完成后，添加AI消息到对话历史
                    String aiResponse = stringBuilder.toString();
                    if (StrUtil.isNotBlank(aiResponse)) {
                        chatHistoryService.addChatHistory(appId, loginUser.getId(), aiResponse, ChatHistoryMessageTypeEnum.AI.getValue());
                    }
                }).doOnError(error -> {
                    // 如果AI回复失败，也要记录错误消息
                    String errorMessage = "AI回复失败: " + error.getMessage();
                    chatHistoryService.addChatHistory(appId, loginUser.getId(), errorMessage, ChatHistoryMessageTypeEnum.AI.getValue());

                });


    }

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
        //7.复制文件到部署目录
        //构建部署目录路径
        String deployDirPath = AppConstant.CODE_DEPLOY_ROOT_DIR + File.separator + deployKey;
        try {
            FileUtil.copyContent(sourceDirFile, new File(deployDirPath), true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "部署失败：" + e.getMessage());
        }
        //8.更新数据库
        App updateApp = new App();
        updateApp.setId(appId);
        updateApp.setDeployKey(deployKey);
        updateApp.setDeployedTime(LocalDateTime.now());
        boolean res = this.updateById(updateApp);
        ThrowUtils.throwIf(!res, ErrorCode.OPERATION_ERROR, "更新应用部署信息失败");

        return String.format("%s/%s", AppConstant.CODE_DEPLOY_HOST, deployKey);
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
