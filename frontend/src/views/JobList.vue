<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6 dark:text-white">岗位列表</h1>

    <!-- Filters -->
    <div class="bg-white dark:bg-dark-card rounded-xl p-4 border border-gray-200 dark:border-dark-border mb-6">
      <div class="flex flex-wrap gap-3">
        <el-input v-model="filters.keyword" placeholder="搜索岗位..." clearable class="w-60" @keyup.enter="doSearch">
          <template #prefix><el-icon><Search /></el-icon></template>
        </el-input>
        <el-select v-model="filters.companyType" placeholder="企业类型" clearable class="w-36">
          <el-option label="国企" value="国企" />
          <el-option label="央企" value="央企" />
          <el-option label="私企" value="私企" />
          <el-option label="外企" value="外企" />
          <el-option label="公务员" value="公务员" />
          <el-option label="事业单位" value="事业单位" />
        </el-select>
        <el-select v-model="filters.educationReq" placeholder="学历要求" clearable class="w-32">
          <el-option label="本科" value="本科" />
          <el-option label="硕士" value="硕士" />
          <el-option label="博士" value="博士" />
        </el-select>
        <el-input v-model="filters.city" placeholder="城市" clearable class="w-32" />
        <el-button type="primary" @click="doSearch">搜索</el-button>
      </div>
    </div>

    <!-- Job list -->
    <div v-loading="loading" class="space-y-4">
      <div
        v-for="job in jobs"
        :key="job.id"
        class="card-hover bg-white dark:bg-dark-card rounded-xl p-5 border border-gray-200 dark:border-dark-border cursor-pointer"
        @click="$router.push(`/jobs/${job.id}`)"
      >
        <div class="flex items-start justify-between">
          <div class="flex-1">
            <div class="flex items-center space-x-2">
              <h3 class="font-semibold text-lg dark:text-white">{{ job.jobName }}</h3>
              <el-tag v-if="job.isStateOwned" type="primary" size="small">国企/央企</el-tag>
              <el-tag v-else-if="job.isCivilService" type="success" size="small">公务员</el-tag>
              <el-tag v-else-if="job.companyType === '外企'" type="warning" size="small">外企</el-tag>
            </div>
            <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">
              {{ job.companyName }} · {{ job.city }} · {{ job.educationReq || '学历不限' }}
            </p>
          </div>
          <div class="text-right">
            <div class="text-lg font-bold text-orange-500">
              {{ job.salaryMin }}-{{ job.salaryMax }}万
            </div>
            <div class="text-xs text-gray-400">/年</div>
          </div>
        </div>
        <div v-if="job.skillsRequired?.length" class="flex flex-wrap gap-2 mt-3">
          <el-tag v-for="skill in job.skillsRequired.slice(0, 5)" :key="skill" size="small" type="info">
            {{ skill }}
          </el-tag>
        </div>
      </div>

      <div v-if="jobs.length === 0 && !loading" class="text-center py-12 text-gray-400">
        暂无岗位数据
      </div>
    </div>

    <!-- Pagination -->
    <div class="mt-8 flex justify-center">
      <el-pagination
        v-model:current-page="page"
        :page-size="size"
        :total="total"
        layout="prev, pager, next, total"
        @current-change="loadData"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { Search } from '@element-plus/icons-vue'
import { searchJobs } from '@/api/job'

const route = useRoute()
const jobs = ref<any[]>([])
const loading = ref(false)
const page = ref(1)
const size = 10
const total = ref(0)

const filters = reactive({
  keyword: (route.query.keyword as string) || '',
  companyType: '',
  educationReq: '',
  city: '',
})

async function loadData() {
  loading.value = true
  try {
    const res = await searchJobs({ ...filters, page: page.value, size })
    jobs.value = res.data?.records || []
    total.value = res.data?.total || 0
  } catch (e) {
    jobs.value = []
  } finally {
    loading.value = false
  }
}

function doSearch() {
  page.value = 1
  loadData()
}

onMounted(() => loadData())
</script>
