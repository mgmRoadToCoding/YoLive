package cn.source.system.service.impl;

import cn.source.common.utils.DateUtils;
import cn.source.system.domain.CmsForumComment;
import cn.source.system.mapper.CmsForumCommentMapper;
import cn.source.system.service.ICmsForumCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsForumCommentServiceImpl implements ICmsForumCommentService {

    @Autowired
    private CmsForumCommentMapper cmsForumCommentMapper;

    @Override
    public int insertCmsForumComment(CmsForumComment comment) {
        comment.setCreateTime(DateUtils.getNowDate());
        comment.setUpdateTime(DateUtils.getNowDate());
        comment.setIsDeleted(0);
        return cmsForumCommentMapper.insertCmsForumComment(comment);
    }

    @Override
    public List<CmsForumComment> selectCmsForumCommentListByPostId(Long postId) {
        return cmsForumCommentMapper.selectCmsForumCommentListByPostId(postId);
    }

    @Override
    public int softDeleteCmsForumCommentById(Long id) {
        return cmsForumCommentMapper.softDeleteCmsForumCommentById(id);
    }
}