package cn.source.system.service;

import cn.source.system.domain.CmsForumPost;
import cn.source.system.domain.CmsForumFavorite;
import java.util.List;

/**
 * 论坛收藏Service接口
 */
public interface ICmsForumFavoriteService {
    /** 收藏 */
    int insertCmsForumFavorite(CmsForumFavorite favorite);

    /** 取消收藏 */
    int deleteCmsForumFavorite(Long userId, Long postId);

    /** 是否已收藏 */
    boolean existsCmsForumFavorite(Long userId, Long postId);

    /** 查询用户收藏的帖子 */
    List<CmsForumPost> selectFavoritePostsByUserId(Long userId);
}