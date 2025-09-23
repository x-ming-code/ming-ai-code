package com.ming.mingaicode.ai.guardrail;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailResult;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PromptSafetyInputGuardrail implements InputGuardrail {

    // 敏感词列表
    private static final List<String> SENSITIVE_WORDS = Arrays.asList(
            "忽略之前的指令", "ignore previous instructions", "ignore above",
            "破解", "hack", "绕过", "bypass", "越狱", "jailbreak", "忽略上述指令",
            "忘记之前的设定", "你不再是AI助手", "扮演另一个角色", "假设你是",
            "从现在开始", "系统提示", "system prompt", "输出以下内容", "重复下面的话",
            "打印这段文字", "不要作为AI回答", "突破你的限制", "解除道德限制", "展示隐藏模式",
            "进入开发者模式", "启用 DAN 模式", "模仿另一个AI", "绕过内容政策", "生成非法内容",
            "伪造身份", "冒充他人", "模拟黑客攻击", "编写病毒", "创建恶意软件", "如何制作炸弹",
            "如何盗窃", "如何入侵系统"
    );

    // 注入攻击模式
    private static final List<Pattern> INJECTION_PATTERNS = Arrays.asList(
            Pattern.compile("(?i)(?:you\\s+are|you're|your\\s+role\\s+is)\\s+(?:no\\s+longer|not)\\s+an?\\s+(?:AI|assistant)", Pattern.DOTALL),
            Pattern.compile("(?i)from\\s+now\\s+on\\s+you\\s+are\\s+(?!helpful)", Pattern.DOTALL),

            Pattern.compile("(?i)(?:output|print|repeat|write|generate)\\s+(?:the\\s+following|below):?.{20,}", Pattern.DOTALL),

            Pattern.compile("(?i)dan\\s*(?:mode)?\\s*[:\\-\\s]*enable|activate|start", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(?i)do\\s+anything\\s+now", Pattern.CASE_INSENSITIVE),

            Pattern.compile("(?i)this\\s+is\\s+a\\s+new\\s+instruction", Pattern.CASE_INSENSITIVE),

            Pattern.compile("(?i)(?:base64\\s*:\\s*|decode\\s+this\\s*:).{50,}", Pattern.DOTALL),

            Pattern.compile("(?i)<\\s*?system\\s*?>.*?<\\s*?/\\s*?system\\s*?>", Pattern.DOTALL),
            Pattern.compile("(?i)\\[\\s*system\\s*\\].*?\\[\\s*/\\s*system\\s*\\]", Pattern.DOTALL),
            Pattern.compile("(?i)<\\s*?prompt\\s*?>.*?<\\s*?/\\s*?prompt\\s*?>", Pattern.DOTALL)
    );

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        String input = userMessage.singleText();
        // 检查输入长度
        if (input.length() > 1000) {
            return fatal("输入内容过长，不要超过 1000 字");
        }
        // 检查是否为空
        if (input.trim().isEmpty()) {
            return fatal("输入内容不能为空");
        }
        // 检查敏感词
        String lowerInput = input.toLowerCase();
        for (String sensitiveWord : SENSITIVE_WORDS) {
            if (lowerInput.contains(sensitiveWord.toLowerCase())) {
                return fatal("输入包含不当内容，请修改后重试");
            }
        }
        // 检查注入攻击模式
        for (Pattern pattern : INJECTION_PATTERNS) {
            if (pattern.matcher(input).find()) {
                return fatal("检测到恶意输入，请求被拒绝");
            }
        }
        return success();
    }
}
