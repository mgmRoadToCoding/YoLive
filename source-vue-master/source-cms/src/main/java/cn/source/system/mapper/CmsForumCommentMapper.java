package cn.source.system.mapper;

import cn.source.system.domain.CmsForumComment;
import java.util.List;

/**
 * 论坛评论Mapper接口
 */
public interface CmsForumCommentMapper {
    /** 插入评论 */
    int insertCmsForumComment(CmsForumComment comment);

    /** 查询评论列表（按时间升序） */
    List<CmsForumComment> selectCmsForumCommentListByPostId(Long postId);

    /** 逻辑删除评论 */
    int softDeleteCmsForumCommentById(Long id);
}