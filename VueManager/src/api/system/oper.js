import request from '@/utils/request'

// 常量
const api_name = '/admin/system/sysOperLog/'

export default {
  // 列表
  getPageList(page, limit, searchObj) {
    return request({
      // 接口路径
      // url: `${api_name}/${page}/${limit}`,
      url: api_name + page + '/' + limit,
      method: 'get', // 提交方式
      // 参数
      params: searchObj
    })
  }
}
