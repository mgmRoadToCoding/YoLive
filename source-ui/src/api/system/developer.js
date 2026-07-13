import request from '@/utils/request'

// 查询成员管理列表
export function listDeveloper(query) {
  return request({
    url: '/system/developer/list',
    method: 'get',
    params: query
  })
}

// 查询成员管理详细
export function getDeveloper(id) {
  return request({
    url: '/system/developer/' + id,
    method: 'get'
  })
}

// 新增成员管理
export function addDeveloper(data) {
  return request({
    url: '/system/developer',
    method: 'post',
    data: data
  })
}

// 修改成员管理
export function updateDeveloper(data) {
  return request({
    url: '/system/developer',
    method: 'put',
    data: data
  })
}

// 删除成员管理
export function delDeveloper(id) {
  return request({
    url: '/system/developer/' + id,
    method: 'delete'
  })
}
