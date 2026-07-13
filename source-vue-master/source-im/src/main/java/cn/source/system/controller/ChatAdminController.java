package cn.source.system.controller;

import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.core.page.TableDataInfo;
import cn.source.common.core.domain.entity.SysUser;
import cn.source.system.domain.ImChatMessage;
import cn.source.system.domain.ImChatSession;
import cn.source.system.dto.ChatSessionRow;
import cn.source.system.mapper.ImChatMessageMapper;
import cn.source.system.mapper.ImChatSessionMapper;
import cn.source.system.service.ISysUserService;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台聊天查询接口：提供会话与消息的查询能力
 */
@RestController
@RequestMapping("/chat/admin")
public class ChatAdminController extends BaseController {

    @Autowired
    private ImChatSessionMapper imChatSessionMapper;

    @Autowired
    private ImChatMessageMapper imChatMessageMapper;

    /**
     * 会话列表查询（支持 userId、type、responderId、title 模糊）
     */
    @GetMapping("/sessions")
    public TableDataInfo listSessions(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "responderId", required = false) Long responderId,
            @RequestParam(value = "title", required = false) String title
    ) {
        startPage();
        ImChatSession criteria = new ImChatSession();
        criteria.setUserId(userId);
        criteria.setType(type);
        criteria.setResponderId(responderId);
        criteria.setTitle(title);
        List<ChatSessionRow> rows = imChatSessionMapper.selectChatSessionRows(criteria);
        return getDataTable(rows);
    }

    /**
     * 会话详情（包含基础信息与统计）
     */
    @GetMapping("/sessions/{id}")
    public AjaxResult getSessionDetail(@PathVariable("id") Long id) {
        ImChatSession session = imChatSessionMapper.selectImChatSessionById(id);
        if (session == null) {
            return AjaxResult.error("会话不存在");
        }
        String sessionIdStr = String.valueOf(session.getId());
        int messageCount = imChatMessageMapper.countBySessionId(sessionIdStr);
        ImChatMessage lastMessage = imChatMessageMapper.selectLastBySessionId(sessionIdStr);

        Map<String, Object> data = new HashMap<>();
        data.put("session", session);
        JSONObject stats = new JSONObject();
        stats.put("messageCount", messageCount);
        stats.put("lastMessageTime", lastMessage != null ? lastMessage.getCreateTime() : null);
        stats.put("lastMessageRole", lastMessage != null ? lastMessage.getRole() : null);
        data.put("stats", stats);
        return AjaxResult.success(data);
    }


    /**
     * 根据会话ID查询该会话的全部消息
     */
    @GetMapping("/messages/{id}")
    public AjaxResult getMessagesBySession(@PathVariable("id") String sessionId) {
        List<ImChatMessage> messages = imChatMessageMapper.selectBySessionId(sessionId);
        return AjaxResult.success(messages);
    }
}
