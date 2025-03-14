package cn.source.system.controller;

import cn.source.common.constant.HttpStatus;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.core.domain.entity.SysDictData;
import cn.source.common.core.page.TableDataInfo;
import cn.source.common.utils.ip.IpUtils;
import cn.source.system.domain.*;
import cn.source.system.domain.vo.CmsCategoryToolVo;
import cn.source.system.domain.vo.CmsToolVo;
import cn.source.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: cms api控制类
 * @author: 詹Sir
 */
@RestController
@RequestMapping("/api/cmsApi")
public class CmsApiController extends BaseController {

    @Autowired
    private ICmsServiceItemService cmsServiceItemService;

    @Autowired
    private ICmsArticleService cmsArticleService;

    @Autowired
    private ICmsFeedbackService cmsFeedbackService;

    @Autowired
    private ICmsLinkService cmsLinkService;

    @Autowired
    private ICmsDeveloperService cmsDeveloperService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    @Autowired
    private ICmsToolService cmsToolService;

    @Autowired
    private ICmsToolCategoryService cmsToolCategoryService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    /**
     * 根据类型获取服务内容(返回实体)
     */
    @GetMapping("/getServiceItem")
    public CmsServiceItem getServiceItmeByType(CmsServiceItem cmsServiceItem) {
        List<CmsServiceItem> list = cmsServiceItemService.selectCmsServiceItemList(cmsServiceItem);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据类型获取服务内容(返回ajax)
     */
    @GetMapping("/getServiceItemByAjax")
    public AjaxResult getServiceItemByAjax(CmsServiceItem cmsServiceItem) {
        String msg = "服务查找成功";
        List<CmsServiceItem> list = cmsServiceItemService.selectCmsServiceItemList(cmsServiceItem);
        AjaxResult ajaxResult = new AjaxResult(HttpStatus.SUCCESS, msg, list.get(0));
        return ajaxResult;
    }

    /**
     * @Description: 获取文章列表
     */
    @GetMapping("/findArticleList")
    public TableDataInfo findArticleList(CmsArticle cmsArticle) {
        startPage();
        List<CmsArticle> list = cmsArticleService.selectCmsArticleList(cmsArticle);
        return getDataTable(list);
    }

    /**
     * 获取文章详细信息
     */
    @GetMapping(value = "/getArticle/{id}")
    public AjaxResult getArticle(@PathVariable("id") Long id) {
        return AjaxResult.success(cmsArticleService.selectCmsArticleById(id));
    }

    /**
     * @Description: 点赞
     */
    @PostMapping("/starArticle")
    public AjaxResult starArticle(CmsArticle cmsArticle) {
        return toAjax(cmsArticleService.starCmsArticle(cmsArticle));
    }

    /**
     * @Description: 反馈
     */
    @PostMapping("/saveCmsFeedback")
    public AjaxResult saveCmsFeedback(HttpServletRequest request, CmsFeedback cmsFeedback) {
        cmsFeedback.setCreateBy(IpUtils.getIpAddr(request));
        return toAjax(cmsFeedbackService.insertCmsFeedback(cmsFeedback));
    }

    /**
     * @Description: 申请友链
     */
    @PostMapping("/submitLink")
    public AjaxResult submitLink(HttpServletRequest request, CmsLink cmsLink) {
        cmsLink.setCreateBy(IpUtils.getIpAddr(request));
        return toAjax(cmsLinkService.insertCmsLink(cmsLink));
    }

    /**
     * @Description: 获取友链列表
     */
    @GetMapping("/findLinkList")
    public TableDataInfo findLinkList(CmsLink cmsLink) {
        startPage();
        List<CmsLink> list = cmsLinkService.selectCmsLinkList(cmsLink);
        return getDataTable(list);
    }

    /**
     * @Description: 获取成员列表
     */
    @GetMapping("/findDeveloperList")
    public TableDataInfo findDeveloperList(CmsDeveloper cmsDeveloper) {
        startPage();
        List<CmsDeveloper> list = cmsDeveloperService.selectCmsDeveloperList(cmsDeveloper);
        return getDataTable(list);
    }

    /**
     * @Description: 获取公告信息
     */
    @GetMapping("/findNoticeList")
    public TableDataInfo findNoticeList(SysNotice sysNotice) {
        startPage();
        List<SysNotice> list = sysNoticeService.selectNoticeList(sysNotice);
        return getDataTable(list);
    }

    /**
     * @Description: 获取工具列表
     */
    @GetMapping("/findToolList")
    public TableDataInfo findToolList(CmsTool cmsTool) {
        startPage();
        List<CmsToolVo> list = cmsToolService.selectCmsToolList(cmsTool);
        return getDataTable(list);
    }

    /**
     * 获取分类与工具列表 - 无分页
     */
    @GetMapping("/findCategoryToolList")
    public TableDataInfo findCategoryToolList(CmsTool cmsTool) {
        List<CmsCategoryToolVo> list = cmsToolCategoryService.selectCategoryToolList(cmsTool);
        return getDataTable(list);
    }

    /**
     * 获取工具分类
     */
    @GetMapping("/findToolCategoryList")
    public TableDataInfo findToolCategoryList(CmsToolCategory cmsToolCategory) {
        List<CmsToolCategory> list = cmsToolCategoryService.selectCmsToolCategoryList(cmsToolCategory);
        return getDataTable(list);
    }

    /**
     * 获取工具详细信息
     */
    @GetMapping(value = "/getTool/{id}")
    public AjaxResult getTool(@PathVariable("id") Long id) {
        return AjaxResult.success(cmsToolService.selectCmsToolById(id));
    }

    /**
     * @Description: 获取字典数据
     */
    @GetMapping("/findDicList")
    public TableDataInfo findDicList(SysDictData sysDictData) {
        List<SysDictData> list = sysDictDataService.selectDictDataList(sysDictData);
        return getDataTable(list);
    }
}
