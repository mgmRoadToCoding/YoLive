import request from '@/utils/request'

// 查询工具分类列表
export function listCategory(query) {
  return request({
    url: '/system/toolCategory/list',
    method: 'get',
    params: query
  })
}

// 查询工具分类详细
export function getCategory(id) {
  return request({
    url: '/system/toolCategory/' + id,
    method: 'get'
  })
}

// 新增工具分类
export function addCategory(data) {
  return request({
    url: '/system/toolCategory',
    method: 'post',
    data: data
  })
}

// 修改工具分类
export function updateCategory(data) {
  return request({
    url: '/system/toolCategory',
    method: 'put',
    data: data
  })
}

// 删除工具分类
export function delCategory(id) {
  return request({
    url: '/system/toolCategory/' + id,
    method: 'delete'
  })
}
