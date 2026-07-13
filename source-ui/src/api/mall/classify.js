import request from '@/utils/request'

// 查询分类管理列表
export function listClassify(query) {
  return request({
    url: '/system/classify/list',
    method: 'get',
    params: query
  })
}

// 查询分类管理详细
export function getClassify(id) {
  return request({
    url: '/system/classify/' + id,
    method: 'get'
  })
}

// 新增分类管理
export function addClassify(data) {
  return request({
    url: '/system/classify',
    method: 'post',
    data: data
  })
}

// 修改分类管理
export function updateClassify(data) {
  return request({
    url: '/system/classify',
    method: 'put',
    data: data
  })
}

// 删除分类管理
export function delClassify(id) {
  return request({
    url: '/system/classify/' + id,
    method: 'delete'
  })
}
