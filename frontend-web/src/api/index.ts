import request from '@/utils/request'

// ======== 认证 ========
export const authApi = {
  login: (data: any) => request.post('/auth/login', data),
  getInfo: () => request.get('/auth/info'),
  getCaptcha: () => request.get('/auth/captcha'),
  logout: () => request.post('/auth/logout'),
}

// ======== 系统管理 ========
export const userApi = {
  list: (params: any) => request.get('/system/user/list', { params }),
  add: (data: any) => request.post('/system/user', data),
  edit: (data: any) => request.put('/system/user', data),
  remove: (userId: number) => request.delete(`/system/user/${userId}`),
  resetPwd: (userId: number, newPassword: string) => request.put('/system/user/resetPwd', null, { params: { userId, newPassword } }),
}

export const roleApi = {
  list: (keyword?: string) => request.get('/system/role/list', { params: { keyword } }),
  add: (data: any) => request.post('/system/role', data),
  edit: (data: any) => request.put('/system/role', data),
  remove: (roleId: number) => request.delete(`/system/role/${roleId}`),
}

export const menuApi = {
  list: (keyword?: string) => request.get('/system/menu/list', { params: { keyword } }),
  add: (data: any) => request.post('/system/menu', data),
  edit: (data: any) => request.put('/system/menu', data),
  remove: (menuId: number) => request.delete(`/system/menu/${menuId}`),
}

export const deptApi = {
  list: (keyword?: string) => request.get('/system/dept/list', { params: { keyword } }),
  add: (data: any) => request.post('/system/dept', data),
  edit: (data: any) => request.put('/system/dept', data),
  remove: (deptId: number) => request.delete(`/system/dept/${deptId}`),
}

export const dictApi = {
  list: (keyword?: string) => request.get('/system/dict/list', { params: { keyword } }),
  add: (data: any) => request.post('/system/dict', data),
  edit: (data: any) => request.put('/system/dict', data),
  remove: (dictId: number) => request.delete(`/system/dict/${dictId}`),
  getDictData: (dictType: string) => request.get(`/system/dict/data/${dictType}`),
}

// ======== 托育业务 ========
export const orgApi = {
  list: (params: any) => request.get('/nursery/org/list', { params }),
  add: (data: any) => request.post('/nursery/org', data),
  edit: (data: any) => request.put('/nursery/org', data),
  remove: (id: number) => request.delete(`/nursery/org/${id}`),
  all: () => request.get('/nursery/org/all'),
}

export const classApi = {
  list: (params: any) => request.get('/nursery/class/list', { params }),
  add: (data: any) => request.post('/nursery/class', data),
  edit: (data: any) => request.put('/nursery/class', data),
  remove: (id: number) => request.delete(`/nursery/class/${id}`),
}

export const studentApi = {
  list: (params: any) => request.get('/nursery/student/list', { params }),
  add: (data: any) => request.post('/nursery/student', data),
  edit: (data: any) => request.put('/nursery/student', data),
  remove: (id: number) => request.delete(`/nursery/student/${id}`),
}

export const attendanceApi = {
  list: (params: any) => request.get('/nursery/attendance/list', { params }),
  add: (data: any) => request.post('/nursery/attendance', data),
  edit: (data: any) => request.put('/nursery/attendance', data),
  remove: (id: number) => request.delete(`/nursery/attendance/${id}`),
}

export const nurseryLogApi = {
  list: (params: any) => request.get('/nursery/log/list', { params }),
  add: (data: any) => request.post('/nursery/log', data),
  edit: (data: any) => request.put('/nursery/log', data),
  remove: (id: number) => request.delete(`/nursery/log/${id}`),
}

export const recruitmentApi = {
  list: (params: any) => request.get('/nursery/recruitment/list', { params }),
  add: (data: any) => request.post('/nursery/recruitment', data),
  edit: (data: any) => request.put('/nursery/recruitment', data),
  remove: (id: number) => request.delete(`/nursery/recruitment/${id}`),
}

export const feeItemApi = {
  list: (params: any) => request.get('/nursery/fee-item/list', { params }),
  add: (data: any) => request.post('/nursery/fee-item', data),
  edit: (data: any) => request.put('/nursery/fee-item', data),
  remove: (id: number) => request.delete(`/nursery/fee-item/${id}`),
}

export const healthApi = {
  list: (params: any) => request.get('/nursery/health/list', { params }),
  add: (data: any) => request.post('/nursery/health', data),
  edit: (data: any) => request.put('/nursery/health', data),
  remove: (id: number) => request.delete(`/nursery/health/${id}`),
}

// ======== 文件上传 ========
export const fileApi = {
  upload: (file: File) => {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/file/upload', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })
  },
}
