import request from "@/utils/request";

// 查询工单管理列表
export function listWork(query) {
  return request({
    url: "/system/work/list",
    method: "get",
    params: query,
  });
}

// 查询工单管理详细
export function getWork(id) {
  return request({
    url: "/system/work/" + id,
    method: "get",
  });
}

// 新增工单管理
export function addWork(data) {
  return request({
    url: "/system/work",
    method: "post",
    data: data,
  });
}

// 修改工单管理
export function updateWork(data) {
  return request({
    url: "/system/work",
    method: "put",
    data: data,
  });
}

// 删除工单管理
export function delWork(id) {
  return request({
    url: "/system/work/" + id,
    method: "delete",
  });
}

// 处理工单
export function feedWork(ids, handleResult) {
  return request({
    url: "/system/work/feedWork/" + ids,
    method: "put",
    params: {
      handleResult: handleResult,
    },
  });
}
