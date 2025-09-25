package com.ming.mingaicode.core.parser;

import com.ming.mingaicode.ai.model.HtmlCodeResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML 单文件代码解析器
 *
 * @author yupi
 */
public class HtmlCodeParser implements CodeParser<HtmlCodeResult> {

    private static final Pattern HTML_CODE_PATTERN =
            Pattern.compile("```(?:html|HTML)?\\s*\\r?\\n([\\s\\S]*?)\\s*```", Pattern.CASE_INSENSITIVE);
    @Override
    public HtmlCodeResult parseCode(String codeContent) {
        HtmlCodeResult result = new HtmlCodeResult();
        String htmlCode = extractHtmlCode(codeContent);

        if (htmlCode != null && !htmlCode.trim().isEmpty()) {
            result.setHtmlCode(htmlCode.trim());
        } else {
            // 找不到代码块：返回空（或抛异常，根据业务决定）
            result.setHtmlCode(""); // 或者 throw new IllegalArgumentException("No HTML code block found");
        }
        return result;
    }

    /**
     * 提取HTML代码内容
     *
     * @param content 原始内容
     * @return HTML代码
     */
    private String extractHtmlCode(String content) {
        Matcher matcher = HTML_CODE_PATTERN.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
