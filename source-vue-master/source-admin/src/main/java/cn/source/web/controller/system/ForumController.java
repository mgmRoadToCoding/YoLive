package cn.source.web.controller.system;

import cn.source.common.annotation.Log;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.enums.BusinessType;
import cn.source.system.domain.CmsForumPost;
import cn.source.system.service.ICmsForumPostService;
import cn.source.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 论坛管理端接口（审核/列表/删除）
 */
@RestController
@RequestMapping("/system/forum")
public class ForumController extends BaseController {

    @Autowired
    private ICmsForumPostService cmsForumPostService;


    /**
     * 2. 审核通过
     */
    @PreAuthorize("@ss.hasPermi('system:forum:audit')")
    @Log(title = "论坛审核通过", businessType = BusinessType.UPDATE)
    @PutMapping("/posts/{id}/approve")
    public AjaxResult approve(@PathVariable Long id) {
        int rows = cmsForumPostService.auditCmsForumPost(id, 1, null);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error("审核失败");
    }

    /**
     * 2. 审核驳回
     */
    @PreAuthorize("@ss.hasPermi('system:forum:audit')")
    @Log(title = "论坛审核驳回", businessType = BusinessType.UPDATE)
    @PutMapping("/posts/{id}/reject")
    public AjaxResult reject(@PathVariable Long id) {
        int rows = cmsForumPostService.auditCmsForumPost(id, 2, null);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error("驳回失败");
    }

    /**
     * 5. 论坛查询（管理端支持条件过滤）
     */
    @PreAuthorize("@ss.hasPermi('system:forum:list')")
    @GetMapping("/posts")
    public TableDataInfo list(CmsForumPost query) {
        startPage();
        List<CmsForumPost> list = cmsForumPostService.selectCmsForumPostList(query);
        return getDataTable(list);
    }

    /**
     * 6. 删除论坛（逻辑删除）
     */
    @PreAuthorize("@ss.hasPermi('system:forum:remove')")
    @Log(title = "论坛删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/posts/{id}")
    public AjaxResult delete(@PathVariable Long id) {
        int rows = cmsForumPostService.softDeleteCmsForumPostById(id);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error("删除失败");
    }
}
