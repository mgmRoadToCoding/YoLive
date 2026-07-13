import request from '@/utils/request'

// 查询消息管理列表
export function listMsg(query) {
  return request({
    url: '/system/msg/list',
    method: 'get',
    params: query
  })
}

// 查询消息管理详细
export function getMsg(id) {
  return request({
    url: '/system/msg/' + id,
    method: 'get'
  })
}

// 新增消息管理
export function addMsg(data) {
  return request({
    url: '/system/msg',
    method: 'post',
    data: data
  })
}

// 修改消息管理
export function updateMsg(data) {
  return request({
    url: '/system/msg',
    method: 'put',
    data: data
  })
}

// 删除消息管理
export function delMsg(id) {
  return request({
    url: '/system/msg/' + id,
    method: 'delete'
  })
}
