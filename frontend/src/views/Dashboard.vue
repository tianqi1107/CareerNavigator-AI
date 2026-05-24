<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6 dark:text-white">数据可视化看板</h1>

    <!-- Summary cards -->
    <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
      <div v-for="card in summaryCards" :key="card.label"
        class="bg-white dark:bg-dark-card rounded-xl p-5 border border-gray-200 dark:border-dark-border">
        <div class="text-sm text-gray-500 dark:text-gray-400">{{ card.label }}</div>
        <div class="text-2xl font-bold mt-1" :class="card.color">{{ card.value }}</div>
      </div>
    </div>

    <!-- Charts -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <div class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border">
        <h3 class="font-semibold mb-4 dark:text-white">岗位发布趋势</h3>
        <div ref="trendChartRef" class="h-72"></div>
      </div>
      <div class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border">
        <h3 class="font-semibold mb-4 dark:text-white">城市就业分布 TOP15</h3>
        <div ref="cityChartRef" class="h-72"></div>
      </div>
      <div class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border">
        <h3 class="font-semibold mb-4 dark:text-white">企业类型薪资对比</h3>
        <div ref="salaryChartRef" class="h-72"></div>
      </div>
      <div class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border">
        <h3 class="font-semibold mb-4 dark:text-white">岗位分类分布</h3>
        <div ref="categoryChartRef" class="h-72"></div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, computed } from 'vue'
import * as echarts from 'echarts'
import { getDashboardData } from '@/api/dashboard'

const trendChartRef = ref<HTMLElement>()
const cityChartRef = ref<HTMLElement>()
const salaryChartRef = ref<HTMLElement>()
const categoryChartRef = ref<HTMLElement>()

let charts: echarts.ECharts[] = []

const summaryCards = computed(() => [
  { label: '总岗位数', value: dashboardData.value?.summary?.totalJobs || 200, color: 'text-blue-500' },
  { label: '专业数量', value: dashboardData.value?.summary?.totalMajors || 30, color: 'text-green-500' },
  { label: 'AI供应商', value: '4', color: 'text-purple-500' },
  { label: '数据来源', value: '5+', color: 'text-orange-500' },
])

const dashboardData = ref<any>(null)

function initCharts() {
  // Trend chart
  if (trendChartRef.value) {
    const chart = echarts.init(trendChartRef.value)
    charts.push(chart)
    const trendData = dashboardData.value?.jobTrends || [
      { month: '2024-01', count: 45 }, { month: '2024-02', count: 52 },
      { month: '2024-03', count: 68 }, { month: '2024-04', count: 75 },
      { month: '2024-05', count: 82 }, { month: '2024-06', count: 95 },
    ]
    chart.setOption({
      tooltip: { trigger: 'axis' },
      xAxis: { type: 'category', data: trendData.map((d: any) => d.month) },
      yAxis: { type: 'value' },
      series: [{
        data: trendData.map((d: any) => d.count),
        type: 'line',
        smooth: true,
        areaStyle: { color: 'rgba(59,130,246,0.1)' },
        lineStyle: { color: '#3b82f6' },
        itemStyle: { color: '#3b82f6' },
      }],
    })
  }

  // City chart
  if (cityChartRef.value) {
    const chart = echarts.init(cityChartRef.value)
    charts.push(chart)
    const cityData = dashboardData.value?.cityDistribution || [
      { city: '北京', count: 45 }, { city: '上海', count: 38 },
      { city: '深圳', count: 32 }, { city: '杭州', count: 28 },
      { city: '广州', count: 22 }, { city: '成都', count: 18 },
      { city: '南京', count: 15 }, { city: '武汉', count: 14 },
      { city: '西安', count: 12 }, { city: '长沙', count: 10 },
    ]
    chart.setOption({
      tooltip: { trigger: 'axis' },
      xAxis: { type: 'category', data: cityData.map((d: any) => d.city || d.city), axisLabel: { rotate: 30 } },
      yAxis: { type: 'value' },
      series: [{
        data: cityData.map((d: any) => d.count),
        type: 'bar',
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#60a5fa' },
            { offset: 1, color: '#3b82f6' },
          ]),
        },
      }],
    })
  }

  // Salary chart
  if (salaryChartRef.value) {
    const chart = echarts.init(salaryChartRef.value)
    charts.push(chart)
    chart.setOption({
      tooltip: { trigger: 'axis' },
      xAxis: { type: 'category', data: ['国企', '央企', '私企', '外企', '事业单位'] },
      yAxis: { type: 'value', name: '万/年' },
      series: [{
        data: [16, 22, 25, 35, 12],
        type: 'bar',
        itemStyle: {
          color: (params: any) => {
            const colors = ['#3b82f6', '#8b5cf6', '#f59e0b', '#10b981', '#6366f1']
            return colors[params.dataIndex]
          },
        },
      }],
    })
  }

  // Category pie
  if (categoryChartRef.value) {
    const chart = echarts.init(categoryChartRef.value)
    charts.push(chart)
    chart.setOption({
      tooltip: { trigger: 'item' },
      series: [{
        type: 'pie',
        radius: ['40%', '70%'],
        data: [
          { value: 35, name: '私企' },
          { value: 25, name: '国企/央企' },
          { value: 15, name: '公务员' },
          { value: 10, name: '外企' },
          { value: 8, name: '事业单位' },
          { value: 7, name: '其他' },
        ],
        emphasis: { itemStyle: { shadowBlur: 10, shadowColor: 'rgba(0,0,0,0.3)' } },
      }],
    })
  }
}

onMounted(async () => {
  try {
    const res = await getDashboardData()
    dashboardData.value = res.data
  } catch (e) {
    // Use mock data
  }
  initCharts()

  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  charts.forEach(c => c.dispose())
  window.removeEventListener('resize', handleResize)
})

function handleResize() {
  charts.forEach(c => c.resize())
}
</script>
