import request from './request'
import type { CareerPlanRequest, AiChatRequest } from '@/types/ai'

export function careerPlan(data: CareerPlanRequest) {
  return request.post<any, any>('/ai/career-plan', data)
}

export function aiChat(data: AiChatRequest) {
  return request.post<any, any>('/ai/chat', data)
}

export function getAiHistory(params: { type?: string; page?: number; size?: number }) {
  return request.get<any, any>('/ai/history', { params })
}
