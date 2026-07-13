package cn.source.system.mapper;

import cn.source.system.domain.CmsForumPost;
import java.util.List;

/**
 * 论坛帖子Mapper接口
 */
public interface CmsForumPostMapper {
    /** 插入帖子 */
    int insertCmsForumPost(CmsForumPost post);

    /** 更新帖子审核状态与理由 */
    int updateCmsForumPostStatus(CmsForumPost post);

    /** 逻辑删除帖子 */
    int softDeleteCmsForumPostById(Long id);

    /** 根据ID查询帖子 */
    CmsForumPost selectCmsForumPostById(Long id);

    /** 查询帖子列表（支持条件） */
    List<CmsForumPost> selectCmsForumPostList(CmsForumPost post);

    /** 查询待审核帖子列表 */
    List<CmsForumPost> selectPendingList();
}