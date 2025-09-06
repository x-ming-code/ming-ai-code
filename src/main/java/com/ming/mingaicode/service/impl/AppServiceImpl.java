package com.ming.mingaicode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ming.mingaicode.model.entity.App;
import com.ming.mingaicode.mapper.AppMapper;
import com.ming.mingaicode.service.AppService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author <a href="https://ming-code.work/">ming</a>
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>  implements AppService{

}
