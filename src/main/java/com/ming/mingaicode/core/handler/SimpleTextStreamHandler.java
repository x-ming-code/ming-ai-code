package com.ming.mingaicode.core.handler;

import cn.hutool.core.util.StrUtil;
import com.ming.mingaicode.model.entity.User;
import com.ming.mingaicode.model.enums.ChatHistoryMessageTypeEnum;
import com.ming.mingaicode.service.ChatHistoryService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * @author ming
 * @description 简单文本流处理器处理 HTML 和 MULTI_FILE 类型的流式响应
 * @date 2025/9/13 20:26
 */
@Slf4j
public class SimpleTextStreamHandler {

    /**
     * 处理传统流（HTML, MULTI_FILE）
     * 直接收集完整的文本响应
     *
     * @param originFlux         原始流
     * @param chatHistoryService 聊天历史服务
     * @param appId              应用ID
     * @param loginUser          登录用户
     * @return 处理后的流
     */
    public Flux<String> handle(Flux<String> originFlux, ChatHistoryService chatHistoryService,
                               long appId, User loginUser) {
        StringBuilder stringBuilder = new StringBuilder();
        return originFlux
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

    ;
}
