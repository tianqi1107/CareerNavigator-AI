<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50 dark:bg-dark-bg px-4">
    <div class="w-full max-w-md">
      <div class="text-center mb-8">
        <div class="w-16 h-16 rounded-2xl bg-gradient-to-br from-blue-500 to-purple-600 flex items-center justify-center mx-auto mb-4">
          <span class="text-white font-bold text-2xl">CN</span>
        </div>
        <h1 class="text-2xl font-bold dark:text-white">登录 CareerNavigator AI</h1>
        <p class="text-gray-500 dark:text-gray-400 mt-2">智能职业规划，从这里开始</p>
      </div>

      <div class="bg-white dark:bg-dark-card rounded-2xl p-8 border border-gray-200 dark:border-dark-border shadow-sm">
        <el-form ref="formRef" :model="form" :rules="rules" @submit.prevent="handleLogin">
          <el-form-item prop="username">
            <el-input v-model="form.username" size="large" placeholder="用户名" prefix-icon="User" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" size="large" type="password" placeholder="密码" prefix-icon="Lock" show-password @keyup.enter="handleLogin" />
          </el-form-item>
          <el-button type="primary" size="large" :loading="loading" class="w-full" @click="handleLogin">
            登录
          </el-button>
        </el-form>

        <div class="text-center mt-4 text-sm text-gray-500 dark:text-gray-400">
          还没有账号？
          <router-link to="/register" class="text-blue-500 hover:text-blue-600">立即注册</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const form = reactive({ username: '', password: '' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

async function handleLogin() {
  await formRef.value?.validate()
  loading.value = true
  try {
    await userStore.login(form.username, form.password)
    ElMessage.success('登录成功')
    const redirect = (route.query.redirect as string) || '/'
    router.push(redirect)
  } catch (e) {
    // Error handled by interceptor
  } finally {
    loading.value = false
  }
}
</script>
