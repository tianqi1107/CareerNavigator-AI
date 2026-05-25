<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-2 dark:text-white">专业查岗位</h1>
    <p class="text-gray-500 dark:text-gray-400 mb-6">输入专业名称或专业代码，查询可报考的公务员、国企、央企岗位</p>

    <!-- Search -->
    <div class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border mb-6">
      <div class="flex flex-wrap gap-3 items-end">
        <div class="flex-1 min-w-[200px]">
          <label class="text-sm text-gray-500 dark:text-gray-400 mb-1 block">专业名称或代码</label>
          <el-input v-model="major" size="large" placeholder="如：计算机科学与技术 或 080901" @keyup.enter="doSearch">
            <template #prefix><el-icon><Search /></el-icon></template>
          </el-input>
        </div>
        <div>
          <label class="text-sm text-gray-500 dark:text-gray-400 mb-1 block">岗位类型</label>
          <el-select v-model="filterType" size="large" class="w-40">
            <el-option label="全部" value="" />
            <el-option label="公务员" value="civil_service" />
            <el-option label="国企/央企" value="soe" />
          </el-select>
        </div>
        <el-button type="primary" size="large" :loading="loading" @click="doSearch">
          查询可报考岗位
        </el-button>
      </div>

      <!-- Quick examples -->
      <div class="mt-4 flex flex-wrap gap-2">
        <span class="text-xs text-gray-400">快捷查询：</span>
        <el-button v-for="ex in examples" :key="ex" size="small" text @click="major = ex; doSearch()">
          {{ ex }}
        </el-button>
      </div>
    </div>

    <!-- Results -->
    <div v-loading="loading">
      <div v-if="jobs.length > 0" class="mb-4">
        <div class="flex items-center justify-between mb-4">
          <h2 class="text-lg font-semibold dark:text-white">
            共找到 <span class="text-blue-500">{{ jobs.length }}</span> 个匹配岗位
          </h2>
          <div class="flex gap-2">
            <el-tag :type="filterType === '' ? 'primary' : 'info'" size="small" @click="filterType = ''; doSearch()">全部</el-tag>
            <el-tag :type="filterType === 'civil_service' ? 'success' : 'info'" size="small" @click="filterType = 'civil_service'; doSearch()">公务员</el-tag>
            <el-tag :type="filterType === 'soe' ? 'warning' : 'info'" size="small" @click="filterType = 'soe'; doSearch()">国企/央企</el-tag>
          </div>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div
            v-for="job in jobs"
            :key="job.id"
            class="card-hover bg-white dark:bg-dark-card rounded-xl p-5 border border-gray-200 dark:border-dark-border cursor-pointer"
            @click="$router.push(`/jobs/${job.id}`)"
          >
            <div class="flex items-start justify-between">
              <div class="flex-1">
                <div class="flex items-center space-x-2 flex-wrap">
                  <h3 class="font-semibold dark:text-white">{{ job.jobName }}</h3>
                  <el-tag v-if="job.isCivilService" type="success" size="small">公务员</el-tag>
                  <el-tag v-else-if="job.isStateOwned" type="warning" size="small">国企/央企</el-tag>
                </div>
                <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">
                  {{ job.companyName }} · {{ job.city }}
                </p>
                <p v-if="job.description" class="text-xs text-gray-400 dark:text-gray-500 mt-2 line-clamp-2">
                  {{ job.description }}
                </p>
              </div>
              <div class="text-right ml-4">
                <div class="text-lg font-bold text-orange-500">
                  {{ job.salaryMin }}-{{ job.salaryMax }}万
                </div>
                <div class="text-xs text-gray-400">/年</div>
                <div class="text-xs text-gray-400 mt-1">{{ job.educationReq || '学历不限' }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-else-if="searched && !loading" class="bg-white dark:bg-dark-card rounded-xl p-12 border border-gray-200 dark:border-dark-border text-center">
        <el-icon :size="48" class="text-gray-300 dark:text-gray-600 mb-4"><Search /></el-icon>
        <p class="text-gray-400">未找到匹配的岗位</p>
        <p class="text-xs text-gray-400 mt-2">请尝试其他专业名称或代码</p>
      </div>

      <div v-else-if="!searched" class="bg-white dark:bg-dark-card rounded-xl p-12 border border-gray-200 dark:border-dark-border text-center">
        <el-icon :size="48" class="text-gray-300 dark:text-gray-600 mb-4"><Briefcase /></el-icon>
        <p class="text-gray-400">输入专业名称或代码，查询可报考的公务员和国企岗位</p>
        <p class="text-xs text-gray-400 mt-2">支持模糊搜索，如"计算机"、"电气"、"法学"等</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Search, Briefcase } from '@element-plus/icons-vue'
import { searchMajors } from '@/api/major'
import request from '@/api/request'

const major = ref('')
const filterType = ref('')
const loading = ref(false)
const searched = ref(false)
const jobs = ref<any[]>([])

const examples = ['计算机科学与技术', '电气工程', '法学', '会计学', '通信工程', '临床医学', '080901', '030101K']

async function doSearch() {
  if (!major.value.trim()) return
  loading.value = true
  searched.value = true
  try {
    const res = await request.get('/majors/search-jobs', {
      params: { major: major.value.trim(), type: filterType.value || undefined }
    })
    jobs.value = res.data || []
  } catch (e) {
    jobs.value = []
  } finally {
    loading.value = false
  }
}
</script>
