package com.ming.mingaicode.service;

import com.ming.mingaicode.model.dto.user.UserLoginRequest;
import com.ming.mingaicode.model.dto.user.UserQueryRequest;
import com.ming.mingaicode.model.vo.LoginUserVO;
import com.ming.mingaicode.model.vo.UserVO;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.ming.mingaicode.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 服务层。
 *
 * @author <a href="https://ming-code.work/">ming</a>
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);


    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 用户登录
     *
     * @param
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    String getEncryptPassword(String userPassword);

    //获取当前登录用户信息
    User getLoginUser(HttpServletRequest httpServletRequest);

    //用户注销
    boolean userLogout(HttpServletRequest request);

    //获取脱敏的用户信息
    UserVO getUserVO(User user);

    //获取脱敏的用户信息列表
    List<UserVO> getUserVOList(List<User> userList);

    //获取查询条件构造查询条件
    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);
}
