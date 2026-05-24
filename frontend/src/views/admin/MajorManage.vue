<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6 dark:text-white">专业管理</h1>
    <div class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border">
      <el-input v-model="keyword" placeholder="搜索专业..." class="mb-4" @keyup.enter="loadData" />
      <el-table :data="majors" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="majorCode" label="专业代码" width="100" />
        <el-table-column prop="majorName" label="专业名称" />
        <el-table-column prop="category" label="学科门类" />
        <el-table-column prop="employmentRate" label="就业率" width="80" />
        <el-table-column prop="avgSalary" label="均薪(万)" width="80" />
        <el-table-column prop="hotScore" label="热度" width="80" />
      </el-table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { searchMajors } from '@/api/major'

const keyword = ref('')
const majors = ref([])

async function loadData() {
  try {
    const res = await searchMajors({ keyword: keyword.value, page: 1, size: 50 })
    majors.value = res.data?.records || []
  } catch (e) {}
}

onMounted(() => loadData())
</script>
