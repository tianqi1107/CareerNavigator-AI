<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6 dark:text-white">岗位管理</h1>
    <div class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border">
      <el-input v-model="keyword" placeholder="搜索岗位..." class="mb-4" @keyup.enter="loadData" />
      <el-table :data="jobs" stripe>
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="jobName" label="岗位名称" />
        <el-table-column prop="companyName" label="公司" />
        <el-table-column prop="city" label="城市" width="80" />
        <el-table-column label="薪资" width="120">
          <template #default="{ row }">{{ row.salaryMin }}-{{ row.salaryMax }}万</template>
        </el-table-column>
        <el-table-column prop="educationReq" label="学历" width="80" />
        <el-table-column prop="hotScore" label="热度" width="80" />
      </el-table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { searchJobs } from '@/api/job'

const keyword = ref('')
const jobs = ref([])

async function loadData() {
  try {
    const res = await searchJobs({ keyword: keyword.value, page: 1, size: 50 })
    jobs.value = res.data?.records || []
  } catch (e) {}
}

onMounted(() => loadData())
</script>
