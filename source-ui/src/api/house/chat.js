import request from '@/utils/request'
export function sessions(query) {
  return request({
    url: '/chat/admin/sessions',
    method: 'get',
    params: query
  })
}
// 根据会话ID查询消息列表
export function getMessages(sessionId) {
  return request({
    url: "/chat/admin/messages/" + sessionId,
    method: "get",
  });
}
