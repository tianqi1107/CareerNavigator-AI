<template>
  <div class="max-w-3xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-2 dark:text-white">AI 就业顾问</h1>
    <p class="text-gray-500 dark:text-gray-400 mb-6">有任何就业问题，直接问我</p>

    <!-- Chat messages -->
    <div class="bg-white dark:bg-dark-card rounded-xl border border-gray-200 dark:border-dark-border mb-4 h-[500px] overflow-y-auto p-4 space-y-4" ref="chatBox">
      <div v-for="(msg, i) in messages" :key="i" :class="msg.role === 'user' ? 'flex justify-end' : 'flex justify-start'">
        <div
          v-if="msg.role === 'user'"
          class="max-w-[80%] px-4 py-3 text-sm bg-blue-500 text-white rounded-2xl rounded-br-sm whitespace-pre-line"
        >
          {{ msg.content }}
        </div>
        <div
          v-else
          class="max-w-[80%] px-4 py-3 text-sm bg-gray-100 dark:bg-dark-border text-gray-800 dark:text-gray-200 rounded-2xl rounded-bl-sm"
        >
          <MarkdownRenderer :content="msg.content" />
        </div>
      </div>
      <div v-if="chatLoading" class="flex justify-start">
        <div class="bg-gray-100 dark:bg-dark-border rounded-2xl rounded-bl-sm px-4 py-3 text-sm">
          <span class="animate-pulse">AI正在思考...</span>
        </div>
      </div>
    </div>

    <!-- Quick questions -->
    <div class="flex flex-wrap gap-2 mb-4">
      <el-button
        v-for="q in quickQuestions"
        :key="q"
        size="small"
        @click="sendMessage(q)"
      >
        {{ q }}
      </el-button>
    </div>

    <!-- Input -->
    <div class="flex space-x-3">
      <el-input
        v-model="input"
        size="large"
        placeholder="输入你的问题..."
        @keyup.enter="sendMessage()"
      />
      <el-button type="primary" size="large" :loading="chatLoading" @click="sendMessage()">
        发送
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, nextTick } from 'vue'
import { aiChat } from '@/api/ai'
import { ElMessage } from 'element-plus'
import MarkdownRenderer from '@/components/common/MarkdownRenderer.vue'

const input = ref('')
const chatLoading = ref(false)
const chatBox = ref<HTMLElement>()
const messages = ref<{ role: string; content: string }[]>([
  { role: 'assistant', content: '你好！我是AI就业顾问，可以帮你解答关于专业就业方向、岗位分析、考公考研等问题。请问有什么可以帮你的？' },
])

const quickQuestions = [
  '通信工程适合哪些国企？',
  '海洋技术专业能报哪些事业编？',
  '我适合考公还是进互联网？',
  '计算机专业如何准备央企面试？',
]

async function sendMessage(msg?: string) {
  const text = msg || input.value.trim()
  if (!text) return
  input.value = ''

  messages.value.push({ role: 'user', content: text })
  await scrollToBottom()

  chatLoading.value = true
  try {
    const res = await aiChat({ message: text })
    messages.value.push({ role: 'assistant', content: res.data || '抱歉，暂时无法回答。' })
  } catch (e: any) {
    messages.value.push({ role: 'assistant', content: '抱歉，' + (e.message || 'AI服务暂时不可用') + '。请稍后重试。' })
  } finally {
    chatLoading.value = false
    await scrollToBottom()
  }
}

async function scrollToBottom() {
  await nextTick()
  if (chatBox.value) {
    chatBox.value.scrollTop = chatBox.value.scrollHeight
  }
}
</script>
