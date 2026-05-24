<template>
  <header class="fixed top-0 left-0 right-0 z-50 glass border-b border-gray-200 dark:border-dark-border">
    <div class="max-w-7xl mx-auto px-4 h-16 flex items-center justify-between">
      <!-- Logo -->
      <router-link to="/" class="flex items-center space-x-2">
        <div class="w-8 h-8 rounded-lg bg-gradient-to-br from-blue-500 to-purple-600 flex items-center justify-center">
          <span class="text-white font-bold text-sm">CN</span>
        </div>
        <span class="text-lg font-bold gradient-text hidden sm:inline">CareerNavigator AI</span>
      </router-link>

      <!-- Navigation -->
      <nav class="hidden md:flex items-center space-x-1">
        <router-link
          v-for="item in navItems"
          :key="item.path"
          :to="item.path"
          class="px-3 py-2 rounded-lg text-sm font-medium transition-colors
                 hover:bg-gray-100 dark:hover:bg-dark-card
                 text-gray-600 dark:text-gray-300"
          active-class="!bg-blue-500/10 !text-blue-600 dark:!text-blue-400"
        >
          {{ item.name }}
        </router-link>
      </nav>

      <!-- Right section -->
      <div class="flex items-center space-x-3">
        <!-- Dark mode toggle -->
        <button
          @click="toggleDark"
          class="p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-dark-card transition-colors"
        >
          <el-icon :size="18">
            <Sunny v-if="isDark" />
            <Moon v-else />
          </el-icon>
        </button>

        <!-- User menu -->
        <template v-if="userStore.isLoggedIn">
          <el-dropdown trigger="click">
            <div class="flex items-center space-x-2 cursor-pointer px-2 py-1 rounded-lg hover:bg-gray-100 dark:hover:bg-dark-card">
              <el-avatar :size="32" :src="userStore.userInfo?.avatar">
                {{ userStore.userInfo?.nickname?.[0] || userStore.userInfo?.username?.[0] || 'U' }}
              </el-avatar>
              <span class="text-sm hidden sm:inline">{{ userStore.userInfo?.nickname || userStore.userInfo?.username }}</span>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="$router.push('/profile')">
                  <el-icon><User /></el-icon> 个人中心
                </el-dropdown-item>
                <el-dropdown-item @click="$router.push('/ai/career')">
                  <el-icon><DataAnalysis /></el-icon> AI职业规划
                </el-dropdown-item>
                <el-dropdown-item v-if="userStore.isAdmin" @click="$router.push('/admin')">
                  <el-icon><Setting /></el-icon> 管理后台
                </el-dropdown-item>
                <el-dropdown-item divided @click="handleLogout">
                  <el-icon><SwitchButton /></el-icon> 退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>
        <template v-else>
          <router-link to="/login">
            <el-button type="primary" size="small">登录</el-button>
          </router-link>
          <router-link to="/register" class="hidden sm:inline">
            <el-button size="small">注册</el-button>
          </router-link>
        </template>

        <!-- Mobile menu -->
        <el-dropdown trigger="click" class="md:hidden">
          <el-button :icon="Menu" circle size="small" />
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item v-for="item in navItems" :key="item.path" @click="$router.push(item.path)">
                {{ item.name }}
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { Sunny, Moon, User, Setting, SwitchButton, DataAnalysis, Menu as MenuIcon } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const Menu = MenuIcon
const router = useRouter()
const userStore = useUserStore()
const isDark = ref(true)

const navItems = [
  { name: '首页', path: '/' },
  { name: '专业查询', path: '/majors' },
  { name: '岗位列表', path: '/jobs' },
  { name: 'AI规划', path: '/ai/career' },
  { name: '数据看板', path: '/dashboard' },
]

onMounted(() => {
  isDark.value = document.documentElement.classList.contains('dark')
})

function toggleDark() {
  isDark.value = !isDark.value
  document.documentElement.classList.toggle('dark')
  localStorage.setItem('darkMode', String(isDark.value))
}

function handleLogout() {
  userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/')
}
</script>
