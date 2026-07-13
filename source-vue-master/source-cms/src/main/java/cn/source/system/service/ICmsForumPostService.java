package cn.source.system.service;

import cn.source.system.domain.CmsForumPost;
import java.util.List;

/**
 * 论坛帖子Service接口
 */
public interface ICmsForumPostService {
    /** 发布帖子（默认待审核） */
    int insertCmsForumPost(CmsForumPost post);

    /** 审核：通过或驳回 */
    int auditCmsForumPost(Long id, Integer status, String reason);

    /** 逻辑删除帖子 */
    int softDeleteCmsForumPostById(Long id);

    /** 根据ID查询 */
    CmsForumPost selectCmsForumPostById(Long id);

    /** 查询帖子列表（按条件） */
    List<CmsForumPost> selectCmsForumPostList(CmsForumPost post);

    /** 查询待审核列表 */
    List<CmsForumPost> selectPendingList();
}