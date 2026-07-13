package cn.source.system.service.impl;

import cn.source.common.utils.DateUtils;
import cn.source.system.domain.CmsForumFavorite;
import cn.source.system.domain.CmsForumPost;
import cn.source.system.mapper.CmsForumFavoriteMapper;
import cn.source.system.service.ICmsForumFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsForumFavoriteServiceImpl implements ICmsForumFavoriteService {

    @Autowired
    private CmsForumFavoriteMapper cmsForumFavoriteMapper;

    @Override
    public int insertCmsForumFavorite(CmsForumFavorite favorite) {
        favorite.setCreateTime(DateUtils.getNowDate());
        favorite.setUpdateTime(DateUtils.getNowDate());
        return cmsForumFavoriteMapper.insertCmsForumFavorite(favorite);
    }

    @Override
    public int deleteCmsForumFavorite(Long userId, Long postId) {
        return cmsForumFavoriteMapper.deleteCmsForumFavorite(userId, postId);
    }

    @Override
    public boolean existsCmsForumFavorite(Long userId, Long postId) {
        Integer count = cmsForumFavoriteMapper.existsCmsForumFavorite(userId, postId);
        return count != null && count > 0;
    }

    @Override
    public List<CmsForumPost> selectFavoritePostsByUserId(Long userId) {
        return cmsForumFavoriteMapper.selectFavoritePostsByUserId(userId);
    }
}