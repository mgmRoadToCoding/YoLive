import request from '@/utils/request'

// 查询导航管理列表
export function listNavigate(query) {
  return request({
    url: '/system/navigate/list',
    method: 'get',
    params: query
  })
}

// 查询导航管理详细
export function getNavigate(id) {
  return request({
    url: '/system/navigate/' + id,
    method: 'get'
  })
}

// 新增导航管理
export function addNavigate(data) {
  return request({
    url: '/system/navigate',
    method: 'post',
    data: data
  })
}

// 修改导航管理
export function updateNavigate(data) {
  return request({
    url: '/system/navigate',
    method: 'put',
    data: data
  })
}

// 删除导航管理
export function delNavigate(id) {
  return request({
    url: '/system/navigate/' + id,
    method: 'delete'
  })
}
