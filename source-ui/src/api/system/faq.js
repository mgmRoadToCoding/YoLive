import request from '@/utils/request'

// 查询常见问题列表（按排序字段返回所有）
export function listFaq(query) {
  return request({
    url: '/system/faq/list',
    method: 'get',
    params: query
  })
}

// 新增常见问题（multipart/form-data 上传 icon 文件；或传 iconId）
export function addFaq(form) {
  const fd = new FormData()
  if (form && typeof form === 'object') {
    if (form.faqName !== undefined) fd.append('faqName', form.faqName)
    if (form.faqLink !== undefined) fd.append('faqLink', form.faqLink)
    if (form.sort !== undefined && form.sort !== null) fd.append('sort', form.sort)

    // 优先使用文件字段
    if (form.iconFile instanceof File) {
      fd.append('icon', form.iconFile)
    } else if (form.icon instanceof File) {
      fd.append('icon', form.icon)
    }

    // 兼容后端支持 iconId（已上传文件ID）
    if (form.iconId !== undefined && form.iconId !== null && form.iconId !== '') {
      fd.append('iconId', form.iconId)
    }
  }

  return request({
    url: '/system/faq/create',
    method: 'post',
    data: fd,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

// 删除常见问题
export function delFaq(id) {
  return request({
    url: '/system/faq/' + id,
    method: 'delete'
  })
}
