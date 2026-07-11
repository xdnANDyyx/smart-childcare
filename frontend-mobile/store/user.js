import { defineStore } from 'pinia'
import http from '@/utils/request'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: uni.getStorageSync('token') || '',
    userInfo: uni.getStorageSync('userInfo') || null,
    role: uni.getStorageSync('role') || 'teacher', // teacher / parent / admin
  }),
  actions: {
    async login(data) {
      const res = await http.post('/auth/login', data)
      this.token = res.data.token
      uni.setStorageSync('token', res.data.token)
      return res
    },
    async getInfo() {
      const res = await http.get('/auth/info')
      this.userInfo = res.data.user
      uni.setStorageSync('userInfo', res.data.user)
      return res
    },
    logout() {
      http.post('/auth/logout').catch(() => {})
      this.token = ''
      this.userInfo = null
      uni.removeStorageSync('token')
      uni.removeStorageSync('userInfo')
    },
    setRole(role) {
      this.role = role
      uni.setStorageSync('role', role)
    }
  }
})
