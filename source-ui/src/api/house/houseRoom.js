import request from "@/utils/request";

// 查询房源详情列表
export function listHouseRoom(query) {
  return request({
    url: "/system/houseRoom/list",
    method: "get",
    params: query,
  });
}

// 查询房源详情详细
export function getHouseRoom(id) {
  return request({
    url: "/system/houseRoom/" + id,
    method: "get",
  });
}

// 新增房源详情
export function addHouseRoom(data) {
  return request({
    url: "/system/houseRoom",
    method: "post",
    data: data,
  });
}

// 修改房源详情
export function updateHouseRoom(data) {
  return request({
    url: "/system/houseRoom",
    method: "put",
    data: data,
  });
}

// 审核
export function handleAudit(id) {
  return request({
    url: "/system/houseRoom/handleAudit/" + id,
    method: "put",
  });
}

// 出租
export function handlePush(id) {
  return request({
    url: "/system/houseRoom/handlePush/" + id,
    method: "put",
  });
}

// 下架
export function handleClose(id) {
  return request({
    url: "/system/houseRoom/handleClose/" + id,
    method: "put",
  });
}

// 上架
export function handleOpen(id) {
  return request({
    url: "/system/houseRoom/handleOpen/" + id,
    method: "put",
  });
}

// 删除房源详情
export function delHouseRoom(id) {
  return request({
    url: "/system/houseRoom/" + id,
    method: "delete",
  });
}

// 查询用户列表
export function userList(query) {
  return request({
    url: "/system/houseRoom/userList",
    method: "get",
    params: query,
  });
}

// 授权用户选择
export function authUser(ids, userId) {
  return request({
    url: "/system/houseRoom/authUser/" + ids,
    method: "put",
    params: {
      userId: userId,
    },
  });
}
