import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('@/components/layout/AppLayout.vue'),
    children: [
      { path: '', name: 'Home', component: () => import('@/views/Home.vue') },
      { path: 'majors', name: 'MajorSearch', component: () => import('@/views/MajorSearch.vue') },
      { path: 'jobs', name: 'JobList', component: () => import('@/views/JobList.vue') },
      { path: 'jobs/:id', name: 'JobDetail', component: () => import('@/views/JobDetail.vue') },
      { path: 'ai/career', name: 'AiCareer', component: () => import('@/views/AiCareer.vue'), meta: { requiresAuth: true } },
      { path: 'ai/chat', name: 'AiChat', component: () => import('@/views/AiChat.vue'), meta: { requiresAuth: true } },
      { path: 'dashboard', name: 'Dashboard', component: () => import('@/views/Dashboard.vue') },
      { path: 'profile', name: 'UserProfile', component: () => import('@/views/UserProfile.vue'), meta: { requiresAuth: true } },
    ],
  },
  {
    path: '/admin',
    component: () => import('@/components/layout/AppLayout.vue'),
    meta: { requiresAuth: true, requiresAdmin: true },
    children: [
      { path: '', name: 'AdminDashboard', component: () => import('@/views/admin/AdminDashboard.vue') },
      { path: 'users', name: 'UserManage', component: () => import('@/views/admin/UserManage.vue') },
      { path: 'majors', name: 'MajorManage', component: () => import('@/views/admin/MajorManage.vue') },
      { path: 'jobs', name: 'JobManage', component: () => import('@/views/admin/JobManage.vue') },
    ],
  },
  { path: '/login', name: 'Login', component: () => import('@/views/Login.vue') },
  { path: '/register', name: 'Register', component: () => import('@/views/Register.vue') },
  { path: '/:pathMatch(.*)*', name: 'NotFound', component: () => import('@/views/NotFound.vue') },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior: () => ({ top: 0 }),
})

// Navigation guard
router.beforeEach((to, _from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next({ name: 'Login', query: { redirect: to.fullPath } })
  } else if (to.meta.requiresAdmin) {
    const user = JSON.parse(localStorage.getItem('user') || '{}')
    if (user.role !== 'ADMIN') {
      next({ name: 'Home' })
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
