package cn.source.system.mapper;

import cn.source.system.domain.ImChatMessage;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 聊天消息Mapper接口。
 */
@Mapper
public interface ImChatMessageMapper {
    /**
     * 插入一条聊天消息记录。
     * @param message 聊天消息
     * @return 影响行数
     */
    int insertImChatMessage(ImChatMessage message);

    /**
     * 根据会话ID查询该会话的所有消息，按创建时间升序。
     */
    List<ImChatMessage> selectBySessionId(String sessionId);

    /**
     * 条件查询消息列表（支持 userId、sessionId、role、content 关键词）
     */
    List<ImChatMessage> selectImChatMessageList(
            @Param("userId") Long userId,
            @Param("sessionId") String sessionId,
            @Param("role") String role,
            @Param("keyword") String keyword
    );

    /**
     * 根据ID查询消息详情
     */
    ImChatMessage selectImChatMessageById(Long id);

    /**
     * 统计会话内消息数量
     */
    int countBySessionId(@Param("sessionId") String sessionId);

    /**
     * 查询会话最后一条消息
     */
    ImChatMessage selectLastBySessionId(@Param("sessionId") String sessionId);

    /**
     * 删除指定会话
     */
    int deleteImChatSessionById(Long id);

    /**
     * 删除指定会话下的所有消息
     */
    int deleteBySessionId(String sessionId);

}