import request from '@/utils/request'

// 管理端：查询帖子列表（支持条件过滤）
export function listAdminPosts(query) {
  return request({
    url: '/system/forum/posts',
    method: 'get',
    params: query
  })
}

// 管理端：待审核列表
export function listPendingPosts(query) {
  return request({
    url: '/system/forum/posts/pending',
    method: 'get',
    params: query
  })
}

// 管理端：审核通过
export function approvePost(id, reason) {
  const config = {
    url: `/system/forum/posts/${id}/approve`,
    method: 'put'
  }
  if (reason !== undefined && reason !== null && reason !== '') {
    config.params = { reason }
  }
  return request(config)
}

// 管理端：审核驳回
export function rejectPost(id, reason) {
  const config = {
    url: `/system/forum/posts/${id}/reject`,
    method: 'put'
  }
  if (reason !== undefined && reason !== null && reason !== '') {
    config.params = { reason }
  }
  return request(config)
}

// 管理端：删除帖子（逻辑删除）
export function deletePost(id) {
  return request({
    url: `/system/forum/posts/${id}`,
    method: 'delete'
  })
}

// 前台：帖子详情（如需在弹窗查看）
export function getPostDetail(id) {
  return request({
    url: `/api/forum/posts/${id}`,
    method: 'get'
  })
}