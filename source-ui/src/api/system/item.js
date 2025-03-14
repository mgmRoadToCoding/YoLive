import request from "@/utils/request";

// 查询服务条目列表
export function listItem(query) {
  return request({
    url: "/system/item/list",
    method: "get",
    params: query,
  });
}

// 查询服务条目详细
export function getItem(id) {
  return request({
    url: "/system/item/" + id,
    method: "get",
  });
}

// 新增服务条目
export function addItem(data) {
  return request({
    url: "/system/item",
    method: "post",
    data: data,
  });
}

// 修改服务条目
export function updateItem(data) {
  return request({
    url: "/system/item",
    method: "put",
    data: data,
  });
}

// 删除服务条目
export function delItem(id) {
  return request({
    url: "/system/item/" + id,
    method: "delete",
  });
}
