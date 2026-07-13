package cn.source.system.websocket;

import cn.source.common.config.RuoYiConfig;
import cn.source.system.domain.ImChatMessage;
import cn.source.system.mapper.ImChatMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 基于官方 OpenAI Java SDK 的 AI 回复服务（Responses API）。
 */
@Component
public class AiReplyService {

    private static final Logger logger = LoggerFactory.getLogger(AiReplyService.class);

    @Value("${openai.model:gpt-4o-mini}")
    private String model;

    @Autowired
    private ImChatMessageMapper imChatMessageMapper;

    public String generateReply(String sessionId, String userMessage) {
        String key = RuoYiConfig.getOpenAiApiKey();
        if (key == null || key.trim().isEmpty()) {
            logger.warn("[AI客服] 未配置 OpenAI API Key，使用固定回复。");
            return "这是ai客服的回复";
        }

        String systemPrompt = "你是一个简洁、友好的AI客服，尽量简短作答。请严格基于会话上下文连续对话，理解用户与客服历史交流，不要重复提问。";
        String userText = userMessage == null ? "" : userMessage;

        HttpURLConnection conn = null;
        try {
            // 构造请求体
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode messages = mapper.createArrayNode();
            // system 指令
            ObjectNode sys = mapper.createObjectNode();
            sys.put("role", "system");
            sys.put("content", systemPrompt);
            messages.add(sys);

            // 如果有会话ID，拼接该会话历史上下文
            boolean hasCurrentUserMessage = false;
            if (sessionId != null && !sessionId.isEmpty()) {
                try {
                    List<ImChatMessage> history = imChatMessageMapper.selectBySessionId(sessionId);
                    if (history != null && !history.isEmpty()) {
                        for (ImChatMessage m : history) {
                            String role = m.getRole();
                            String content = m.getContent();
                            if (content == null || content.trim().isEmpty()) continue;
                            // 将存储中的 agent 映射为 assistant，user 保持不变
                            String openaiRole = "agent".equalsIgnoreCase(role) ? "assistant" : "user";
                            ObjectNode item = mapper.createObjectNode();
                            item.put("role", openaiRole);
                            item.put("content", content);
                            messages.add(item);
                            if ("user".equalsIgnoreCase(openaiRole) && content.trim().equals(userText)) {
                                hasCurrentUserMessage = true;
                            }
                        }
                    }
                } catch (Exception ex) {
                    logger.warn("[AI客服] 加载会话上下文失败，降级为仅当前消息", ex);
                }
            }

            // 若未提供会话或未包含当前用户消息，补充本次用户输入
            if (userText != null && !userText.isEmpty() && !hasCurrentUserMessage) {
                ObjectNode usr = mapper.createObjectNode();
                usr.put("role", "user");
                usr.put("content", userText);
                messages.add(usr);
            }

            ObjectNode payload = mapper.createObjectNode();
            payload.put("model", "deepseek-chat");
            payload.set("messages", messages);

            String body = mapper.writeValueAsString(payload);
            logger.debug("[AI客服] 请求体: {}", body);

            // 发送 HTTP 请求
//            URL url = new URL("https://api.openai.com/v1/chat/completions");
            URL url = new URL("https://api.openai-proxy.org/v1/chat/completions");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + key);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "application/json");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();
            InputStream is = code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream();
            String resp = readStream(is);
            logger.debug("[AI客服] 响应({}): {}", code, resp);

            if (code >= 200 && code < 300) {
                // 解析回复：choices[0].message.content
                JsonNode root = mapper.readTree(resp);
                JsonNode choices = root.path("choices");
                if (choices.isArray() && choices.size() > 0) {
                    JsonNode msg = choices.get(0).path("message");
                    String content = msg.path("content").asText("");
                    if (content != null && !content.trim().isEmpty()) {
                        return content.trim();
                    }
                }
                logger.warn("[AI客服] OpenAI 返回空文本，降级固定回复。");
                return "这是ai客服的回复";
            } else {
                logger.warn("[AI客服] OpenAI HTTP 非 2xx，降级固定回复: code={}", code);
                return "这是ai客服的回复";
            }
        } catch (Exception ex) {
            logger.error("[AI客服] 调用 OpenAI HTTP 接口异常", ex);
            return "这是ai客服的回复";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private String readStream(InputStream is) throws IOException {
        if (is == null) return "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }
}
