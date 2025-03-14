import request from "@/utils/request";

// 获取流量数据
export function initIndexFlow() {
  return request({
    url: "/api/flow/findFlow",
    headers: {
      isToken: false,
    },
    method: "get",
  });
}

// 获取图表数据
export function initEcharts() {
  return request({
    url: "/api/echarts/flowLine",
    headers: {
      isToken: false,
    },
    method: "get",
  });
}
