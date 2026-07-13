package cn.source.system.controller;

import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.system.domain.ImChatSession;
import cn.source.system.mapper.ImChatSessionMapper;
import cn.source.system.mapper.ImChatMessageMapper;
import cn.source.system.domain.ImChatMessage;
import cn.source.system.dto.MessageLiteView;
import cn.source.system.dto.UserSessionMessagesView;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 会话创建接口：提供 /chat/session/start 创建新会话
 */
@RestController
@RequestMapping("/chat")
public class ChatSessionController extends BaseController {

    @Autowired
    private ImChatSessionMapper imChatSessionMapper;

    @Autowired
    private ImChatMessageMapper imChatMessageMapper;

    /**
     * 通过 POST 请求体创建会话
     */
    @PostMapping("/session/start")
    public AjaxResult startSession(@RequestBody ImChatSession session) {
        session.setUserId(getUserId());
        // title 可选，若为空给个默认
        if (session.getTitle() == null || session.getTitle().trim().isEmpty()) {
            session.setTitle("新会话");
        }
        if (session.getResponderId() == null) {
            session.setResponderId(1L);
        }
        int rows = imChatSessionMapper.insertImChatSession(session);
        if (rows <= 0) {
            return AjaxResult.error("创建会话失败");
        }
        return AjaxResult.success(session);
    }

    /**
     * 查询当前登录用户的所有会话（不分页）
     */
    @GetMapping("/sessions")
    public AjaxResult listMySessions() {
        ImChatSession criteria = new ImChatSession();
        criteria.setUserId(getUserId());
        List<ImChatSession> sessions = imChatSessionMapper.selectImChatSessionList(criteria);

        List<UserSessionMessagesView> result = new ArrayList<>();
        for (ImChatSession s : sessions) {
            UserSessionMessagesView dto = new UserSessionMessagesView();
            dto.setId(s.getId());
            dto.setUserId(s.getUserId());
            dto.setTitle(s.getTitle());
            dto.setType(s.getType());
            String sid = s.getId() == null ? null : String.valueOf(s.getId());
            List<MessageLiteView> liteMessages = new ArrayList<>();
            List<ImChatMessage> messages = sid == null ? new ArrayList<>() : imChatMessageMapper.selectBySessionId(sid);
            for (ImChatMessage m : messages) {
                MessageLiteView v = new MessageLiteView();
                v.setContent(m.getContent());
                v.setRole(m.getRole());
                v.setCreateTime(m.getCreateTime());
                liteMessages.add(v);
            }
            dto.setMessages(liteMessages);
            result.add(dto);
        }
        return AjaxResult.success(result);
    }

    /**
     * 根据会话ID查询当前用户该会话的消息
     */
    @GetMapping("/messages/{id}")
    public AjaxResult getMyMessagesBySession(@PathVariable("id") String sessionId) {
        // 校验会话归属，避免越权访问
        Long sidLong = null;
        try { sidLong = Long.parseLong(sessionId); } catch (Exception ignore) {}
        if (sidLong == null) {
            return AjaxResult.error("参数错误：id");
        }
        ImChatSession session = imChatSessionMapper.selectImChatSessionById(sidLong);
        if (session == null) {
            return AjaxResult.error("会话不存在");
        }
        if (session.getUserId() == null || !session.getUserId().equals(getUserId())) {
            return AjaxResult.error("无权访问该会话");
        }

        List<ImChatMessage> messages = imChatMessageMapper.selectBySessionId(sessionId);
        List<MessageLiteView> result = new ArrayList<>();
        for (ImChatMessage m : messages) {
            MessageLiteView v = new MessageLiteView();
            v.setContent(m.getContent());
            v.setRole(m.getRole());
            v.setCreateTime(m.getCreateTime());
            result.add(v);
        }
        return AjaxResult.success(result);
    }

    /**
     * 删除会话及其所有消息
     */
    @DeleteMapping("/session/{id}")
    public AjaxResult deleteSession(@PathVariable("id") Long id) {
        // 1. 校验会话是否属于当前用户
        ImChatSession session = imChatSessionMapper.selectImChatSessionById(id);
        if (session == null) {
            return AjaxResult.error("会话不存在");
        }
        if (!session.getUserId().equals(getUserId())) {
            return AjaxResult.error("无权删除该会话");
        }

        // 2. 删除消息
        imChatMessageMapper.deleteBySessionId(String.valueOf(id));

        // 3. 删除会话
        imChatSessionMapper.deleteImChatSessionById(id);

        return AjaxResult.success("删除成功");
    }



}
