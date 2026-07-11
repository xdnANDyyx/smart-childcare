import http from '@/utils/request'

// 认证
export const authApi = {
  login: (data) => http.post('/auth/login', data),
  getInfo: () => http.get('/auth/info'),
  getCaptcha: () => http.get('/auth/captcha'),
  logout: () => http.post('/auth/logout'),
}

// 儿童
export const studentApi = {
  list: (params) => http.get('/nursery/student/list', params),
}

// 出勤
export const attendanceApi = {
  list: (params) => http.get('/nursery/attendance/list', params),
}

// 保育日志
export const nurseryLogApi = {
  list: (params) => http.get('/nursery/log/list', params),
  add: (data) => http.post('/nursery/log', data),
}

// 通知
export const notificationApi = {
  list: (params) => http.get('/system/dict/data/nur_notification_type'),
}
