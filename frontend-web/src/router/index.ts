import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import NProgress from 'nprogress'
import { useUserStore } from '@/store/user'

const Layout = () => import('@/layout/index.vue')

export const constantRoutes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { hidden: true },
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '工作台', icon: 'Odometer' },
      },
    ],
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/user',
    meta: { title: '系统管理', icon: 'Setting' },
    children: [
      {
        path: 'user',
        name: 'SysUser',
        component: () => import('@/views/system/user.vue'),
        meta: { title: '用户管理', icon: 'User' },
      },
      {
        path: 'role',
        name: 'SysRole',
        component: () => import('@/views/system/role.vue'),
        meta: { title: '角色管理', icon: 'UserFilled' },
      },
      {
        path: 'menu',
        name: 'SysMenu',
        component: () => import('@/views/system/menu.vue'),
        meta: { title: '菜单管理', icon: 'Menu' },
      },
      {
        path: 'dept',
        name: 'SysDept',
        component: () => import('@/views/system/dept.vue'),
        meta: { title: '部门管理', icon: 'OfficeBuilding' },
      },
      {
        path: 'dict',
        name: 'SysDict',
        component: () => import('@/views/system/dict.vue'),
        meta: { title: '字典管理', icon: 'Collection' },
      },
    ],
  },
  {
    path: '/nursery',
    component: Layout,
    redirect: '/nursery/org',
    meta: { title: '托育管理', icon: 'School' },
    children: [
      {
        path: 'org',
        name: 'NurOrg',
        component: () => import('@/views/nursery/org.vue'),
        meta: { title: '机构管理', icon: 'OfficeBuilding' },
      },
      {
        path: 'class',
        name: 'NurClass',
        component: () => import('@/views/nursery/class.vue'),
        meta: { title: '班级管理', icon: 'Grid' },
      },
      {
        path: 'student',
        name: 'NurStudent',
        component: () => import('@/views/nursery/student.vue'),
        meta: { title: '儿童管理', icon: 'User' },
      },
      {
        path: 'log',
        name: 'NurLog',
        component: () => import('@/views/nursery/log.vue'),
        meta: { title: '保育日志', icon: 'Document' },
      },
    ],
  },
  {
    path: '/recruit',
    component: Layout,
    redirect: '/recruit/lead',
    meta: { title: '招生管理', icon: 'Promotion' },
    children: [
      {
        path: 'lead',
        name: 'RecruitLead',
        component: () => import('@/views/nursery/recruitment.vue'),
        meta: { title: '招生线索', icon: 'Pointer' },
      },
    ],
  },
  {
    path: '/finance',
    component: Layout,
    redirect: '/finance/item',
    meta: { title: '收费管理', icon: 'Money' },
    children: [
      {
        path: 'item',
        name: 'FeeItem',
        component: () => import('@/views/nursery/fee-item.vue'),
        meta: { title: '收费项目', icon: 'Wallet' },
      },
    ],
  },
  {
    path: '/health',
    component: Layout,
    redirect: '/health/record',
    meta: { title: '健康档案', icon: 'FirstAidKit' },
    children: [
      {
        path: 'record',
        name: 'HealthRecord',
        component: () => import('@/views/nursery/health.vue'),
        meta: { title: '健康档案', icon: 'FirstAidKit' },
      },
    ],
  },
  {
    path: '/attendance',
    component: Layout,
    redirect: '/attendance/record',
    meta: { title: '出勤管理', icon: 'Calendar' },
    children: [
      {
        path: 'record',
        name: 'AttendanceRecord',
        component: () => import('@/views/nursery/attendance.vue'),
        meta: { title: '考勤记录', icon: 'Calendar' },
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes,
})

const whiteList = ['/login']

router.beforeEach(async (to, from, next) => {
  NProgress.start()
  document.title = (to.meta?.title as string) || '智慧托育综合应用平台'
  const userStore = useUserStore()

  if (userStore.token) {
    if (to.path === '/login') {
      next('/')
    } else {
      if (!userStore.userInfo) {
        try {
          await userStore.getInfo()
        } catch {
          userStore.logout()
          next('/login')
          return
        }
      }
      next()
    }
  } else {
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next('/login')
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})

export default router
