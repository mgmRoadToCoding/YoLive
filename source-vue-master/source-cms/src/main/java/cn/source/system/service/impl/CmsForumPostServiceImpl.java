package cn.source.system.service.impl;

import cn.source.common.utils.DateUtils;
import cn.source.system.domain.CmsForumPost;
import cn.source.system.mapper.CmsForumPostMapper;
import cn.source.system.service.ICmsForumPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsForumPostServiceImpl implements ICmsForumPostService {

    @Autowired
    private CmsForumPostMapper cmsForumPostMapper;

    @Override
    public int insertCmsForumPost(CmsForumPost post) {
        post.setCreateTime(DateUtils.getNowDate());
        post.setUpdateTime(DateUtils.getNowDate());
        // 默认待审核
        if (post.getStatus() == null) {
            post.setStatus(0);
        }
        // 默认未删除
        post.setIsDeleted(0);
        return cmsForumPostMapper.insertCmsForumPost(post);
    }

    @Override
    public int auditCmsForumPost(Long id, Integer status, String reason) {
        CmsForumPost p = new CmsForumPost();
        p.setId(id);
        p.setStatus(status);
        p.setAuditReason(reason);
        p.setUpdateTime(DateUtils.getNowDate());
        return cmsForumPostMapper.updateCmsForumPostStatus(p);
    }

    @Override
    public int softDeleteCmsForumPostById(Long id) {
        return cmsForumPostMapper.softDeleteCmsForumPostById(id);
    }

    @Override
    public CmsForumPost selectCmsForumPostById(Long id) {
        return cmsForumPostMapper.selectCmsForumPostById(id);
    }

    @Override
    public List<CmsForumPost> selectCmsForumPostList(CmsForumPost post) {
        return cmsForumPostMapper.selectCmsForumPostList(post);
    }

    @Override
    public List<CmsForumPost> selectPendingList() {
        return cmsForumPostMapper.selectPendingList();
    }
}