package cn.source.system.service;

import cn.source.system.domain.CmsForumComment;
import java.util.List;

/**
 * 论坛评论Service接口
 */
public interface ICmsForumCommentService {
    /** 新增评论 */
    int insertCmsForumComment(CmsForumComment comment);

    /** 根据帖子查询评论列表 */
    List<CmsForumComment> selectCmsForumCommentListByPostId(Long postId);

    /** 逻辑删除评论 */
    int softDeleteCmsForumCommentById(Long id);
}