package com.ming.mingaicode.service;

import com.ming.mingaicode.model.dto.app.AppQueryRequest;
import com.ming.mingaicode.model.dto.app.AppVO;
import com.ming.mingaicode.model.entity.User;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.ming.mingaicode.model.entity.App;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 *  服务层。
 *
 * @author <a href="https://ming-code.work/">ming</a>
 */
public interface AppService extends IService<App> {

    // 关联查询用户信息
    AppVO getAppVO(App app);

    //构造查询对象
    QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);

    List<AppVO> getAppVOList(List<App> appList);

    //应用聊天生成代码（流式 SSE）
    Flux<String> chatToGenCode(Long appId, String message, User loginUser);

    /**
     * 应用部署
     *
     * @param appId   应用 ID
     * @param loginUser 登录用户
     * @return 部署成功后可访问的网址
     */
     String deployApp(Long appId, User loginUser);

    /**
     * 异步生成应用截图并更新封面
     *
     * @param appId  应用ID
     * @param appDeployUrl 应用访问URL
     */
    void generateAppScreenshotAsync(Long appId, String appDeployUrl);

}
