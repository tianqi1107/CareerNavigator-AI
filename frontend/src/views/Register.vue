<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50 dark:bg-dark-bg px-4 py-8">
    <div class="w-full max-w-md">
      <div class="text-center mb-8">
        <h1 class="text-2xl font-bold dark:text-white">注册账号</h1>
        <p class="text-gray-500 dark:text-gray-400 mt-2">开启你的AI职业规划之旅</p>
      </div>

      <div class="bg-white dark:bg-dark-card rounded-2xl p-8 border border-gray-200 dark:border-dark-border shadow-sm">
        <el-form ref="formRef" :model="form" :rules="rules" @submit.prevent="handleRegister">
          <el-form-item prop="username">
            <el-input v-model="form.username" size="large" placeholder="用户名 (3-50个字符)" prefix-icon="User" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" size="large" type="password" placeholder="密码 (至少6位)" prefix-icon="Lock" show-password />
          </el-form-item>
          <el-form-item prop="email">
            <el-input v-model="form.email" size="large" placeholder="邮箱 (可选)" prefix-icon="Message" />
          </el-form-item>
          <el-form-item>
            <el-input v-model="form.nickname" size="large" placeholder="昵称 (可选)" />
          </el-form-item>
          <el-row :gutter="12">
            <el-col :span="12">
              <el-form-item>
                <el-input v-model="form.major" size="large" placeholder="专业 (可选)" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item>
                <el-select v-model="form.education" size="large" placeholder="学历" class="w-full">
                  <el-option label="本科" value="本科" />
                  <el-option label="硕士" value="硕士" />
                  <el-option label="博士" value="博士" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-button type="primary" size="large" :loading="loading" class="w-full" @click="handleRegister">
            注册
          </el-button>
        </el-form>

        <div class="text-center mt-4 text-sm text-gray-500 dark:text-gray-400">
          已有账号？
          <router-link to="/login" class="text-blue-500 hover:text-blue-600">立即登录</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  username: '', password: '', email: '', nickname: '', major: '', education: ''
})
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 50, message: '3-50个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '至少6位', trigger: 'blur' },
  ],
}

async function handleRegister() {
  await formRef.value?.validate()
  loading.value = true
  try {
    await userStore.register(form)
    ElMessage.success('注册成功')
    router.push('/')
  } catch (e) {} finally { loading.value = false }
}
</script>
