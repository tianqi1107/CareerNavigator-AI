import axios from 'axios'
import type { CareerPlanRequest, AiChatRequest } from '@/types/ai'

// AI requests use longer timeout (120s)
const aiRequest = axios.create({
  baseURL: '/api',
  timeout: 120000,
  headers: { 'Content-Type': 'application/json' },
})

aiRequest.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

aiRequest.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res.code !== 200) throw new Error(res.message || '请求失败')
    return res
  },
  (error) => {
    if (error.code === 'ECONNABORTED') throw new Error('请求超时，请稍后重试')
    if (!error.response) throw new Error('网络连接失败，请检查网络')
    throw error
  }
)

export function careerPlan(data: CareerPlanRequest) {
  return aiRequest.post<any, any>('/ai/career-plan', data)
}

export function aiChat(data: AiChatRequest) {
  return aiRequest.post<any, any>('/ai/chat', data)
}

export function getAiHistory(params: { type?: string; page?: number; size?: number }) {
  return request.get<any, any>('/ai/history', { params })
}
