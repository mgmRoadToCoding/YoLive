import request from '@/utils/request'

// 查询房源小区列表
export function listHouseVillage(query) {
  return request({
    url: '/system/houseVillage/list',
    method: 'get',
    params: query
  })
}

// 查询房源小区详细
export function getHouseVillage(id) {
  return request({
    url: '/system/houseVillage/' + id,
    method: 'get'
  })
}

// 新增房源小区
export function addHouseVillage(data) {
  return request({
    url: '/system/houseVillage',
    method: 'post',
    data: data
  })
}

// 修改房源小区
export function updateHouseVillage(data) {
  return request({
    url: '/system/houseVillage',
    method: 'put',
    data: data
  })
}

// 删除房源小区
export function delHouseVillage(id) {
  return request({
    url: '/system/houseVillage/' + id,
    method: 'delete'
  })
}
