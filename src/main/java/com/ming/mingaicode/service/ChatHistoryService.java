package com.ming.mingaicode.service;

import com.ming.mingaicode.model.dto.chathistory.ChatHistoryQueryRequest;
import com.ming.mingaicode.model.entity.User;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.ming.mingaicode.model.entity.ChatHistory;

import java.time.LocalDateTime;

/**
 * 服务层。
 *
 * @author <a href="https://ming-code.work/">ming</a>
 */
public interface ChatHistoryService extends IService<ChatHistory> {

    //添加聊天记录
    boolean addChatHistory(Long appId, Long userId, String message, String messageType);


    //删除指定的对话消息
    boolean deleteByAppId(Long appId);

    /**
     * 分页获取指定app的聊天记录 游标查询
     *
     * @param appId
     * @param pageSize
     * @param lastCreateTime
     * @param loginUser
     * @return
     */
    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                               LocalDateTime lastCreateTime,
                                               User loginUser);

    /**
     * 获取查询包装类
     *
     * @param chatHistoryQueryRequest
     * @return
     */
    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);
}
