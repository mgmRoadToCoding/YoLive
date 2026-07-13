package cn.source.system.mapper;

import cn.source.system.domain.CmsForumFavorite;
import cn.source.system.domain.CmsForumPost;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 论坛收藏Mapper接口
 */
public interface CmsForumFavoriteMapper {
    /** 插入收藏 */
    int insertCmsForumFavorite(CmsForumFavorite favorite);

    /** 取消收藏 */
    int deleteCmsForumFavorite(@Param("userId") Long userId, @Param("postId") Long postId);

    /** 是否已收藏 */
    Integer existsCmsForumFavorite(@Param("userId") Long userId, @Param("postId") Long postId);

    /** 查询收藏帖子列表 */
    List<CmsForumPost> selectFavoritePostsByUserId(@Param("userId") Long userId);
}