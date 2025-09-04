package com.ming.mingaicode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ming.mingaicode.exceptioon.BusinessException;
import com.ming.mingaicode.exceptioon.ErrorCode;
import com.ming.mingaicode.model.dto.user.UserLoginRequest;
import com.ming.mingaicode.model.dto.user.UserQueryRequest;
import com.ming.mingaicode.model.enums.UserRoleEnum;
import com.ming.mingaicode.model.vo.LoginUserVO;
import com.ming.mingaicode.model.vo.UserVO;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ming.mingaicode.model.entity.User;
import com.ming.mingaicode.mapper.UserMapper;
import com.ming.mingaicode.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ming.mingaicode.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 服务层实现。
 *
 * @author <a href="https://ming-code.work/">ming</a>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    //用户注册
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验参数
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账过短");
        }
        if (userPassword.length() < 4 || checkPassword.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        //2.查询账号是否存在
        QueryWrapper query = new QueryWrapper();
        query.eq(User::getUserAccount, userAccount);
        long count = this.mapper.selectCountByQuery(query);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号已存在");
        }
        //3.保存数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(getEncryptPassword(userPassword));
        user.setUserName("无名");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean save = this.save(user);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败");
        }
        return user.getId();
    }

    //用户数据脱敏
    @Override
    public LoginUserVO getLoginUserVO(User user) {
        LoginUserVO loginUserVO = new LoginUserVO();
        if (user == null) {
            return null;
        }
        BeanUtil.copyProperties(user, loginUserVO);
        return loginUserVO;
    }


    //用户登录
    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StrUtil.hasBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号过短");
        }

        // 2. 加密
        String encryptPassword = getEncryptPassword(userPassword);
        // 查询用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.mapper.selectOneByQuery(queryWrapper);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        // 4. 获得脱敏后的用户信息
        return this.getLoginUserVO(user);
    }
    @Override
    public String getEncryptPassword(String userPassword) {
        // 盐值，混淆密码
        final String SALT = "ming";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }


    //获取当前登录用户信息
    @Override
    public User getLoginUser(HttpServletRequest httpServletRequest) {
        Object attribute = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) attribute;
        if (user == null || user.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        //从数据库查询当前用户信息
        User currentUser = this.getById(user.getId());
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    //用户注销
    @Override
    public boolean userLogout(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (attribute== null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    //获取脱敏的用户信息
    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }

    //获取脱敏的用户信息列表
    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }


    //获取查询条件构造查询条件
    @Override
    public QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String userAccount = userQueryRequest.getUserAccount();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        return QueryWrapper.create()
                .eq("id", id)
                .eq("userRole", userRole)
                .like("userAccount", userAccount)
                .like("userName", userName)
                .like("userProfile", userProfile)
                .orderBy(sortField, "ascend".equals(sortOrder));
    }


}
