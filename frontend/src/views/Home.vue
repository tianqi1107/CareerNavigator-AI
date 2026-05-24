<template>
  <div>
    <!-- Hero Section -->
    <section class="gradient-bg text-white py-20 px-4">
      <div class="max-w-5xl mx-auto text-center">
        <h1 class="text-4xl md:text-6xl font-bold mb-6">
          AI 驱动的
          <span class="bg-gradient-to-r from-blue-400 to-purple-400 bg-clip-text text-transparent">
            智能职业规划
          </span>
        </h1>
        <p class="text-lg md:text-xl text-gray-300 mb-8 max-w-2xl mx-auto">
          根据你的专业、技能和兴趣，智能分析最适合的岗位方向，
          聚合公务员、国企、央企等海量就业信息
        </p>

        <!-- Search bar -->
        <div class="max-w-2xl mx-auto flex space-x-3">
          <el-input
            v-model="searchKeyword"
            size="large"
            placeholder="搜索专业或岗位..."
            class="flex-1"
            @keyup.enter="handleSearch"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
          <el-button type="primary" size="large" @click="handleSearch">
            搜索
          </el-button>
        </div>

        <!-- Quick stats -->
        <div class="flex justify-center space-x-8 mt-10">
          <div v-for="stat in stats" :key="stat.label" class="text-center">
            <div class="text-2xl md:text-3xl font-bold text-blue-400">{{ stat.value }}</div>
            <div class="text-sm text-gray-400 mt-1">{{ stat.label }}</div>
          </div>
        </div>
      </div>
    </section>

    <!-- Features -->
    <section class="max-w-7xl mx-auto px-4 py-16">
      <h2 class="text-2xl font-bold text-center mb-12 dark:text-white">核心功能</h2>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div
          v-for="feature in features"
          :key="feature.title"
          class="card-hover bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border"
        >
          <div class="w-12 h-12 rounded-lg bg-blue-500/10 flex items-center justify-center mb-4">
            <el-icon :size="24" class="text-blue-500">
              <component :is="feature.icon" />
            </el-icon>
          </div>
          <h3 class="font-semibold text-lg mb-2 dark:text-white">{{ feature.title }}</h3>
          <p class="text-sm text-gray-500 dark:text-gray-400">{{ feature.desc }}</p>
        </div>
      </div>
    </section>

    <!-- Hot Majors -->
    <section class="max-w-7xl mx-auto px-4 py-12">
      <div class="flex items-center justify-between mb-8">
        <h2 class="text-2xl font-bold dark:text-white">热门专业</h2>
        <router-link to="/majors" class="text-blue-500 hover:text-blue-600 text-sm">
          查看全部 →
        </router-link>
      </div>
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4">
        <div
          v-for="(major, index) in hotMajors"
          :key="major.id"
          class="card-hover bg-white dark:bg-dark-card rounded-xl p-4 border border-gray-200 dark:border-dark-border cursor-pointer"
          @click="$router.push(`/majors?keyword=${major.majorName}`)"
        >
          <div class="flex items-center space-x-3">
            <span
              class="w-6 h-6 rounded-full flex items-center justify-center text-xs font-bold text-white"
              :class="index < 3 ? 'bg-gradient-to-br from-amber-400 to-orange-500' : 'bg-gray-400'"
            >
              {{ index + 1 }}
            </span>
            <span class="font-medium dark:text-white">{{ major.majorName }}</span>
          </div>
          <div class="mt-2 text-xs text-gray-500 dark:text-gray-400">
            {{ major.category }} · 就业率 {{ major.employmentRate }}%
          </div>
        </div>
      </div>
    </section>

    <!-- Hot Jobs -->
    <section class="max-w-7xl mx-auto px-4 py-12">
      <div class="flex items-center justify-between mb-8">
        <h2 class="text-2xl font-bold dark:text-white">热门岗位</h2>
        <router-link to="/jobs" class="text-blue-500 hover:text-blue-600 text-sm">
          查看全部 →
        </router-link>
      </div>
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        <div
          v-for="job in hotJobs"
          :key="job.id"
          class="card-hover bg-white dark:bg-dark-card rounded-xl p-5 border border-gray-200 dark:border-dark-border cursor-pointer"
          @click="$router.push(`/jobs/${job.id}`)"
        >
          <div class="flex justify-between items-start">
            <div>
              <h3 class="font-semibold dark:text-white">{{ job.jobName }}</h3>
              <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">{{ job.companyName }}</p>
            </div>
            <span
              v-if="job.isStateOwned"
              class="text-xs px-2 py-0.5 rounded bg-blue-100 text-blue-700 dark:bg-blue-900/30 dark:text-blue-400"
            >
              国企/央企
            </span>
            <span
              v-else-if="job.isCivilService"
              class="text-xs px-2 py-0.5 rounded bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400"
            >
              公务员
            </span>
          </div>
          <div class="flex items-center justify-between mt-3">
            <span class="text-sm text-orange-500 font-medium">
              {{ job.salaryMin }}-{{ job.salaryMax }}万/年
            </span>
            <span class="text-xs text-gray-400">{{ job.city }}</span>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Search, DataAnalysis, Briefcase, School, TrendCharts } from '@element-plus/icons-vue'
