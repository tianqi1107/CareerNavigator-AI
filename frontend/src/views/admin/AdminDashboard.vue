<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6 dark:text-white">管理后台</h1>
    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
      <router-link v-for="item in menus" :key="item.path" :to="item.path"
        class="card-hover bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border">
        <el-icon :size="32" class="text-blue-500 mb-3"><component :is="item.icon" /></el-icon>
        <h3 class="font-semibold dark:text-white">{{ item.title }}</h3>
        <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">{{ item.desc }}</p>
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
const menus = [
  { path: '/admin/users', title: '用户管理', desc: '查看和管理用户账号', icon: 'User' },
  { path: '/admin/majors', title: '专业管理', desc: '维护专业数据', icon: 'School' },
  { path: '/admin/jobs', title: '岗位管理', desc: '维护岗位数据', icon: 'Briefcase' },
]
</script>
