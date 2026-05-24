<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6 dark:text-white">专业查询</h1>

    <!-- Search -->
    <div class="flex space-x-3 mb-8">
      <el-input v-model="keyword" size="large" placeholder="输入专业名称或代码搜索..." @keyup.enter="doSearch">
        <template #prefix><el-icon><Search /></el-icon></template>
      </el-input>
      <el-button type="primary" size="large" @click="doSearch">搜索</el-button>
    </div>

    <!-- Results -->
    <div v-loading="loading">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <div
          v-for="major in majors"
          :key="major.id"
          class="card-hover bg-white dark:bg-dark-card rounded-xl p-5 border border-gray-200 dark:border-dark-border cursor-pointer"
          @click="openDetail(major)"
        >
          <div class="flex items-start justify-between">
            <div>
              <h3 class="font-semibold text-lg dark:text-white">{{ major.majorName }}</h3>
              <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">{{ major.majorCode }} · {{ major.category }}</p>
            </div>
            <el-tag size="small" :type="major.hotScore > 80 ? 'danger' : 'info'">
              热度 {{ major.hotScore }}
            </el-tag>
          </div>
          <p class="text-sm text-gray-600 dark:text-gray-300 mt-3 line-clamp-2">{{ major.description }}</p>
          <div class="flex items-center space-x-4 mt-3 text-xs text-gray-500 dark:text-gray-400">
            <span>就业率: {{ major.employmentRate }}%</span>
            <span>均薪: {{ major.avgSalary }}万/年</span>
          </div>
        </div>
      </div>

      <div v-if="majors.length === 0 && !loading" class="text-center py-12 text-gray-400">
        暂无数据，请尝试搜索
      </div>

      <!-- Pagination -->
      <div class="mt-8 flex justify-center">
        <el-pagination
          v-model:current-page="page"
          :page-size="size"
          :total="total"
          layout="prev, pager, next"
          @current-change="loadData"
        />
      </div>
    </div>

    <!-- Detail Dialog -->
    <el-dialog v-model="showDetail" :title="detailMajor?.majorName" width="600px">
      <div v-if="detailMajor" class="space-y-4">
        <div>
          <h4 class="font-semibold dark:text-white">专业代码</h4>
          <p class="text-gray-600 dark:text-gray-300">{{ detailMajor.majorCode }}</p>
        </div>
        <div>
          <h4 class="font-semibold dark:text-white">学科门类</h4>
          <p class="text-gray-600 dark:text-gray-300">{{ detailMajor.category }} / {{ detailMajor.firstLevel }}</p>
        </div>
        <div>
          <h4 class="font-semibold dark:text-white">专业描述</h4>
          <p class="text-gray-600 dark:text-gray-300">{{ detailMajor.description }}</p>
        </div>
        <div>
          <h4 class="font-semibold dark:text-white">就业方向</h4>
          <p class="text-gray-600 dark:text-gray-300">{{ detailMajor.employmentDirection }}</p>
        </div>
        <div v-if="detailMajor.relatedJobs?.length">
          <h4 class="font-semibold dark:text-white mb-2">推荐岗位方向</h4>
          <div class="flex flex-wrap gap-2">
            <el-tag v-for="job in detailMajor.relatedJobs" :key="job">{{ job }}</el-tag>
          </div>
        </div>
        <div class="flex space-x-6">
          <div>
            <span class="text-sm text-gray-500">平均年薪</span>
            <p class="text-xl font-bold text-orange-500">{{ detailMajor.avgSalary }}万</p>
          </div>
          <div>
            <span class="text-sm text-gray-500">就业率</span>
            <p class="text-xl font-bold text-green-500">{{ detailMajor.employmentRate }}%</p>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { Search } from '@element-plus/icons-vue'
import { searchMajors, getMajorDetail } from '@/api/major'

const route = useRoute()
const keyword = ref((route.query.keyword as string) || '')
const majors = ref<any[]>([])
const loading = ref(false)
const page = ref(1)
const size = 12
const total = ref(0)
const showDetail = ref(false)
const detailMajor = ref<any>(null)

async function loadData() {
  loading.value = true
  try {
    const res = await searchMajors({ keyword: keyword.value, page: page.value, size })
    majors.value = res.data?.records || []
    total.value = res.data?.total || 0
  } catch (e) {
    majors.value = []
  } finally {
    loading.value = false
  }
}

function doSearch() {
  page.value = 1
  loadData()
}

async function openDetail(major: any) {
  try {
    const res = await getMajorDetail(major.id)
    detailMajor.value = res.data
  } catch (e) {
    detailMajor.value = major
  }
  showDetail.value = true
}

onMounted(() => {
  loadData()
})
</script>
