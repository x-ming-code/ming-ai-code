package com.ming.mingaicode.service;

import com.ming.mingaicode.model.dto.app.AppQueryRequest;
import com.ming.mingaicode.model.dto.app.AppVO;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.ming.mingaicode.model.entity.App;

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
}
