<template>
  <div class="max-w-4xl mx-auto px-4 py-8">
    <div v-loading="loading">
      <template v-if="job">
        <!-- Header -->
        <div class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border mb-6">
          <div class="flex items-start justify-between">
            <div>
              <h1 class="text-2xl font-bold dark:text-white">{{ job.jobName }}</h1>
              <p class="text-gray-500 dark:text-gray-400 mt-2">
                {{ job.companyName }} · {{ job.city }}, {{ job.province }}
              </p>
            </div>
            <div class="text-right">
              <div class="text-2xl font-bold text-orange-500">
                {{ job.salaryMin }}-{{ job.salaryMax }}万/年
              </div>
              <div class="flex space-x-2 mt-2 justify-end">
                <el-tag v-if="job.isStateOwned" type="primary">国企/央企</el-tag>
                <el-tag v-if="job.isCivilService" type="success">公务员</el-tag>
                <el-tag v-if="job.categoryName">{{ job.categoryName }}</el-tag>
              </div>
            </div>
          </div>
        </div>

        <!-- Details -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div class="md:col-span-2 space-y-6">
            <div class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border">
              <h2 class="text-lg font-semibold mb-4 dark:text-white">岗位描述</h2>
              <p class="text-gray-600 dark:text-gray-300 whitespace-pre-line">{{ job.description }}</p>
            </div>
            <div v-if="job.requirements" class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border">
              <h2 class="text-lg font-semibold mb-4 dark:text-white">任职要求</h2>
              <p class="text-gray-600 dark:text-gray-300 whitespace-pre-line">{{ job.requirements }}</p>
            </div>
            <div v-if="job.benefits" class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border">
              <h2 class="text-lg font-semibold mb-4 dark:text-white">福利待遇</h2>
              <p class="text-gray-600 dark:text-gray-300">{{ job.benefits }}</p>
            </div>
          </div>

          <!-- Sidebar -->
          <div class="space-y-4">
            <div class="bg-white dark:bg-dark-card rounded-xl p-5 border border-gray-200 dark:border-dark-border">
              <h3 class="font-semibold mb-3 dark:text-white">基本信息</h3>
              <div class="space-y-2 text-sm">
                <div class="flex justify-between">
                  <span class="text-gray-500">学历要求</span>
                  <span class="dark:text-white">{{ job.educationReq || '不限' }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">经验要求</span>
                  <span class="dark:text-white">{{ job.experienceReq || '不限' }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">企业类型</span>
                  <span class="dark:text-white">{{ job.companyType || '-' }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-500">浏览次数</span>
                  <span class="dark:text-white">{{ job.viewCount || 0 }}</span>
                </div>
              </div>
            </div>

            <div v-if="job.skillsRequired?.length" class="bg-white dark:bg-dark-card rounded-xl p-5 border border-gray-200 dark:border-dark-border">
              <h3 class="font-semibold mb-3 dark:text-white">技能要求</h3>
              <div class="flex flex-wrap gap-2">
                <el-tag v-for="skill in job.skillsRequired" :key="skill" size="small">{{ skill }}</el-tag>
              </div>
            </div>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getJobDetail } from '@/api/job'

const route = useRoute()
const job = ref<any>(null)
const loading = ref(false)

onMounted(async () => {
  loading.value = true
  try {
    const res = await getJobDetail(Number(route.params.id))
    job.value = res.data
  } catch (e) {} finally {
    loading.value = false
  }
})
</script>
