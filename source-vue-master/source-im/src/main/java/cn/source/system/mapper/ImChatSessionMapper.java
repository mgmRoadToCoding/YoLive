package cn.source.system.mapper;

import cn.source.system.domain.ImChatSession;
import cn.source.system.dto.ChatSessionRow;
import java.util.List;

/**
 * 会话Mapper
 */
public interface ImChatSessionMapper {
    /**
     * 新增会话
     * @param session 会话实体
     * @return 影响行数
     */
    int insertImChatSession(ImChatSession session);

    /**
     * 根据ID查询会话详情
     */
    ImChatSession selectImChatSessionById(Long id);

    /**
     * 按条件查询会话列表（支持 userId、type、responderId、title 模糊）
     */
    List<ImChatSession> selectImChatSessionList(ImChatSession criteria);

    /**
     * 按条件查询会话列表（携带用户名username，关联sys_user）
     */
    List<ChatSessionRow> selectChatSessionRows(ImChatSession criteria);

    /**
     * 更新会话的处理状态（仅人工会话使用）：0=未处理，1=已处理
     * @param id 会话ID
     * @param handled 状态值
     * @return 影响行数
     */
    int updateHandledById(@org.apache.ibatis.annotations.Param("id") Long id,
                           @org.apache.ibatis.annotations.Param("handled") Integer handled);


    /**
     * 删除指定会话
     * @param id 会话ID
     * @return 影响行数
     */
    int deleteImChatSessionById(Long id);

}
