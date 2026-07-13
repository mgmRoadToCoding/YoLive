package cn.source.system.controller;

import cn.source.common.config.RuoYiConfig;
import cn.source.common.core.controller.BaseController;
import cn.source.common.core.domain.AjaxResult;
import cn.source.common.utils.file.FileUploadUtils;
import cn.source.system.domain.CmsFaq;
import cn.source.system.service.ICmsFaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 常见问题 API 控制器
 */
@RestController
@RequestMapping("/system/faq")
public class FaqApiController extends BaseController {

    @Autowired
    private ICmsFaqService cmsFaqService;

    /** 域名前缀，用于返回绝对路径 */
    @Value("${ruoyi.domain}")
    private String domain;

    /**
     * 接口1：新增常见问题（图标改为上传图片，或传已上传文件ID）
     * 支持 multipart/form-data：icon 文件 + 名称/链接/排序
     * 也支持通过 iconId 直接传已有文件ID（即 /profile 下的资源路径）。
     */
    @PostMapping("/create")
    public AjaxResult create(
            @RequestParam("faqName") String faqName,
            @RequestParam("faqLink") String faqLink,
            @RequestParam("sort") Integer sort,
            @RequestParam(value = "icon", required = false) MultipartFile icon,
            @RequestParam(value = "iconId", required = false) String iconId
    ) {
        try {
            CmsFaq cmsFaq = new CmsFaq();
            cmsFaq.setFaqName(faqName);
            cmsFaq.setFaqLink(faqLink);
            cmsFaq.setSort(sort);

            // 优先使用上传的文件
            if (icon != null && !icon.isEmpty()) {
                String uploadPath = RuoYiConfig.getUploadPath();
                String fileName = FileUploadUtils.upload(uploadPath, icon);
                // 保存文件ID（实际为资源路径），前端可用 domain + fileName 访问
                cmsFaq.setFaqIcon(fileName);
            } else if (iconId != null && iconId.trim().length() > 0) {
                // 如果前端已经走了 /common/upload，直接保存其返回的 fileName 作为ID
                cmsFaq.setFaqIcon(iconId.trim());
            }

            int rows = cmsFaqService.insertCmsFaq(cmsFaq);
            // 返回绝对路径
            if (rows > 0) {
                cmsFaq.setFaqIcon(buildIconUrl(cmsFaq.getFaqIcon()));
                return AjaxResult.success(cmsFaq);
            }
            return AjaxResult.error("新增失败");
        } catch (Exception e) {
            return AjaxResult.error("上传或保存失败: " + e.getMessage());
        }
    }

    /**
     * 接口2：查询常见问题，返回所有并按排序显示
     */
    @GetMapping("/list")
    public AjaxResult list() {
        List<CmsFaq> list = cmsFaqService.selectCmsFaqList(new CmsFaq());
        // 将图标转换为绝对URL
        for (CmsFaq faq : list) {
            faq.setFaqIcon(buildIconUrl(faq.getFaqIcon()));
        }
        return AjaxResult.success(list);
    }

    /**
     * 接口3：删除常见问题
     */
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Long id) {
        int rows = cmsFaqService.deleteCmsFaqById(id);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error("删除失败");
    }

    /**
     * 将存储的资源路径转换为绝对URL
     */
    private String buildIconUrl(String icon) {
        if (icon == null || icon.trim().isEmpty()) {
            return icon;
        }
        String v = icon.trim();
        if (v.startsWith("http://") || v.startsWith("https://")) {
            return v;
        }
        // 确保 domain 末尾无重复斜杠
        String prefix = domain != null ? domain.replaceAll("/+$", "") : "";
        return prefix + v;
    }
}