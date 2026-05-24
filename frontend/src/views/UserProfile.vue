<template>
  <div class="max-w-3xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6 dark:text-white">个人中心</h1>

    <div class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border">
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input :value="form.username" disabled />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="form.major" />
        </el-form-item>
        <el-form-item label="学历">
          <el-select v-model="form.education" class="w-full">
            <el-option label="本科" value="本科" />
            <el-option label="硕士" value="硕士" />
            <el-option label="博士" value="博士" />
          </el-select>
        </el-form-item>
        <el-form-item label="学校">
          <el-input v-model="form.university" />
        </el-form-item>
        <el-form-item label="毕业年份">
          <el-input-number v-model="form.graduationYear" :min="2020" :max="2030" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSave">保存修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import request from '@/api/request'

const userStore = useUserStore()

const form = reactive({
  username: '',
  nickname: '',
  email: '',
  major: '',
  education: '',
  university: '',
  graduationYear: 2025,
})

onMounted(() => {
  if (userStore.userInfo) {
    Object.assign(form, userStore.userInfo)
  }
})

async function handleSave() {
  try {
    await request.put('/users/me', form)
    userStore.updateUserInfo(form)
    ElMessage.success('保存成功')
  } catch (e) {}
}
</script>
