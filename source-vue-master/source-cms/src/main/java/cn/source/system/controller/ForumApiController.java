package cn.source.system.controller;

import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.utils.StringUtils;
import cn.source.system.domain.CmsForumPost;
import cn.source.system.domain.CmsForumComment;
import cn.source.system.domain.CmsForumFavorite;
import cn.source.system.service.ICmsForumPostService;
import cn.source.system.service.ICmsForumCommentService;
import cn.source.system.service.ICmsForumFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 论坛用户端 API
 */
@RestController
@RequestMapping("/forum")
public class ForumApiController extends BaseController {

    @Autowired
    private ICmsForumPostService cmsForumPostService;

    @Autowired
    private ICmsForumCommentService cmsForumCommentService;

    @Autowired
    private ICmsForumFavoriteService cmsForumFavoriteService;

    /** 域名前缀，用于返回绝对路径 */
    @Value("${ruoyi.domain}")
    private String domain;

    /**
     * 1. 发布帖子（默认待审核）
     */
    @PostMapping("/posts")
    public AjaxResult publish(@RequestBody CmsForumPost post) {
        if (StringUtils.isEmpty(post.getTitle()) || StringUtils.isEmpty(post.getContent())) {
            return AjaxResult.error("标题或内容不能为空");
        }
        post.setUserId(getUserId());
        post.setStatus(0); // 待审核
        int rows = cmsForumPostService.insertCmsForumPost(post);
        if (rows > 0) {
            post.setImages(toAbsoluteImages(post.getImages()));
            return AjaxResult.success(post);
        }
        return AjaxResult.error("发布失败");
    }

    /**
     * 5. 论坛查询（仅显示已审核通过的）
     */
    @GetMapping("/posts")
    public AjaxResult list(@RequestParam(value = "keyword", required = false) String keyword) {
        CmsForumPost query = new CmsForumPost();
        query.setStatus(1); // 仅查询已通过
        if (StringUtils.isNotEmpty(keyword)) {
            query.setTitle(keyword);
        }
        List<CmsForumPost> list = cmsForumPostService.selectCmsForumPostList(query);
        // 转换图片为绝对路径
        for (CmsForumPost p : list) {
            p.setImages(toAbsoluteImages(p.getImages()));
        }
        return AjaxResult.success(list);
    }

    /**
     * 帖子详情（已通过或作者本人可见）
     */
    @GetMapping("/posts/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        CmsForumPost p = cmsForumPostService.selectCmsForumPostById(id);
        if (p == null || p.getIsDeleted() != null && p.getIsDeleted() == 1) {
            return AjaxResult.error("帖子不存在");
        }
        Long uid = null;
        try { uid = getUserId(); } catch (Exception ignored) {}
        if (p.getStatus() != null && p.getStatus() == 1) {
            p.setImages(toAbsoluteImages(p.getImages()));
            return AjaxResult.success(p);
        }
        if (uid != null && p.getUserId() != null && uid.equals(p.getUserId())) {
            p.setImages(toAbsoluteImages(p.getImages()));
            return AjaxResult.success(p);
        }
        return AjaxResult.error("帖子尚未审核通过");
    }

    /**
     * 6. 删除论坛（作者本人可删除，逻辑删除）
     */
    @DeleteMapping("/posts/{id}")
    public AjaxResult deletePost(@PathVariable Long id) {
        CmsForumPost p = cmsForumPostService.selectCmsForumPostById(id);
        if (p == null || p.getIsDeleted() != null && p.getIsDeleted() == 1) {
            return AjaxResult.error("帖子不存在");
        }
        if (!getUserId().equals(p.getUserId())) {
            return AjaxResult.error("无权删除非本人帖子");
        }
        int rows = cmsForumPostService.softDeleteCmsForumPostById(id);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error("删除失败");
    }

    /**
     * 4. 评论（新增评论）
     */
    @PostMapping("/posts/{id}/comments")
    public AjaxResult comment(@PathVariable Long id, @RequestBody CmsForumComment comment) {
        if (StringUtils.isEmpty(comment.getContent())) {
            return AjaxResult.error("评论内容不能为空");
        }
        comment.setPostId(id);
        comment.setUserId(getUserId());
        int rows = cmsForumCommentService.insertCmsForumComment(comment);
        return rows > 0 ? AjaxResult.success(comment) : AjaxResult.error("评论失败");
    }

    /**
     * 评论列表
     */
    @GetMapping("/posts/{id}/comments")
    public AjaxResult comments(@PathVariable Long id) {
        List<CmsForumComment> list = cmsForumCommentService.selectCmsForumCommentListByPostId(id);
        return AjaxResult.success(list);
    }

    /**
     * 3. 收藏
     */
    @PostMapping("/posts/{id}/favorite")
    public AjaxResult favorite(@PathVariable Long id) {
        Long userId = getUserId();
        if (cmsForumFavoriteService.existsCmsForumFavorite(userId, id)) {
            return AjaxResult.success("已收藏");
        }
        CmsForumFavorite f = new CmsForumFavorite();
        f.setUserId(userId);
        f.setPostId(id);
        int rows = cmsForumFavoriteService.insertCmsForumFavorite(f);
        return rows > 0 ? AjaxResult.success(f) : AjaxResult.error("收藏失败");
    }

    /**
     * 3. 取消收藏
     */
    @DeleteMapping("/posts/{id}/favorite")
    public AjaxResult unfavorite(@PathVariable Long id) {
        int rows = cmsForumFavoriteService.deleteCmsForumFavorite(getUserId(), id);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error("取消收藏失败");
    }

    /**
     * 7. 查询收藏的列表（当前用户）
     */
    @GetMapping("/favorites")
    public AjaxResult myFavorites() {
        List<CmsForumPost> list = cmsForumFavoriteService.selectFavoritePostsByUserId(getUserId());
        for (CmsForumPost p : list) {
            p.setImages(toAbsoluteImages(p.getImages()));
        }
        return AjaxResult.success(list);
    }

    /**
     * 我的帖子列表
     */
    @GetMapping("/my/posts")
    public AjaxResult myPosts() {
        CmsForumPost query = new CmsForumPost();
        query.setUserId(getUserId());
        query.setIsDeleted(0);
        List<CmsForumPost> list = cmsForumPostService.selectCmsForumPostList(query);
        for (CmsForumPost p : list) {
            p.setImages(toAbsoluteImages(p.getImages()));
        }
        return AjaxResult.success(list);
    }

    /**
     * 将逗号分隔的资源路径转换为绝对URL集合（仍以逗号分隔返回）
     */
    private String toAbsoluteImages(String images) {
        if (images == null || images.trim().isEmpty()) {
            return images;
        }
        String[] arr = images.split(",");
        StringBuilder sb = new StringBuilder();
        for (String raw : arr) {
            String v = raw == null ? "" : raw.trim();
            if (v.isEmpty()) {
                continue;
            }
            String abs = buildUrl(v);
            if (sb.length() > 0) sb.append(',');
            sb.append(abs);
        }
        return sb.toString();
    }

    /** 单个资源路径转换为绝对URL */
    private String buildUrl(String path) {
        if (path == null || path.trim().isEmpty()) {
            return path;
        }
        String v = path.trim();
        if (v.startsWith("http://") || v.startsWith("https://")) {
            return v;
        }
        String prefix = domain != null ? domain.replaceAll("/+$", "") : "";
        return prefix + v;
    }
}