import { getHotMajors } from '@/api/major'
import { getHotJobs } from '@/api/job'

const router = useRouter()
const searchKeyword = ref('')
const hotMajors = ref<any[]>([])
const hotJobs = ref<any[]>([])

const stats = [
  { value: '30+', label: '专业方向' },
  { value: '200+', label: '岗位信息' },
  { value: '10+', label: '企业类型' },
  { value: 'AI', label: '智能分析' },
]

const features = [
  { icon: 'Search', title: '专业查询', desc: '支持专业名称、代码搜索，模糊查询，热门推荐' },
  { icon: 'DataAnalysis', title: 'AI职业规划', desc: '基于AI大模型分析，个性化推荐岗位方向和学习路线' },
  { icon: 'Briefcase', title: '岗位分类', desc: '公务员、国企、央企、私企、外企等全覆盖分类体系' },
  { icon: 'TrendCharts', title: '数据可视化', desc: '岗位热度、城市分布、薪资趋势等多维度数据展示' },
]

function handleSearch() {
  if (searchKeyword.value.trim()) {
    router.push({ path: '/majors', query: { keyword: searchKeyword.value } })
  }
}

onMounted(async () => {
  try {
    const [majorsRes, jobsRes] = await Promise.all([
      getHotMajors(5),
      getHotJobs(6),
    ])
    hotMajors.value = majorsRes.data || []
    hotJobs.value = jobsRes.data || []
  } catch (e) {
    // Use mock data if API not available
    hotMajors.value = [
      { id: 1, majorName: '人工智能', category: '工学', employmentRate: 96.5 },
      { id: 2, majorName: '计算机科学与技术', category: '工学', employmentRate: 97.5 },
      { id: 3, majorName: '软件工程', category: '工学', employmentRate: 97.0 },
      { id: 4, majorName: '数据科学与大数据技术', category: '工学', employmentRate: 96.0 },
      { id: 5, majorName: '电子信息工程', category: '工学', employmentRate: 95.2 },
    ]
    hotJobs.value = [
      { id: 1, jobName: '大模型算法研究员', companyName: '智谱AI', salaryMin: 35, salaryMax: 70, city: '北京', isStateOwned: 0, isCivilService: 0 },
      { id: 2, jobName: 'Java后端开发工程师', companyName: '字节跳动', salaryMin: 25, salaryMax: 50, city: '北京', isStateOwned: 0, isCivilService: 0 },
      { id: 3, jobName: '算法工程师', companyName: '阿里巴巴', salaryMin: 30, salaryMax: 60, city: '杭州', isStateOwned: 0, isCivilService: 0 },
      { id: 4, jobName: '电气工程师', companyName: '国家电网', salaryMin: 12, salaryMax: 25, city: '全国', isStateOwned: 1, isCivilService: 0 },
      { id: 5, jobName: '航天工程师', companyName: '航天科技集团', salaryMin: 15, salaryMax: 28, city: '北京', isStateOwned: 1, isCivilService: 0 },
      { id: 6, jobName: '银行科技岗', companyName: '工商银行', salaryMin: 12, salaryMax: 25, city: '北京', isStateOwned: 1, isCivilService: 0 },
    ]
  }
})
</script>
