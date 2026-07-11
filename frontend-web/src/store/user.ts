import { defineStore } from 'pinia'
import { ref } from 'vue'
import request from '@/utils/request'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const roles = ref<string[]>([])
  const perms = ref<string[]>([])
  const userInfo = ref<any>(null)
  const menus = ref<any[]>([])

  async function login(loginData: { username: string; password: string; captcha?: string; captchaKey?: string }) {
    const res: any = await request.post('/auth/login', loginData)
    token.value = res.data.token
    roles.value = res.data.roles || []
    perms.value = res.data.perms || []
    menus.value = res.data.menus || []
    localStorage.setItem('token', res.data.token)
    return res
  }

  async function getInfo() {
    const res: any = await request.get('/auth/info')
    userInfo.value = res.data.user
    roles.value = res.data.roles || []
    perms.value = res.data.perms || []
    return res
  }

  function logout() {
    request.post('/auth/logout').catch(() => {})
    token.value = ''
    roles.value = []
    perms.value = []
    userInfo.value = null
    menus.value = []
    localStorage.removeItem('token')
  }

  function hasPerm(perm: string): boolean {
    if (roles.value.includes('admin')) return true
    return perms.value.includes(perm)
  }

  return { token, roles, perms, userInfo, menus, login, getInfo, logout, hasPerm }
})
