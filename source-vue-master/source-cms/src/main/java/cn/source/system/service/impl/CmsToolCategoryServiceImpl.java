package cn.source.system.service.impl;

import java.util.List;
import java.util.Objects;

import cn.source.common.exception.ServiceException;
import cn.source.common.utils.DateUtils;
import cn.source.system.domain.CmsTool;
import cn.source.system.domain.vo.CmsCategoryToolVo;
import cn.source.system.domain.vo.CmsToolVo;
import cn.source.system.service.ICmsToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import cn.source.system.mapper.CmsToolCategoryMapper;
import cn.source.system.domain.CmsToolCategory;
import cn.source.system.service.ICmsToolCategoryService;

/**
 * 工具分类Service业务层处理
 *
 * @author sourcebyte.vip
 * @date 2024-12-29
 */
@Service
public class CmsToolCategoryServiceImpl implements ICmsToolCategoryService {
    @Autowired
    private CmsToolCategoryMapper cmsToolCategoryMapper;

    @Autowired
    private ICmsToolService cmsToolService;

    /**
     * 查询工具分类
     *
     * @param id 工具分类主键
     * @return 工具分类
     */
    @Override
    public CmsToolCategory selectCmsToolCategoryById(Long id) {
        return cmsToolCategoryMapper.selectCmsToolCategoryById(id);
    }

    /**
     * 查询工具分类列表
     *
     * @param cmsToolCategory 工具分类
     * @return 工具分类
     */
    @Override
    public List<CmsToolCategory> selectCmsToolCategoryList(CmsToolCategory cmsToolCategory) {
        return cmsToolCategoryMapper.selectCmsToolCategoryList(cmsToolCategory);
    }

    /**
     * 新增工具分类
     *
     * @param cmsToolCategory 工具分类
     * @return 结果
     */
    @Override
    public int insertCmsToolCategory(CmsToolCategory cmsToolCategory) {
        CmsToolCategory localCategory = new CmsToolCategory();
        localCategory.setName(cmsToolCategory.getName());
        _validateCategoryProperty(localCategory, null);
        cmsToolCategory.setCreateTime(DateUtils.getNowDate());
        return cmsToolCategoryMapper.insertCmsToolCategory(cmsToolCategory);
    }

    /**
     * 修改工具分类
     *
     * @param cmsToolCategory 工具分类
     * @return 结果
     */
    @Override
    public int updateCmsToolCategory(CmsToolCategory cmsToolCategory) {
        CmsToolCategory localCategory = new CmsToolCategory();
        localCategory.setName(cmsToolCategory.getName());
        _validateCategoryProperty(localCategory, cmsToolCategory.getId());
        cmsToolCategory.setUpdateTime(DateUtils.getNowDate());
        return cmsToolCategoryMapper.updateCmsToolCategory(cmsToolCategory);
    }

    private void _validateCategoryProperty(CmsToolCategory localCategory, Long id) {
        // 根据code查询数据信息
        List<CmsToolCategory> cmsToolCategories = selectCmsToolCategoryList(localCategory);
        // 新增
        if (id == null) {
            if (!cmsToolCategories.isEmpty()) {
                throw new ServiceException("该分类名称已经存在");
            }
        } else {
            // 取第一个分类是否与当前分类一致
            if (!cmsToolCategories.isEmpty()) {
                CmsToolCategory firstCategory = cmsToolCategories.get(0);
                if (null != firstCategory && !Objects.equals(firstCategory.getId(), id)) {
                    throw new ServiceException("该分类已经存在");
                }
            }
        }

    }

    /**
     * 批量删除工具分类
     *
     * @param ids 需要删除的工具分类主键
     * @return 结果
     */
    @Override
    public int deleteCmsToolCategoryByIds(Long[] ids) {
        // 判断其下是否还存在数据
        for (Long id : ids) {
            _validateCategory(id);
        }
        return cmsToolCategoryMapper.deleteCmsToolCategoryByIds(ids);
    }

    /**
     * 删除工具分类信息
     *
     * @param id 工具分类主键
     * @return 结果
     */
    @Override
    public int deleteCmsToolCategoryById(Long id) {
        _validateCategory(id);
        return cmsToolCategoryMapper.deleteCmsToolCategoryById(id);
    }

    /**
     * 校验工具分类下是否还存在工具数据
     */
    private void _validateCategory(Long id) {
        CmsTool cmsTool = new CmsTool();
        cmsTool.setCatId(id);
        List<CmsToolVo> cmsToolList = cmsToolService.selectCmsToolList(cmsTool);
        if (!cmsToolList.isEmpty()) {
            throw new ServiceException("该工具分类下还存在工具，请先移除工具");
        }
    }

    @Override
    public List<CmsCategoryToolVo> selectCategoryToolList(CmsTool cmsTool) {
        return cmsToolCategoryMapper.selectCategoryToolList(cmsTool);
    }
}
