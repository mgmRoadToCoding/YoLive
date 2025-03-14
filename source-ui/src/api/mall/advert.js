import request from '@/utils/request'

// 查询广告管理列表
export function listAdvert(query) {
  return request({
    url: '/system/advert/list',
    method: 'get',
    params: query
  })
}

// 查询广告管理详细
export function getAdvert(id) {
  return request({
    url: '/system/advert/' + id,
    method: 'get'
  })
}

// 新增广告管理
export function addAdvert(data) {
  return request({
    url: '/system/advert',
    method: 'post',
    data: data
  })
}

// 修改广告管理
export function updateAdvert(data) {
  return request({
    url: '/system/advert',
    method: 'put',
    data: data
  })
}

// 删除广告管理
export function delAdvert(id) {
  return request({
    url: '/system/advert/' + id,
    method: 'delete'
  })
}
