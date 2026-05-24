<template>
  <div class="max-w-5xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-2 dark:text-white">AI 职业规划</h1>
    <p class="text-gray-500 dark:text-gray-400 mb-8">基于AI大模型，为你量身定制职业发展方案</p>

    <div class="grid grid-cols-1 lg:grid-cols-5 gap-6">
      <!-- Input form -->
      <div class="lg:col-span-2">
        <div class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border sticky top-20">
          <h2 class="font-semibold mb-4 dark:text-white">填写信息</h2>
          <el-form :model="form" label-position="top">
            <el-form-item label="专业" required>
              <el-input v-model="form.major" placeholder="如：计算机科学与技术" />
            </el-form-item>
            <el-form-item label="学历" required>
              <el-select v-model="form.education" class="w-full">
                <el-option label="本科" value="本科" />
                <el-option label="硕士" value="硕士" />
                <el-option label="博士" value="博士" />
              </el-select>
            </el-form-item>
            <el-form-item label="技能">
              <el-input v-model="form.skills" placeholder="如：Python, Java, 机器学习" />
            </el-form-item>
            <el-form-item label="兴趣方向">
              <el-input v-model="form.interests" placeholder="如：人工智能, 金融" />
            </el-form-item>
            <el-form-item label="期望城市">
              <el-input v-model="form.city" placeholder="如：北京, 上海" />
            </el-form-item>
            <el-form-item label="目标企业类型">
              <el-select v-model="form.targetCompanyType" class="w-full" clearable>
                <el-option label="不限" value="" />
                <el-option label="国企/央企" value="国企/央企" />
                <el-option label="互联网" value="互联网" />
                <el-option label="外企" value="外企" />
                <el-option label="公务员/事业单位" value="公务员/事业单位" />
              </el-select>
            </el-form-item>
            <el-button type="primary" size="large" :loading="loading" class="w-full" @click="submitPlan">
              <el-icon class="mr-1"><DataAnalysis /></el-icon>
              开始AI分析
            </el-button>
          </el-form>
        </div>
      </div>

      <!-- Result -->
      <div class="lg:col-span-3">
        <div v-if="loading" class="bg-white dark:bg-dark-card rounded-xl p-12 border border-gray-200 dark:border-dark-border text-center">
          <div class="animate-spin w-12 h-12 border-4 border-blue-500 border-t-transparent rounded-full mx-auto mb-4"></div>
          <p class="text-gray-500 dark:text-gray-400">AI正在分析中，请稍候...</p>
          <p class="text-xs text-gray-400 mt-2">这可能需要10-30秒</p>
        </div>

        <div v-else-if="result" class="bg-white dark:bg-dark-card rounded-xl p-6 border border-gray-200 dark:border-dark-border">
          <div class="flex items-center justify-between mb-4">
            <h2 class="text-lg font-semibold dark:text-white">分析结果</h2>
            <el-tag size="small" type="info">{{ result.aiProvider }}</el-tag>
          </div>
          <div class="prose prose-sm dark:prose-invert max-w-none whitespace-pre-line text-gray-700 dark:text-gray-300">
            {{ result.overallAdvice }}
          </div>
        </div>

        <div v-else class="bg-white dark:bg-dark-card rounded-xl p-12 border border-gray-200 dark:border-dark-border text-center">
          <el-icon :size="48" class="text-gray-300 dark:text-gray-600 mb-4"><DataAnalysis /></el-icon>
          <p class="text-gray-400">填写左侧信息后点击"开始AI分析"</p>
          <p class="text-xs text-gray-400 mt-2">AI将为你生成个性化的职业规划报告</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { DataAnalysis } from '@element-plus/icons-vue'
import { careerPlan } from '@/api/ai'
import { ElMessage } from 'element-plus'
import type { CareerPlanResponse } from '@/types/ai'

const loading = ref(false)
const result = ref<CareerPlanResponse | null>(null)

const form = reactive({
  major: '',
  education: '本科',
  skills: '',
  interests: '',
  city: '',
  targetCompanyType: '',
})

async function submitPlan() {
  if (!form.major) {
    ElMessage.warning('请填写专业')
    return
  }
  loading.value = true
  result.value = null
  try {
    const res = await careerPlan(form)
    result.value = res.data
  } catch (e) {
    ElMessage.error('AI分析失败，请稍后重试')
  } finally {
    loading.value = false
  }
}
</script>
